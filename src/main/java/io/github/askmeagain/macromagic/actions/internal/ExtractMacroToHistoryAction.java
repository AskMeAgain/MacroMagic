package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.actions.MacroMagicBaseAction;
import org.jetbrains.annotations.NotNull;

public class ExtractMacroToHistoryAction extends MacroMagicBaseAction implements MacroMagicInternal {

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    var currentSelectMacros = getMacroManagementService().getCurrentSelectedMacros();
    getHistoryManagementService().importMacro(currentSelectMacros);
  }
}
