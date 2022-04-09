package io.github.askmeagain.macromagic.service;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.ui.components.JBList;
import io.github.askmeagain.macromagic.entities.MacroContainer;
import io.github.askmeagain.macromagic.entities.MacroMagicState;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.DefaultListModel;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public final class MacroManagementService {

  @Getter(lazy = true)
  private final HelperService helperService = HelperService.getInstance();

  @Getter
  private final DefaultListModel<MacroContainer> persistedMacros = new DefaultListModel<>();

  @Getter
  private final JBList<MacroContainer> anActionJbList;
  private MacroMagicState state = PersistenceManagementService.getInstance().getState();

  public MacroManagementService() {
    state.getMacros().forEach(getHelperService()::registerAction);
    persistedMacros.addAll(state.getMacros());
    anActionJbList = new JBList<>(persistedMacros);
    anActionJbList.setDragEnabled(true);
  }

  public void persistActions(List<AnAction> actions, String name) {
    var result = actions.stream()
        .map(getHelperService()::serializeAction)
        .collect(Collectors.toList());

    log.info("'{}' got persisted: {}", name, result);
    var macroContainer = new MacroContainer(name, result);

    for (var i = 0; i < persistedMacros.size(); i++) {
      if (persistedMacros.get(i).getMacroName().equals(name)) {
        persistedMacros.remove(i);
        state.getMacros().removeIf(x -> x.getMacroName().equals(name));
        getHelperService().unregisterAction(macroContainer);
      }
    }

    persistedMacros.addElement(macroContainer);
    getHelperService().registerAction(macroContainer);
    state.getMacros().add(macroContainer);
    anActionJbList.setSelectedIndex(persistedMacros.size() - 1);
  }

  public void deleteSelected() {
    getCurrentSelectedMacros().forEach(macroContainer -> {
      persistedMacros.removeElement(macroContainer);
      state.getMacros().remove(macroContainer);
      getHelperService().unregisterAction(macroContainer);
    });
  }

  public void combineSelected(String name) {
    var selectedItems = getCurrentSelectedMacros();

    selectedItems.forEach(persistedMacros::removeElement);

    var macroContainer = new MacroContainer(
        name,
        selectedItems.stream()
            .map(MacroContainer::getActions)
            .flatMap(Collection::stream)
            .collect(Collectors.toList())
    );

    persistedMacros.addElement(macroContainer);
    getHelperService().registerAction(macroContainer);
    state.getMacros().add(macroContainer);
  }

  public void runSelected(AnActionEvent e) {
    getCurrentSelectedMacros()
        .stream()
        .map(MacroContainer::getActions)
        .forEach(actions -> getHelperService().executeActions(actions, e));
  }

  public static MacroManagementService getInstance() {
    return ApplicationManager.getApplication()
        .getService(MacroManagementService.class);
  }

  public List<MacroContainer> getCurrentSelectedMacros() {
    return anActionJbList.getSelectedValuesList();
  }

  public MacroContainer getMacro(String macroName) {
    for (int i = 0; i < persistedMacros.size(); i++) {
      var macroContainer = persistedMacros.get(i);
      if (macroContainer.getMacroName().equals(macroName)) {
        return macroContainer;
      }
    }

    throw new RuntimeException("Not found");
  }
}
