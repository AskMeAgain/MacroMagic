package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.service.HistoryManagementService;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;

public class StartStopRecordingAction extends AnAction implements MacroMagicInternal {

  @Getter(lazy = true)
  private final HistoryManagementService historyManagementService = HistoryManagementService.getInstance();

  public static Icon PLAY = AllIcons.Actions.Resume;
  public static Icon STOP = AllIcons.Actions.Suspend;

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    var isRunning = getHistoryManagementService().startStopRecording();

    var icon = isRunning ? STOP : PLAY;
    e.getPresentation().setIcon(icon);
  }
}