package io.github.askmeagain.macromagic.service;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.ui.components.JBList;
import io.github.askmeagain.macromagic.actions.internal.ExecuteMacroAction;
import io.github.askmeagain.macromagic.entities.MacroContainer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public final class MacroMagicPersistingService {

  private final HelperService helperService = HelperService.getInstance();

  @Getter
  private final DefaultListModel<MacroContainer> persistedMacros = new DefaultListModel<>();

  @Getter
  private final JBList<MacroContainer> anActionJBList;

  public MacroMagicPersistingService() {
    anActionJBList = new JBList<>(persistedMacros);
  }

  public void persistActions(List<AnAction> actions, String name) {
    var result = actions.stream()
        .map(helperService::serializeAction)
        .collect(Collectors.toList());

    log.info("'{}' got persisted: {}", name, result);
    var macroContainer = MacroContainer.builder()
        .macroName(name)
        .actions(result)
        .build();
    persistedMacros.addElement(macroContainer);
    helperService.registerAction(macroContainer);
  }

  public void deleteSelected() {
    var selectedIndices = anActionJBList.getSelectedIndices();
    var selectedItems = new ArrayList<MacroContainer>();

    for (int selectedIndex : selectedIndices) {
      var removedMacro = persistedMacros.getElementAt(selectedIndex);
      selectedItems.add(removedMacro);
      helperService.unregisterAction(removedMacro);
    }

    selectedItems.forEach(persistedMacros::removeElement);
  }

  public void combineSelected(String name) {
    var selectedItems = getCurrentSelectedMacros();

    selectedItems.forEach(persistedMacros::removeElement);

    var macroContainer = MacroContainer.builder()
        .macroName(name)
        .actions(selectedItems.stream()
            .map(MacroContainer::getActions)
            .flatMap(Collection::stream)
            .collect(Collectors.toList()))
        .build();

    persistedMacros.addElement(macroContainer);
    helperService.registerAction(macroContainer);
  }

  public void runSelected(AnActionEvent event) {
    var selectedIndices = anActionJBList.getSelectedIndices();
    var selectedItems = new ArrayList<MacroContainer>();

    for (int selectedIndex : selectedIndices) {
      selectedItems.add(persistedMacros.getElementAt(selectedIndex));
    }

    selectedItems.stream()
        .map(MacroContainer::getActions)
        .forEach(actions -> helperService.executeActions(actions, event));
  }

  public static MacroMagicPersistingService getInstance() {
    return ApplicationManager.getApplication()
        .getService(MacroMagicPersistingService.class);
  }

  public List<MacroContainer> getCurrentSelectedMacros() {
    var selectedIndices = anActionJBList.getSelectedIndices();
    var selectedItems = new ArrayList<MacroContainer>();

    for (int selectedIndex : selectedIndices) {
      selectedItems.add(persistedMacros.getElementAt(selectedIndex));
    }

    return selectedItems;
  }
}
