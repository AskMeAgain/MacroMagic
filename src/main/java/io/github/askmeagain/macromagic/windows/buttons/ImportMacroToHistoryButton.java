package io.github.askmeagain.macromagic.windows.buttons;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.impl.ActionButton;
import io.github.askmeagain.macromagic.actions.internal.ExtractMacroToHistoryAction;
import io.github.askmeagain.macromagic.actions.internal.ImportMacroToHistoryAction;
import io.github.askmeagain.macromagic.windows.ToolUtils;

public class ImportMacroToHistoryButton extends ActionButton {
  public ImportMacroToHistoryButton() {
    super(
        new ImportMacroToHistoryAction(),
        ToolUtils.getPresentation(AllIcons.Actions.Upload, "Import MacroAction To History"),
        ActionPlaces.UNKNOWN,
        ActionToolbar.DEFAULT_MINIMUM_BUTTON_SIZE
    );
  }
}
