package io.github.askmeagain.macromagic.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class SingleActionWrapper extends MacroMagicBaseAction {

  private final AnAction action;
  private final AnAction nextAction;

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    action.actionPerformed(e);

    if (nextAction != null) {
      getApplication().invokeLater(() -> nextAction.actionPerformed(e));
    }
  }
}
