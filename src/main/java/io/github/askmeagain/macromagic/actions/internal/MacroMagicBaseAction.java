package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.CaretState;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.wm.IdeFocusManager;
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
  private final DataManager dataManager = DataManager.getInstance();
  @Getter(lazy = true, value = AccessLevel.PROTECTED)
  private final FileDocumentManager fileDocumentManager = FileDocumentManager.getInstance();
  @Getter(lazy = true, value = AccessLevel.PROTECTED)
  private final EditorFactory editorFactory = EditorFactory.getInstance();
  @Getter(lazy = true, value = AccessLevel.PROTECTED)
  private final MacroManagementService macroManagementService = MacroManagementService.getInstance();
  @Getter(lazy = true, value = AccessLevel.PROTECTED)
  private final HistoryManagementService historyManagementService = HistoryManagementService.getInstance();
  @Getter(lazy = true, value = AccessLevel.PROTECTED)
  private final HelperService helperService = HelperService.getInstance();
  @Getter(lazy = true, value = AccessLevel.PROTECTED)
  private final Application application = ApplicationManager.getApplication();
  @Getter(lazy = true, value = AccessLevel.PROTECTED)
  private final JBPopupFactory jbPopupFactory = JBPopupFactory.getInstance();


  @NotNull
  protected PsiFile getPsiFile(@NotNull AnActionEvent e) {
    return e.getRequiredData(CommonDataKeys.PSI_FILE);
  }

  protected Editor getEditor(@NotNull AnActionEvent e) {
    var data = e.getData(CommonDataKeys.EDITOR);

    if (data == null) {
      return getSelectedTextEditor(e);
    }

    return data;
  }

  protected AnActionEvent focusEditor(@NotNull AnActionEvent e) {
    var selectedTextEditor = getSelectedTextEditor(e);

    IdeFocusManager.getInstance(e.getProject()).requestFocus(selectedTextEditor.getContentComponent(), true);

    var context = getDataManager().getDataContext(selectedTextEditor.getContentComponent());

    return e.withDataContext(context);
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
