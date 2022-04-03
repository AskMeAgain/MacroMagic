package io.github.askmeagain.macromagic.windows.buttons;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.impl.ActionButton;
import io.github.askmeagain.macromagic.actions.internal.ExtractMacroToHistoryAction;
import io.github.askmeagain.macromagic.windows.ToolUtils;

public class ExtractMacroToHistoryButton extends ActionButton {
  public ExtractMacroToHistoryButton() {
    super(
        new ExtractMacroToHistoryAction(),
        ToolUtils.getPresentation(AllIcons.Vcs.Branch, "Extract Macro To History"),
        ActionPlaces.UNKNOWN,
        ActionToolbar.DEFAULT_MINIMUM_BUTTON_SIZE
    );
  }
}
