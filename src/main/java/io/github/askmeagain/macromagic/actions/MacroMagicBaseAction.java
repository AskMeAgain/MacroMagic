package io.github.askmeagain.macromagic.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretState;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.wm.IdeFocusManager;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.PsiFile;
import io.github.askmeagain.macromagic.service.HelperService;
import io.github.askmeagain.macromagic.service.HistoryManagementService;
import io.github.askmeagain.macromagic.service.MacroManagementService;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class MacroMagicBaseAction extends AnAction {

  @Getter(lazy = true, value = AccessLevel.PROTECTED)
  private final MacroManagementService macroManagementService = MacroManagementService.getInstance();
  @Getter(lazy = true, value = AccessLevel.PROTECTED)
  private final HistoryManagementService historyManagementService = HistoryManagementService.getInstance();
  @Getter(lazy = true, value = AccessLevel.PROTECTED)
  private final HelperService helperService = HelperService.getInstance();

  @NotNull
  protected PsiFile getPsiFile(@NotNull AnActionEvent e) {
    return e.getRequiredData(CommonDataKeys.PSI_FILE);
  }

  protected Editor getEditor(@NotNull AnActionEvent e) {

    var windowManager = ToolWindowManager.getInstance(e.getProject());

    if (!windowManager.isEditorComponentActive()) {
      focusEditor(e);
      return getSelectedTextEditor(e);
    }

    return e.getRequiredData(CommonDataKeys.EDITOR);
  }

  protected void focusEditor(@NotNull AnActionEvent e) {
    var selectedTextEditor = getSelectedTextEditor(e);
    IdeFocusManager.getInstance(e.getProject()).requestFocus(selectedTextEditor.getContentComponent(), true);
  }

  protected Editor getSelectedTextEditor(@NotNull AnActionEvent e) {
    return FileEditorManager.getInstance(e.getProject()).getSelectedTextEditor();
  }

  protected void selectWord(TextRange range, AnActionEvent e) {

    var editor = getEditor(e);
    var caretPosition = editor.offsetToLogicalPosition(range.getStartOffset());
    var selectionEnd = editor.offsetToLogicalPosition(range.getEndOffset());

    var caretState = new CaretState(caretPosition, caretPosition, selectionEnd);
    editor.getCaretModel().setCaretsAndSelections(List.of(caretState));
  }
}
