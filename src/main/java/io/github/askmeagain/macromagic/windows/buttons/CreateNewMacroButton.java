package io.github.askmeagain.macromagic.windows.buttons;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.impl.ActionButton;
import io.github.askmeagain.macromagic.actions.internal.CreateNewMacroAction;
import io.github.askmeagain.macromagic.service.MacroMagicHistoryService;
import io.github.askmeagain.macromagic.windows.ToolUtils;

public class CreateNewMacroButton extends ActionButton {
  public CreateNewMacroButton(MacroMagicHistoryService macroMagicHistoryService) {
    super(
        new CreateNewMacroAction(macroMagicHistoryService),
        ToolUtils.getPresentation(AllIcons.Actions.MenuSaveall),
        ActionPlaces.UNKNOWN,
        ActionToolbar.DEFAULT_MINIMUM_BUTTON_SIZE
    );
  }
}
