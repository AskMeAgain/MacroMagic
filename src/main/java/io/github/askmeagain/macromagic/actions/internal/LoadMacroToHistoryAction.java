package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.service.MacroManagementService;
import io.github.askmeagain.macromagic.service.HistoryManagementService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class LoadMacroToHistoryAction extends AnAction {

  private final MacroManagementService macroManagementService;
  private final HistoryManagementService historyManagementService;

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    var currentSelectMacros = macroManagementService.getCurrentSelectedMacros();
    historyManagementService.loadMacros(currentSelectMacros);
  }
}
