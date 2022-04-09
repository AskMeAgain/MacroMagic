package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.actions.MacroMagicBaseAction;
import org.jetbrains.annotations.NotNull;

public class DeleteMacroAction extends MacroMagicBaseAction implements MacroMagicInternal {
  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    getMacroManagementService().deleteSelected();
  }
}
