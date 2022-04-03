package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.service.HistoryManagementService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

public class ClearHistoryAction extends AnAction implements MacroMagicInternal {

  @Getter(lazy = true)
  private final HistoryManagementService historyManagementService = HistoryManagementService.getInstance();

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    getHistoryManagementService().clearHistory();
  }
}
