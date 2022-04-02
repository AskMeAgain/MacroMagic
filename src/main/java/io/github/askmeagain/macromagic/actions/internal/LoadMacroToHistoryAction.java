package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.service.MacroMagicPersistingService;
import io.github.askmeagain.macromagic.service.MacroMagicService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class LoadMacroToHistoryAction extends AnAction {

  private final MacroMagicPersistingService macroMagicPersistingService;
  private final MacroMagicService macroMagicService;

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    var currentSelectMacros = macroMagicPersistingService.getCurrentSelectedMacros();
    macroMagicService.loadMacros(currentSelectMacros);
  }
}
