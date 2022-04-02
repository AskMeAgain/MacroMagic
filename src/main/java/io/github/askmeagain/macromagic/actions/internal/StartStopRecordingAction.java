package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.service.MacroMagicService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

@RequiredArgsConstructor
public class StartStopRecordingAction extends AnAction implements MacroMagicInternal {

  private final MacroMagicService macroMagicService;

  public static Icon PLAY = AllIcons.Actions.Resume;
  public static Icon STOP = AllIcons.Actions.Suspend;

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    var isRunning = macroMagicService.startStopRecording();

    var icon = isRunning ? STOP : PLAY;
    e.getPresentation().setIcon(icon);
  }
}