package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.service.MacroManagerService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class RunPersistedMacroAction extends AnAction implements MacroMagicInternal {

  private final MacroManagerService macroManagerService;

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    macroManagerService.runSelected(e);
  }
}
