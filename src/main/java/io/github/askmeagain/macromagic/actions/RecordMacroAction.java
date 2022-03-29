package io.github.askmeagain.macromagic.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import io.github.askmeagain.macromagic.MacroMagicService;
import org.jetbrains.annotations.NotNull;

public class RecordMacroAction extends AnAction {

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {

    var service = ApplicationManager.getApplication().getService(MacroMagicService.class);

    if (service.isRunning()) {
      service.stopRunning();
    } else {
      service.setRunning();
    }
  }
}
