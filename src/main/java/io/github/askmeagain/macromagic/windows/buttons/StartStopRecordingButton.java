package io.github.askmeagain.macromagic.windows.buttons;

import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.impl.ActionButton;
import io.github.askmeagain.macromagic.actions.internal.StartStopRecordingAction;
import io.github.askmeagain.macromagic.service.MacroMagicHistoryService;
import io.github.askmeagain.macromagic.windows.ToolUtils;

public class StartStopRecordingButton extends ActionButton {
  public StartStopRecordingButton(MacroMagicHistoryService macroMagicHistoryService) {
    super(
        new StartStopRecordingAction(macroMagicHistoryService),
        ToolUtils.getPresentation(StartStopRecordingAction.STOP),
        ActionPlaces.UNKNOWN,
        ActionToolbar.DEFAULT_MINIMUM_BUTTON_SIZE
    );
  }
}
