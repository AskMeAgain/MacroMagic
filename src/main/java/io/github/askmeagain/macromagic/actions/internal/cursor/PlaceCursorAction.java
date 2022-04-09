package io.github.askmeagain.macromagic.actions.internal.cursor;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.TextRange;
import io.github.askmeagain.macromagic.actions.MacroMagicBaseAction;
import org.jetbrains.annotations.NotNull;

public class PlaceCursorAction extends MacroMagicBaseAction {
  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    selectWord(new TextRange(0, 0), e);
  }
}
