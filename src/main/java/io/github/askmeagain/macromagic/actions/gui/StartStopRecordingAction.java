package io.github.askmeagain.macromagic.actions.gui;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.actions.MacroMagicInternal;
import io.github.askmeagain.macromagic.actions.internal.MacroMagicBaseAction;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;

public class StartStopRecordingAction extends MacroMagicBaseAction implements MacroMagicInternal {

  public static Icon PLAY = AllIcons.Actions.Resume;
  public static Icon STOP = AllIcons.Actions.Suspend;
  public static String PLAY_TEXT = "Start recording";
  public static String STOP_TEXT = "Stop recording";

  public void update(@NotNull AnActionEvent e) {
    setIcon(e);
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    getHistoryManagementService().startStopRecording();
    setIcon(e);
  }

  private void setIcon(@NotNull AnActionEvent e) {
    var isRunning = getHistoryManagementService().isRunning();
    var icon = isRunning ? STOP : PLAY;
    var description = isRunning ? STOP_TEXT : PLAY_TEXT;
    var presentation = e.getPresentation();

    presentation.setIcon(icon);
    presentation.setText(description);
  }
}