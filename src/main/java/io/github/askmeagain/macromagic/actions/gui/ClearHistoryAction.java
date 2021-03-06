package io.github.askmeagain.macromagic.actions.gui;

import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.actions.MacroMagicInternal;
import io.github.askmeagain.macromagic.actions.internal.MacroMagicBaseAction;
import org.jetbrains.annotations.NotNull;

public class ClearHistoryAction extends MacroMagicBaseAction implements MacroMagicInternal {

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    getHistoryManagementService().clearHistory();
  }
}
