package io.github.askmeagain.macromagic.windows.buttons;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.impl.ActionButton;
import io.github.askmeagain.macromagic.actions.internal.RemoveEntryFromHistoryAction;
import io.github.askmeagain.macromagic.service.HistoryManagementService;
import io.github.askmeagain.macromagic.windows.ToolUtils;

public class RemoveEntryFromHistoryButton extends ActionButton {
  public RemoveEntryFromHistoryButton(HistoryManagementService historyManagementService) {
    super(
        new RemoveEntryFromHistoryAction(historyManagementService),
        ToolUtils.getPresentation(AllIcons.General.Remove),
        ActionPlaces.UNKNOWN,
        ActionToolbar.DEFAULT_MINIMUM_BUTTON_SIZE
    );
  }
}
