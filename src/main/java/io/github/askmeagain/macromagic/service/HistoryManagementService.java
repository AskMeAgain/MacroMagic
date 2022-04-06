package io.github.askmeagain.macromagic.service;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.ui.components.JBList;
import io.github.askmeagain.macromagic.entities.MacroContainer;
import io.github.askmeagain.macromagic.entities.MacroMagicState;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.DefaultListModel;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public final class HistoryManagementService implements DropTargetListener {

  @Getter
  private final JBList<AnAction> anActionJbList;

  private final MacroManagementService macroManagementService = MacroManagementService.getInstance();
  private final HelperService helperService = HelperService.getInstance();

  public HistoryManagementService() {
    anActionJbList = new JBList<>(actionHistory);
  }

  private MacroMagicState state = PersistenceManagementService.getInstance().getState();

  public boolean isRunning() {
    return state.getRunning();
  }

  @Getter
  private final DefaultListModel<AnAction> actionHistory = new DefaultListModel<>();

  public void persistMacro(String name) {
    var selectedItems = anActionJbList.getSelectedValuesList();

    macroManagementService.persistActions(selectedItems, name);
  }

  public void importMacro(List<MacroContainer> macroContainers) {
    macroContainers.stream()
        .map(MacroContainer::getActions)
        .flatMap(Collection::stream)
        .map(helperService::deserializeAction)
        .forEach(this::addAction);
  }

  public void addAction(AnAction action) {
    if (actionHistory.size() == 30) {
      actionHistory.removeElementAt(0);
    }
    actionHistory.addElement(action);
  }

  public void clearHistory() {
    actionHistory.clear();
  }

  public void removeSelectedIndices() {
    var selectedIndices = anActionJbList.getSelectedIndices();
    var selectedItems = new ArrayList<AnAction>();

    for (int selectedIndex : selectedIndices) {
      selectedItems.add(actionHistory.getElementAt(selectedIndex));
    }

    selectedItems.forEach(actionHistory::removeElement);
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

  public void startStopRecording() {
    state.setRunning(!state.getRunning());
  }

  public static HistoryManagementService getInstance() {
    return ApplicationManager.getApplication()
        .getService(HistoryManagementService.class);
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

  @Override
  public void drop(DropTargetDropEvent e) {
    try {
      var tr = e.getTransferable();

      if (tr.isDataFlavorSupported(DataFlavor.stringFlavor)) {
        e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
        var macroName = (String) tr.getTransferData(DataFlavor.stringFlavor);
        var macroContainer = macroManagementService.getMacro(macroName);

        actionHistory.addElement(helperService.createMacroAction(macroContainer));

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
    }
  }
}
