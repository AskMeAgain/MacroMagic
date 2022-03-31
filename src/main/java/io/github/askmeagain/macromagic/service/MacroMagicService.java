package io.github.askmeagain.macromagic.service;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.ui.components.JBList;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.util.ArrayList;

@Slf4j
@Service
public final class MacroMagicService {

  public MacroMagicService() {
    anActionJBList = new JBList<>(actionHistory);
  }

  private boolean running = false;
  private final MacroMagicPersistingService persistingUtils = MacroMagicPersistingService.getInstance();

  @Getter
  private final JBList<AnAction> anActionJBList;
  @Getter
  private final DefaultListModel<AnAction> actionHistory = new DefaultListModel<>();

  public boolean isRunning() {
    return running;
  }

  public void setRunning() {
    running = true;
    log.info("Started now");
  }

  public void stopRunning() {
    running = false;
    log.info("Stopped now");
  }

  public void runActions(AnActionEvent e) {
    for (var i = 0; i < actionHistory.size(); i++) {
      actionHistory.get(i).actionPerformed(e);
    }
  }

  public void addAction(AnAction action) {
    if (actionHistory.size() == 30) {
      actionHistory.removeElementAt(0);
    }
    actionHistory.addElement(action);
  }

  public void persist() {
    persistingUtils.persistActions(actionHistory, "TestName");
  }

  public void load() {
    actionHistory.clear();
    actionHistory.addAll(persistingUtils.loadActions("TestName"));
  }

  public static MacroMagicService getInstance() {
    return ApplicationManager.getApplication()
        .getService(MacroMagicService.class);
  }

  public void clearHistory() {
    actionHistory.clear();
  }

  public void removeSelectedIndices() {
    var selectedIndices = anActionJBList.getSelectedIndices();
    var selectedItems = new ArrayList<AnAction>();

    for (int selectedIndex : selectedIndices) {
      selectedItems.add(actionHistory.getElementAt(selectedIndex));
    }

    selectedItems.forEach(actionHistory::removeElement);
  }
}
