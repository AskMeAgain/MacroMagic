package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.actions.ActionEventChanger;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.stream.Collectors;

@AllArgsConstructor
public class QueueAction extends MacroMagicBaseAction {

  private Queue<AnAction> queue;

  @Override
  public void actionPerformed(@NotNull AnActionEvent originalEvent) {
    var action = queue.remove();

    if (action instanceof ExecuteMacroAction) {
      var actions = ((ExecuteMacroAction) action).getMacroContainer().getActions()
          .stream()
          .map(getHelperService()::deserializeAction)
          .collect(Collectors.toCollection(ArrayDeque::new));

      actions.addAll(queue);
      queue = actions;
    } else {
      action.actionPerformed(originalEvent);

      if (action instanceof ActionEventChanger) {
        originalEvent = ((ActionEventChanger) action).getNewActionEvent();
      }
    }

    final var newEvent = originalEvent;

    if (!queue.isEmpty()) {
      if (queue.peek() instanceof PressKeyAction) {
        getApplication().executeOnPooledThread(() -> new QueueAction(queue).actionPerformed(newEvent));
      } else {
        getApplication().invokeLater(() -> new QueueAction(queue).actionPerformed(newEvent));
      }
    }
  }
}
