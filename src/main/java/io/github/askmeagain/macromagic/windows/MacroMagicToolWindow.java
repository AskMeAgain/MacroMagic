package io.github.askmeagain.macromagic.windows;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.ContentFactory;
import io.github.askmeagain.macromagic.service.HistoryManagementService;
import io.github.askmeagain.macromagic.service.MacroManagementService;
import org.jetbrains.annotations.NotNull;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.dnd.DropTarget;

public class MacroMagicToolWindow implements ToolWindowFactory, DumbAware {

  private final HistoryManagementService historyManagementService = HistoryManagementService.getInstance();
  private final MacroManagementService macroManagementService = MacroManagementService.getInstance();

  @Override
  public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 1;
    gbc.weighty = 0.5;

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;

    var panel = new JPanel(new GridBagLayout());
    panel.add(createHistoryPanel(), gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 1;

    panel.add(createMacroPanel(), gbc);

    var content = ApplicationManager.getApplication()
        .getService(ContentFactory.class)
        .createContent(panel, "", false);
    var contentManager = toolWindow.getContentManager();

    contentManager.addContent(content);
  }

  private JComponent createHistoryPanel() {
    var buttonToolBar = createButtonToolBar("history");

    var jList = historyManagementService.getAnActionJbList();

    new DropTarget(jList, historyManagementService);

    jList.setSelectedIndex(0);

    return createListPanel(buttonToolBar, jList);
  }

  private JComponent createMacroPanel() {
    var buttonToolBar = createButtonToolBar("macros");

    var jList = macroManagementService.getAnActionJbList();
    jList.setDragEnabled(true);
    jList.setSelectedIndex(0);

    return createListPanel(buttonToolBar, jList);
  }

  private JComponent createListPanel(JComponent buttonToolBar, JBList<?> anActionJbList) {


    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1;
    gbc.weighty = 0;

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;

    var panel = new JPanel(new GridBagLayout());
    panel.add(buttonToolBar, gbc);

    gbc.weighty = 1;
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridheight = 100;
    gbc.fill = GridBagConstraints.BOTH;

    var pane = new JBScrollPane(anActionJbList);
    panel.add(pane, gbc);

    panel.setPreferredSize(new Dimension(0, 200));

    return panel;
  }

  private JComponent createButtonToolBar(String groupName) {

    var instance = ActionManager.getInstance();
    var actionGroup = (ActionGroup) instance.getAction("io.github.askmeagain.macromagic.group." + groupName);
    var toolbar = instance.createActionToolbar(ActionPlaces.EDITOR_TOOLBAR, actionGroup, true);

    return toolbar.getComponent();
  }

}
