package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.service.MacroManagementService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

public class DeleteMacroAction extends AnAction implements MacroMagicInternal {

  @Getter(lazy = true)
  private final MacroManagementService macroManagementService = MacroManagementService.getInstance();

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    getMacroManagementService().deleteSelected();
  }
}
