package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.entities.MacroContainer;
import io.github.askmeagain.macromagic.service.HelperService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class ExecuteMacroAction extends AnAction {

  private final MacroContainer macroContainer;
  private final HelperService helperService = HelperService.getInstance();

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    helperService.executeActions(macroContainer.getActions(), e);
  }
}
