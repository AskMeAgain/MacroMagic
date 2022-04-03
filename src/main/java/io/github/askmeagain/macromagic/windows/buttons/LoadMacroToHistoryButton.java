package io.github.askmeagain.macromagic.windows.buttons;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.impl.ActionButton;
import io.github.askmeagain.macromagic.actions.internal.LoadMacroToHistoryAction;
import io.github.askmeagain.macromagic.service.MacroManagerService;
import io.github.askmeagain.macromagic.service.MacroMagicHistoryService;
import io.github.askmeagain.macromagic.windows.ToolUtils;

public class LoadMacroToHistoryButton extends ActionButton {
  public LoadMacroToHistoryButton(MacroManagerService macroManagerService, MacroMagicHistoryService macroMagicHistoryService) {
    super(
        new LoadMacroToHistoryAction(macroManagerService, macroMagicHistoryService),
        ToolUtils.getPresentation(AllIcons.Vcs.Branch),
        ActionPlaces.UNKNOWN,
        ActionToolbar.DEFAULT_MINIMUM_BUTTON_SIZE
    );
  }
}
