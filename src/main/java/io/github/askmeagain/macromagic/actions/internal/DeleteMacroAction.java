package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.service.MacroMagicPersistingService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class DeleteMacroAction extends AnAction implements MacroMagicInternal {

  private final MacroMagicPersistingService macroMagicPersistingService;

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    macroMagicPersistingService.deleteSelected();
  }
}
