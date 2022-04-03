package io.github.askmeagain.macromagic.windows.buttons;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.impl.ActionButton;
import io.github.askmeagain.macromagic.actions.internal.MoveActionDownAction;
import io.github.askmeagain.macromagic.service.HistoryManagementService;
import io.github.askmeagain.macromagic.windows.ToolUtils;

public class MoveActionDownButton extends ActionButton {
  public MoveActionDownButton() {
    super(
        new MoveActionDownAction(),
        ToolUtils.getPresentation(AllIcons.Actions.MoveDown, "Move Selected Actions Down"),
        ActionPlaces.UNKNOWN,
        ActionToolbar.DEFAULT_MINIMUM_BUTTON_SIZE
    );
  }
}
