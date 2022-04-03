package io.github.askmeagain.macromagic.windows.buttons;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.impl.ActionButton;
import io.github.askmeagain.macromagic.actions.internal.LoadMacroToHistoryAction;
import io.github.askmeagain.macromagic.service.MacroManagementService;
import io.github.askmeagain.macromagic.service.HistoryManagementService;
import io.github.askmeagain.macromagic.windows.ToolUtils;

public class LoadMacroToHistoryButton extends ActionButton {
  public LoadMacroToHistoryButton() {
    super(
        new LoadMacroToHistoryAction(),
        ToolUtils.getPresentation(AllIcons.Vcs.Branch),
        ActionPlaces.UNKNOWN,
        ActionToolbar.DEFAULT_MINIMUM_BUTTON_SIZE
    );
  }
}
