package io.github.askmeagain.macromagic.windows.buttons;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.impl.ActionButton;
import io.github.askmeagain.macromagic.actions.internal.MoveActionUpAction;
import io.github.askmeagain.macromagic.actions.internal.RegisterShortcutAction;
import io.github.askmeagain.macromagic.windows.ToolUtils;

public class RegisterShortcutButton extends ActionButton {
  public RegisterShortcutButton() {
    super(
        new RegisterShortcutAction(),
        ToolUtils.getPresentation(AllIcons.General.Settings, "Register Shortcut"),
        ActionPlaces.UNKNOWN,
        ActionToolbar.DEFAULT_MINIMUM_BUTTON_SIZE
    );
  }
}
