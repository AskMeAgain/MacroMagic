package io.github.askmeagain.macromagic.service;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.extensions.PluginId;
import io.github.askmeagain.macromagic.actions.ExecuteMacroAction;
import io.github.askmeagain.macromagic.actions.PressKeyAction;
import io.github.askmeagain.macromagic.entities.MacroContainer;
import io.github.askmeagain.macromagic.entities.PersistedActionDto;
import lombok.Getter;

@Service
public final class HelperService {

  public static final String macroActionPrefix = "MacroAction-";

  private final PluginId pluginId = PluginId.findId("io.github.askmeagain.macromagic.MacroMagic");

  @Getter(lazy = true)
  private final ActionManager actionManager = ActionManager.getInstance();

  public static HelperService getInstance() {
    return ApplicationManager.getApplication().getService(HelperService.class);
  }

  public AnAction deserializeAction(PersistedActionDto persistedActionDto) {
    if (persistedActionDto.getActionId().equals("io.github.askmeagain.macromagic.actions.PressKeyAction")) {
      var castedAction = new PressKeyAction();
      var additionalInformation = persistedActionDto.getAdditionalInformation();
      castedAction.setOriginalString(additionalInformation.substring(4));
      castedAction.setInEditor(additionalInformation.charAt(0) == '1');
      return castedAction;
    }

    return getActionManager().getAction(persistedActionDto.getActionId());
  }

  public PersistedActionDto serializeAction(AnAction anAction) {
    if (anAction instanceof PressKeyAction) {
      var castedAction = (PressKeyAction) anAction;
      return new PersistedActionDto(
          "io.github.askmeagain.macromagic.actions.PressKeyAction",
          (castedAction.isInEditor() ? 1 : 0) + "###" + castedAction.getOriginalString()
      );
    }

    return new PersistedActionDto(getActionManager().getId(anAction), null);
  }

  public void unregisterAction(MacroContainer removedMacro) {
    getActionManager().unregisterAction(macroActionPrefix + removedMacro.getMacroName());
  }

  public void registerAction(MacroContainer macroContainer) {
    var macroAction = new ExecuteMacroAction(macroContainer);

    var macroMagicGroup = (DefaultActionGroup) getActionManager()
        .getAction("io.github.askmeagain.macromagic.actions.groups.refactoring");
    getActionManager().registerAction(macroActionPrefix + macroContainer.getMacroName(), macroAction, pluginId);

    macroMagicGroup.addAction(macroAction);
  }

  public AnAction createMacroAction(MacroContainer macroContainer) {
    return getActionManager().getAction(macroActionPrefix + macroContainer.getMacroName());
  }
}
