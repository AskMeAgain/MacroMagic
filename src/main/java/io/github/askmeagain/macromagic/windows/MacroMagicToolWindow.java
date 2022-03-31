package io.github.askmeagain.macromagic.windows;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.impl.ActionButton;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.ContentFactory;
import io.github.askmeagain.macromagic.actions.internal.PressKeyAction;
import io.github.askmeagain.macromagic.service.MacroMagicPersistingService;
import io.github.askmeagain.macromagic.service.MacroMagicService;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class MacroMagicToolWindow implements ToolWindowFactory {

  public static final Dimension MINIMUM_SIZE = ActionToolbar.DEFAULT_MINIMUM_BUTTON_SIZE;

  private final MacroMagicService macroMagicService = MacroMagicService.getInstance();
  private final MacroMagicPersistingService macroMagicPersistingService = MacroMagicPersistingService.getInstance();

  @Override
  public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
    var contentFactory = ApplicationManager.getApplication().getService(ContentFactory.class);

    var panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    panel.add(createHistoryPanel());
    panel.add(createStoredMacrosPanel());

    var content = contentFactory.createContent(panel, "", false);
    toolWindow.getContentManager().addContent(content);
  }

  private JComponent createHistoryPanel() {
    var buttonToolBar = createButtonToolBar(
        MINIMUM_SIZE.height + 20,
        new ActionButton(new PressKeyAction('a'), ToolUtils.getPresentation(AllIcons.General.Add), ActionPlaces.UNKNOWN, MINIMUM_SIZE),
        new ActionButton(new PressKeyAction('a'), ToolUtils.getPresentation(AllIcons.General.Remove), ActionPlaces.UNKNOWN, MINIMUM_SIZE),
        new ActionButton(new PressKeyAction('a'), ToolUtils.getPresentation(AllIcons.Actions.GC), ActionPlaces.UNKNOWN, MINIMUM_SIZE)
    );

    var jList = new JBList<>(macroMagicService.getActionHistory());
    jList.setSelectedIndex(0);

    var pane = new JBScrollPane(jList);
    pane.setSize(1000, 1000);

    var historyPanel = new JPanel();

    historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
    historyPanel.add(getTitleLabel("History"));
    historyPanel.add(buttonToolBar);
    historyPanel.add(pane);

    return historyPanel;
  }

  private JComponent createStoredMacrosPanel() {
    var buttonToolBar = createButtonToolBar(
        MINIMUM_SIZE.height + 40,
        new ActionButton(new PressKeyAction('a'), ToolUtils.getPresentation(AllIcons.General.Add), ActionPlaces.UNKNOWN, MINIMUM_SIZE),
        new ActionButton(new PressKeyAction('a'), ToolUtils.getPresentation(AllIcons.General.Remove), ActionPlaces.UNKNOWN, MINIMUM_SIZE),
        new ActionButton(new PressKeyAction('a'), ToolUtils.getPresentation(AllIcons.Actions.GC), ActionPlaces.UNKNOWN, MINIMUM_SIZE),
        new ActionButton(new PressKeyAction('a'), ToolUtils.getPresentation(AllIcons.Actions.Execute), ActionPlaces.UNKNOWN, MINIMUM_SIZE)
    );

    var jList = new JBList<>(macroMagicPersistingService.getPersistedActions());
    var pane = new JBScrollPane(jList);
    pane.setSize(1000, 400);

    var historyPanel = new JPanel();

    historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
    historyPanel.add(getTitleLabel("Available Macros"));
    historyPanel.add(buttonToolBar);
    historyPanel.add(pane);

    return historyPanel;
  }

  private JPanel createButtonToolBar(int height, ActionButton... actionButton) {

    var buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));

    for (var button : actionButton) {
      buttonPanel.add(button);
    }

    return buttonPanel;
  }

  private JLabel getTitleLabel(String title) {
    var label = new JLabel(title, SwingConstants.LEFT);
    label.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
    label.setMinimumSize(new Dimension(Integer.MAX_VALUE, 30));
    label.setOpaque(true);
    return label;
  }
}
