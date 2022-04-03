package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.service.MacroMagicHistoryService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class RemoveEntryFromHistoryAction extends AnAction implements MacroMagicInternal {

  private final MacroMagicHistoryService macroMagicHistoryService;

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    macroMagicHistoryService.removeSelectedIndices();
  }
}
