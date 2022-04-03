package io.github.askmeagain.macromagic.service;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.extensions.PluginId;
import io.github.askmeagain.macromagic.actions.internal.ExecuteMacroAction;
import io.github.askmeagain.macromagic.actions.internal.PressKeyAction;
import io.github.askmeagain.macromagic.entities.MacroContainer;
import io.github.askmeagain.macromagic.entities.PersistedActionDto;

import java.util.List;

@Service
public final class HelperService {

  private static final String macroActionPrefix = "MacroAction-";

  private final PluginId pluginId = PluginId.findId("io.github.askmeagain.macromagic.MacroMagic");

  private final ActionManager actionManager = ActionManager.getInstance();

  public static HelperService getInstance() {
    return ApplicationManager.getApplication().getService(HelperService.class);
  }

  public AnAction deserializeAction(PersistedActionDto persistedActionDto, MacroManagementService macroManagementService) {
    var action = actionManager.getAction(persistedActionDto.getActionId());

    if (action instanceof PressKeyAction) {
      var castedAction = (PressKeyAction) action;
      castedAction.setOriginalString(persistedActionDto.getAdditionalInformation());
      return castedAction;
    }/* else if (action instanceof ExecuteMacroAction) {
      var castedAction = (ExecuteMacroAction) action;
      castedAction.setMacroContainer(macroManagerService.getMacro(persistedActionDto.getAdditionalInformation()));
      return castedAction;
    } */

    return action;
  }

  public PersistedActionDto serializeAction(AnAction anAction) {
    if (anAction instanceof PressKeyAction) {
      var castedAction = (PressKeyAction) anAction;
      return new PersistedActionDto(
          "io.github.askmeagain.macromagic.actions.internal.PressKeyAction",
          castedAction.getOriginalString()
      );
    } /*else if (anAction instanceof ExecuteMacroAction) {
      var castedAction = (ExecuteMacroAction) anAction;
      return new PersistedActionDto(
          "io.github.askmeagain.macromagic.actions.internal.ExecuteMacroAction",
          castedAction.getMacroContainer().getMacroName()
      );
    } */

    return new PersistedActionDto(actionManager.getId(anAction), null);
  }

  public void executeActions(List<PersistedActionDto> actions, MacroManagementService macroManagementService, AnActionEvent e) {
    actions.stream()
        .map(persistedActionDto -> deserializeAction(persistedActionDto, macroManagementService))
        .forEach(x -> x.actionPerformed(e));
  }

  public void unregisterAction(MacroContainer removedMacro) {
    actionManager.unregisterAction(macroActionPrefix + removedMacro.getMacroName());
  }

  public void registerAction(MacroContainer macroContainer) {
    var macroAction = new ExecuteMacroAction(macroContainer);
    actionManager.registerAction(macroActionPrefix + macroContainer.getMacroName(), macroAction, pluginId);
  }

  public AnAction createMacroAction(MacroContainer macroContainer) {
    return actionManager.getAction(macroActionPrefix + macroContainer.getMacroName());
  }
}
