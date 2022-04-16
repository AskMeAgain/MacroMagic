package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.actions.ActionEventChanger;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Queue;

@RequiredArgsConstructor
public class QueueAction extends MacroMagicBaseAction {

  private final Queue<AnAction> queue;

  @Override
  public void actionPerformed(@NotNull AnActionEvent originalEvent) {
    var action = queue.remove();

    action.actionPerformed(originalEvent);

    if (action instanceof ActionEventChanger) {
      originalEvent = ((ActionEventChanger) action).getNewActionEvent();
    }

    final var newEvent = originalEvent;

    if (!queue.isEmpty()) {
      if (queue.peek() instanceof PressKeyAction) {
        getApplication().executeOnPooledThread(() -> {
          new QueueAction(queue).actionPerformed(newEvent);
        });
      } else {
        getApplication().invokeLater(() -> {
          new QueueAction(queue).actionPerformed(newEvent);
        });
      }
    }
  }
}
