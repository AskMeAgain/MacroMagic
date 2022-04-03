package io.github.askmeagain.macromagic.windows.buttons;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.impl.ActionButton;
import io.github.askmeagain.macromagic.actions.internal.DeleteMacroAction;
import io.github.askmeagain.macromagic.service.MacroManagerService;
import io.github.askmeagain.macromagic.windows.ToolUtils;

public class DeleteMacroButton extends ActionButton {
  public DeleteMacroButton(MacroManagerService macroManagerService) {
    super(
        new DeleteMacroAction(macroManagerService),
        ToolUtils.getPresentation(AllIcons.Actions.GC),
        ActionPlaces.UNKNOWN,
        ActionToolbar.DEFAULT_MINIMUM_BUTTON_SIZE
    );
  }
}
