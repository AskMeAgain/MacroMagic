package io.github.askmeagain.macromagic.windows.buttons;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.impl.ActionButton;
import io.github.askmeagain.macromagic.actions.internal.ClearHistoryAction;
import io.github.askmeagain.macromagic.service.MacroMagicService;
import io.github.askmeagain.macromagic.windows.ToolUtils;

public class ClearHistoryButton extends ActionButton {
  public ClearHistoryButton(MacroMagicService macroMagicService) {
    super(
        new ClearHistoryAction(macroMagicService),
        ToolUtils.getPresentation(AllIcons.Actions.GC),
        ActionPlaces.UNKNOWN,
        ActionToolbar.DEFAULT_MINIMUM_BUTTON_SIZE
    );
  }
}
