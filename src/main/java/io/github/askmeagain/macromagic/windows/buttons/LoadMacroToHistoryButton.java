package io.github.askmeagain.macromagic.windows.buttons;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.impl.ActionButton;
import io.github.askmeagain.macromagic.actions.internal.CombineMacroAction;
import io.github.askmeagain.macromagic.actions.internal.LoadMacroToHistoryAction;
import io.github.askmeagain.macromagic.service.MacroMagicPersistingService;
import io.github.askmeagain.macromagic.service.MacroMagicService;
import io.github.askmeagain.macromagic.windows.ToolUtils;

public class LoadMacroToHistoryButton extends ActionButton {
  public LoadMacroToHistoryButton(MacroMagicPersistingService macroMagicPersistingService, MacroMagicService macroMagicService) {
    super(
        new LoadMacroToHistoryAction(macroMagicPersistingService, macroMagicService),
        ToolUtils.getPresentation(AllIcons.Vcs.Branch),
        ActionPlaces.UNKNOWN,
        ActionToolbar.DEFAULT_MINIMUM_BUTTON_SIZE
    );
  }
}
