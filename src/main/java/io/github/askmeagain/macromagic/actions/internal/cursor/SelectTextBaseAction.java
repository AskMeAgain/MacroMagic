package io.github.askmeagain.macromagic.actions.internal.cursor;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretState;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class SelectTextBaseAction extends AnAction {

  @NotNull
  protected PsiFile getPsiFile(@NotNull AnActionEvent e) {
    return e.getRequiredData(CommonDataKeys.PSI_FILE);
  }

  @NotNull
  protected Editor getEditor(@NotNull AnActionEvent e) {
    return e.getRequiredData(CommonDataKeys.EDITOR);
  }

  protected void selectWord(TextRange range, AnActionEvent e) {
    var editor = getEditor(e);
    var caretPosition = editor.offsetToLogicalPosition(range.getStartOffset());
    var selectionEnd = editor.offsetToLogicalPosition(range.getEndOffset());

    var caretState = new CaretState(caretPosition, caretPosition, selectionEnd);
    editor.getCaretModel().setCaretsAndSelections(List.of(caretState));
  }
}
