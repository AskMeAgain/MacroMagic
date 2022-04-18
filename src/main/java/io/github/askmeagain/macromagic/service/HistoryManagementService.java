package io.github.askmeagain.macromagic.service;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.ui.components.JBList;
import io.github.askmeagain.macromagic.entities.MacroContainer;
import io.github.askmeagain.macromagic.entities.MacroMagicState;
import io.github.askmeagain.macromagic.listener.RightClickContextListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.DefaultListModel;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public final class HistoryManagementService implements DropTargetListener {

  @Getter
  private final JBList<AnAction> anActionJbList;
  @Getter
  private final DefaultListModel<AnAction> actionHistory = new DefaultListModel<>();

  @Getter(lazy = true)
  private final MacroMagicState state = PersistenceManagementService.getInstance().getState();
  @Getter(lazy = true)
  private final MacroManagementService macroManagementService = MacroManagementService.getInstance();
  @Getter(lazy = true)
  private final HelperService helperService = HelperService.getInstance();

  private static final String MACRO_UTILS_GROUP = "io.github.askmeagain.macromagic.actions.groups.utils";

  public HistoryManagementService() {
    anActionJbList = new JBList<>(actionHistory);
    var group = (ActionGroup) ActionManager.getInstance().getAction(MACRO_UTILS_GROUP);
    anActionJbList.addMouseListener(new RightClickContextListener(group));
  }

  public static HistoryManagementService getInstance() {
    return ApplicationManager.getApplication()
        .getService(HistoryManagementService.class);
  }

  public boolean isRunning() {
    return getState().getRunning();
  }

  public void persistMacro(String name) {
    getMacroManagementService().persistActions(getSelectedValuesList(), name);
  }

  public void importMacro(List<MacroContainer> macroContainers) {
    macroContainers.stream()
        .map(MacroContainer::getActions)
        .flatMap(Collection::stream)
        .map(getHelperService()::deserializeAction)
        .forEach(this::addAction);
  }

  public void clearHistory() {
    actionHistory.clear();
  }

  public void addAction(AnAction action) {
    actionHistory.addElement(action);

    while (actionHistory.size() > getState().getHistorySize()) {
      actionHistory.removeElementAt(0);
    }
  }

  public void removeSelectedIndices() {
    getSelectedValuesList().forEach(actionHistory::removeElement);
  }

  public void moveSelectionUp() {
    var selectedIndices = anActionJbList.getSelectedIndices();

    if (selectedIndices[0] == 0) {
      return;
    }

    for (int i = 0; i < selectedIndices.length; i++) {
      int selectedIndex = selectedIndices[i];
      var action = actionHistory.remove(selectedIndex);
      actionHistory.insertElementAt(action, selectedIndex - 1);
      selectedIndices[i]--;
    }

    anActionJbList.setSelectedIndices(selectedIndices);
  }

  public void moveSelectionDown() {
    var selectedIndices = anActionJbList.getSelectedIndices();

    var lastSelectedIndex = selectedIndices[selectedIndices.length - 1];
    if (lastSelectedIndex == actionHistory.size() - 1) {
      return;
    }

    for (int i = selectedIndices.length - 1; i >= 0; i--) {
      var action = actionHistory.remove(selectedIndices[i]);
      actionHistory.insertElementAt(action, selectedIndices[i] + 1);
      selectedIndices[i]++;
    }

    anActionJbList.setSelectedIndices(selectedIndices);
  }

  public List<AnAction> getSelectedValuesList() {
    return anActionJbList.getSelectedValuesList();
  }

  public void startStopRecording() {
    getState().setRunning(!getState().getRunning());
  }

  public void removeLatestAction() {
    if (actionHistory.isEmpty()) {
      return;
    }
    actionHistory.removeElementAt(actionHistory.size() - 1);
  }

  @Override
  public void drop(DropTargetDropEvent e) {
    try {
      var tr = e.getTransferable();

      if (tr.isDataFlavorSupported(DataFlavor.stringFlavor)) {
        e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
        var macroName = (String) tr.getTransferData(DataFlavor.stringFlavor);
        var macroContainer = getMacroManagementService().getMacro(macroName);

        actionHistory.addElement(getHelperService().createMacroAction(macroContainer));

        e.getDropTargetContext().dropComplete(true);
      } else {
        System.err.println("DataFlavor.stringFlavor is not supported, rejected");
        e.rejectDrop();
      }
    } catch (Exception ex) {
      System.err.println("UnsupportedFlavorException");
      ex.printStackTrace();
      e.rejectDrop();
    }
  }

  public void duplicateSelected() {
    var selectedIndices = anActionJbList.getSelectedIndices();

    for (int i = selectedIndices.length - 1; i >= 0; i--) {
      var selectedIndex = selectedIndices[i];
      actionHistory.add(selectedIndex + 1, actionHistory.get(selectedIndex));
      selectedIndices[i] += 1;

      //offsetting the indices
      for (int ii = i + 1; ii < selectedIndices.length; ii++) {
        selectedIndices[ii]++;
      }
    }

    anActionJbList.setSelectedIndices(selectedIndices);
  }

  @Override
  public void dragEnter(DropTargetDragEvent dropTargetDragEvent) {
  }

  @Override
  public void dragOver(DropTargetDragEvent dropTargetDragEvent) {
  }

  @Override
  public void dropActionChanged(DropTargetDragEvent dropTargetDragEvent) {
  }

  @Override
  public void dragExit(DropTargetEvent dropTargetEvent) {
  }
}
