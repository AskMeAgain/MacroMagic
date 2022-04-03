package io.github.askmeagain.macromagic.windows.buttons;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.impl.ActionButton;
import io.github.askmeagain.macromagic.actions.internal.MoveActionDownAction;
import io.github.askmeagain.macromagic.service.MacroMagicHistoryService;
import io.github.askmeagain.macromagic.windows.ToolUtils;

public class MoveActionDownButton extends ActionButton {
  public MoveActionDownButton(MacroMagicHistoryService macroMagicHistoryService) {
    super(
        new MoveActionDownAction(macroMagicHistoryService),
        ToolUtils.getPresentation(AllIcons.Actions.MoveDown),
        ActionPlaces.UNKNOWN,
        ActionToolbar.DEFAULT_MINIMUM_BUTTON_SIZE
    );
  }
}
