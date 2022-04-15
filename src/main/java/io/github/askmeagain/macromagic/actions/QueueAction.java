package io.github.askmeagain.macromagic.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Queue;

@RequiredArgsConstructor
public class QueueAction extends MacroMagicBaseAction {

  private final Queue<AnAction> deque;

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    var action = deque.remove();
    System.out.println("Performing at: " + Instant.now() + " -> " + action.toString());

    action.actionPerformed(e);

    if (action instanceof ActionEventChanger) {
      var newActionEvent = ((ActionEventChanger) action).getNewActionEvent();
      e = newActionEvent;
    }

    final var newEvent = e;

    if (!deque.isEmpty()) {
      if (deque.peek() instanceof PressKeyAction) {
        getApplication().executeOnPooledThread(() -> {
          new QueueAction(deque).actionPerformed(newEvent);
        });
      } else {
        getApplication().invokeLater(() -> {
          new QueueAction(deque).actionPerformed(newEvent);
        });
      }
    }
  }
}
