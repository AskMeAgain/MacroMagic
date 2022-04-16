package io.github.askmeagain.macromagic.actions.utils;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.TextRange;
import io.github.askmeagain.macromagic.actions.internal.MacroMagicBaseAction;
import org.jetbrains.annotations.NotNull;

public class PlaceCursorAtTopAction extends MacroMagicBaseAction {
  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    selectWord(new TextRange(0, 0), focusEditor(e));
  }
}
