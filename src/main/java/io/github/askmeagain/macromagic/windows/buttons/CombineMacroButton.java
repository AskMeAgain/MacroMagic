package io.github.askmeagain.macromagic.windows.buttons;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.impl.ActionButton;
import io.github.askmeagain.macromagic.actions.internal.CombineMacroAction;
import io.github.askmeagain.macromagic.actions.internal.DeleteMacroAction;
import io.github.askmeagain.macromagic.service.MacroMagicPersistingService;
import io.github.askmeagain.macromagic.windows.ToolUtils;

public class CombineMacroButton extends ActionButton {
  public CombineMacroButton(MacroMagicPersistingService macroMagicPersistingService) {
    super(
        new CombineMacroAction(macroMagicPersistingService),
        ToolUtils.getPresentation(AllIcons.Vcs.Merge),
        ActionPlaces.UNKNOWN,
        ActionToolbar.DEFAULT_MINIMUM_BUTTON_SIZE
    );
  }
}