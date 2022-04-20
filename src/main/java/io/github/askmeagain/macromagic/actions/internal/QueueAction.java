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
  private int currentNestedDepth;
  private int maxNestedDepth;

  @Override
  public void actionPerformed(@NotNull AnActionEvent originalEvent) {
    var action = queue.remove();

    if (currentNestedDepth > maxNestedDepth) {
      //do nothing
    } else if (action instanceof ExecuteMacroAction) {
      var actions = ((ExecuteMacroAction) action).getMacroContainer().getActions()
          .stream()
          .map(getHelperService()::deserializeAction)
          .collect(Collectors.toCollection(ArrayDeque::new));

      actions.addAll(queue);
      queue = actions;
    } else {
      //editor key collapsing
      while (action instanceof EditorKeyInputAction && queue.peek() instanceof EditorKeyInputAction) {
        var next = queue.remove();
        action = ((EditorKeyInputAction) action).merge((EditorKeyInputAction) next);
      }

      action.actionPerformed(originalEvent);

      if (action instanceof ActionEventChanger) {
        originalEvent = ((ActionEventChanger) action).getNewActionEvent();
      }
    }

    final var newEvent = originalEvent;

    if (!queue.isEmpty()) {
      if (queue.peek() instanceof PressKeyAction) {
        getApplication().executeOnPooledThread(() -> new QueueAction(queue, currentNestedDepth + 1, maxNestedDepth)
            .actionPerformed(newEvent));
      } else {
        getApplication().invokeLater(() -> new QueueAction(queue, currentNestedDepth + 1, maxNestedDepth)
            .actionPerformed(newEvent));
      }
    }
  }
}
