package io.github.askmeagain.macromagic;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import org.jetbrains.annotations.NotNull;

public class CustomActionListener implements AnActionListener {

  public CustomActionListener() {
    System.out.println("Registered Listener!");
  }

  @Override
  public void beforeActionPerformed(@NotNull AnAction action, @NotNull AnActionEvent event) {
    System.out.println(action.getTemplateText() + " << from listener");
  }
}
