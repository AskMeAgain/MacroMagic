package io.github.askmeagain.macromagic.windows.buttons;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.impl.ActionButton;
import io.github.askmeagain.macromagic.actions.internal.RunPersistedMacroAction;
import io.github.askmeagain.macromagic.windows.ToolUtils;

public class RunMacroButton extends ActionButton {
  public RunMacroButton() {
    super(
        new RunPersistedMacroAction(),
        ToolUtils.getPresentation(AllIcons.Actions.Execute, "Run Selected Macros"),
        ActionPlaces.UNKNOWN,
        ActionToolbar.DEFAULT_MINIMUM_BUTTON_SIZE
    );
  }
}
