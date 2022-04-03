package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.service.MacroManagerService;
import io.github.askmeagain.macromagic.service.MacroMagicHistoryService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class LoadMacroToHistoryAction extends AnAction {

  private final MacroManagerService macroManagerService;
  private final MacroMagicHistoryService macroMagicHistoryService;

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    var currentSelectMacros = macroManagerService.getCurrentSelectedMacros();
    macroMagicHistoryService.loadMacros(currentSelectMacros);
  }
}
