package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.service.HistoryManagementService;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public class MoveActionUpAction extends AnAction implements MacroMagicInternal {

  @Getter(lazy = true)
  private final HistoryManagementService historyManagementService = HistoryManagementService.getInstance();

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    getHistoryManagementService().moveSelectionUp();
  }
}
