package io.github.askmeagain.macromagic;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.annotations.NotNull;

public class MacroTestAction extends AnAction {


  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {

    var service = ApplicationManager.getApplication().getService(MacroMagicState.class);

    if (service.isRunning()) {
      service.stopRunning();
    } else {
      service.setRunning();
    }
  }
}
