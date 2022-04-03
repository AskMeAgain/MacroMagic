package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.service.MacroManagementService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class RunPersistedMacroAction extends AnAction implements MacroMagicInternal {

  private final MacroManagementService macroManagementService;

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    macroManagementService.runSelected(e);
  }
}
