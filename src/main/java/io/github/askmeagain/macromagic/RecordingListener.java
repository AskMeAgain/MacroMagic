package io.github.askmeagain.macromagic;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecordingListener implements AnActionListener {

  private final List<AnAction> actions;

  public RecordingListener(List<AnAction> actions) {
    this.actions = actions;
    System.out.println("Registered Listener!");
  }

  @Override
  public void beforeActionPerformed(@NotNull AnAction action, @NotNull AnActionEvent event) {
    if (!(action instanceof RecordMacroAction)) {
      System.out.println(action.getTemplateText() + " << from listener");
      actions.add(action);
    }
  }
}
