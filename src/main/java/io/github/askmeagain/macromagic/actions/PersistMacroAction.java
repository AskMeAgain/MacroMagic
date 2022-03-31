package io.github.askmeagain.macromagic.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import io.github.askmeagain.macromagic.service.MacroMagicService;
import org.jetbrains.annotations.NotNull;

public class PersistMacroAction extends AnAction {

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    ApplicationManager.getApplication()
        .getService(MacroMagicService.class)
        .persist();
  }
}
