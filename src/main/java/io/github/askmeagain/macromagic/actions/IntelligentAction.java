package io.github.askmeagain.macromagic.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ModalityState;
import io.github.askmeagain.macromagic.actions.internal.MacroMagicInternal;
import org.jetbrains.annotations.NotNull;

public class IntelligentAction extends MacroMagicBaseAction implements MacroMagicInternal {

  private final AnAction action;
  private final AnAction nextAction;

  public IntelligentAction(AnAction action) {
    this(action, null);
  }

  public IntelligentAction(AnAction action, AnAction nextAction) {
    this.action = action;
    this.nextAction = nextAction;
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    action.actionPerformed(e);

    if (nextAction != null) {
      getApplication().invokeLater(() -> nextAction.actionPerformed(e), ModalityState.current());
    }
  }

  public IntelligentAction withNextAction(AnAction newAction) {
    return new IntelligentAction(this, newAction);
  }

  public IntelligentAction withBeforeAction(AnAction newAction) {
    return new IntelligentAction(newAction, this);
  }

  @Override
  public String toString() {
    if (nextAction == null) {
      return action.getTemplateText();
    }

    return action.toString() + " (" + nextAction.toString() + ")";
  }
}
