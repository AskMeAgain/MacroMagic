package io.github.askmeagain.macromagic.service;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.ui.components.JBList;
import io.github.askmeagain.macromagic.actions.internal.PressKeyAction;
import io.github.askmeagain.macromagic.entities.MacroContainer;
import io.github.askmeagain.macromagic.entities.PersistedActionDto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public final class MacroMagicPersistingService {

  private final ActionManager actionManager = ActionManager.getInstance();

  @Getter
  private final DefaultListModel<MacroContainer> persistedMacros = new DefaultListModel<>();

  @Getter
  private final JBList<MacroContainer> anActionJBList;

  public MacroMagicPersistingService() {
    anActionJBList = new JBList<>(persistedMacros);
  }

  public void persistActions(List<AnAction> actions, String name) {
    var result = actions.stream()
        .map(this::serializeAction)
        .collect(Collectors.toList());

    log.info("'{}' got persisted: {}", name, result);
    persistedMacros.addElement(MacroContainer.builder()
        .macroName(name)
        .actions(result)
        .build());
  }

  public List<AnAction> loadActions(String name) {
    var list = List.of(
        PersistedActionDto.builder()
            .actionId("io.github.askmeagain.macromagic.actions.internal.PressKeyAction")
            .additionalInformation('a')
            .build(),
        PersistedActionDto.builder()
            .actionId("io.github.askmeagain.macromagic.actions.internal.PressKeyAction")
            .additionalInformation('a')
            .build()
    );

    return list.stream()
        .map(this::deserializeAction)
        .collect(Collectors.toList());
  }

  public AnAction deserializeAction(PersistedActionDto persistedActionDto) {
    var action = actionManager.getAction(persistedActionDto.getActionId());

    if (action instanceof PressKeyAction) {
      var castedAction = (PressKeyAction) action;
      castedAction.setKeyCode(persistedActionDto.getAdditionalInformation());
      return castedAction;
    }

    return action;
  }

  public PersistedActionDto serializeAction(AnAction anAction) {
    if (anAction instanceof PressKeyAction) {
      var castedAction = (PressKeyAction) anAction;
      return PersistedActionDto.builder()
          .actionId("io.github.askmeagain.macromagic.actions.internal.PressKeyAction")
          .isInternal(true)
          .additionalInformation(castedAction.getOriginalChar())
          .build();
    }

    return PersistedActionDto.builder()
        .isInternal(false)
        .actionId(actionManager.getId(anAction))
        .build();
  }

  public static MacroMagicPersistingService getInstance() {
    return ApplicationManager.getApplication()
        .getService(MacroMagicPersistingService.class);
  }

  public void deleteSelected() {
    var selectedIndices = anActionJBList.getSelectedIndices();
    var selectedItems = new ArrayList<MacroContainer>();

    for (int selectedIndex : selectedIndices) {
      selectedItems.add(persistedMacros.getElementAt(selectedIndex));
    }

    selectedItems.forEach(persistedMacros::removeElement);
  }

  public void combineSelected(String name) {
    var selectedIndices = anActionJBList.getSelectedIndices();
    var selectedItems = new ArrayList<MacroContainer>();

    for (int selectedIndex : selectedIndices) {
      selectedItems.add(persistedMacros.getElementAt(selectedIndex));
    }

    selectedItems.forEach(persistedMacros::removeElement);

    var macroContainer = MacroContainer.builder()
        .macroName(name)
        .actions(selectedItems.stream()
            .map(MacroContainer::getActions)
            .flatMap(Collection::stream)
            .collect(Collectors.toList()))
        .build();

    persistedMacros.addElement(macroContainer);
  }

  public void runSelected(AnActionEvent event) {
    var selectedIndices = anActionJBList.getSelectedIndices();
    var selectedItems = new ArrayList<MacroContainer>();

    for (int selectedIndex : selectedIndices) {
      selectedItems.add(persistedMacros.getElementAt(selectedIndex));
    }

    selectedItems.stream()
        .map(MacroContainer::getActions)
        .flatMap(Collection::stream)
        .map(this::deserializeAction)
        .peek(x -> System.out.println("Got: " + x))
        .forEach(x -> x.actionPerformed(event));
  }
}
