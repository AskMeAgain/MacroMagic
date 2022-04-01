package io.github.askmeagain.macromagic.windows.buttons;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.impl.ActionButton;
import io.github.askmeagain.macromagic.actions.internal.DeleteMacroAction;
import io.github.askmeagain.macromagic.actions.internal.MoveActionUpAction;
import io.github.askmeagain.macromagic.service.MacroMagicPersistingService;
import io.github.askmeagain.macromagic.service.MacroMagicService;
import io.github.askmeagain.macromagic.windows.ToolUtils;

public class MoveActionUpButton extends ActionButton {
  public MoveActionUpButton(MacroMagicService macroMagicService) {
    super(
        new MoveActionUpAction(macroMagicService),
        ToolUtils.getPresentation(AllIcons.Actions.MoveUp),
        ActionPlaces.UNKNOWN,
        ActionToolbar.DEFAULT_MINIMUM_BUTTON_SIZE
    );
  }
}
