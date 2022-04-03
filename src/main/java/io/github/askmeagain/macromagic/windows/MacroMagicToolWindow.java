package io.github.askmeagain.macromagic.windows;

import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.impl.ActionButton;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.ContentFactory;
import io.github.askmeagain.macromagic.service.MacroManagementService;
import io.github.askmeagain.macromagic.service.HistoryManagementService;
import io.github.askmeagain.macromagic.windows.buttons.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DropTarget;

public class MacroMagicToolWindow implements ToolWindowFactory {

  public static final Dimension MINIMUM_SIZE = ActionToolbar.DEFAULT_MINIMUM_BUTTON_SIZE;

  private final HistoryManagementService historyManagementService = HistoryManagementService.getInstance();
  private final MacroManagementService macroManagementService = MacroManagementService.getInstance();

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
        new CreateNewMacroButton(historyManagementService),
        new RemoveEntryFromHistoryButton(historyManagementService),
        new ClearHistoryButton(historyManagementService),
        new MoveActionUpButton(historyManagementService),
        new MoveActionDownButton(historyManagementService),
        new StartStopRecordingButton(historyManagementService)
    );

    var jList = historyManagementService.getAnActionJBList();
    DropTarget dropTarget = new DropTarget (jList, historyManagementService);

    jList.setSelectedIndex(0);

    var pane = new JBScrollPane(jList);
    pane.setSize(1000, 1000);

    var historyPanel = new JPanel();

    historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
    historyPanel.add(ToolUtils.getTitleLabel("History"));
    historyPanel.add(buttonToolBar);
    historyPanel.add(pane);

    return historyPanel;
  }

  private JComponent createStoredMacrosPanel() {
    var buttonToolBar = createButtonToolBar(
        MINIMUM_SIZE.height + 40,
        new RunMacroButton(macroManagementService),
        new CombineMacroButton(macroManagementService),
        new LoadMacroToHistoryButton(macroManagementService, historyManagementService),
        new DeleteMacroButton(macroManagementService)
    );

    var anActionJBList = macroManagementService.getAnActionJBList();
    var pane = new JBScrollPane(anActionJBList);
    pane.setSize(1000, 400);

    var historyPanel = new JPanel();

    historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
    historyPanel.add(ToolUtils.getTitleLabel("Available Macros"));
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

}
