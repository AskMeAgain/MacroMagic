package io.github.askmeagain.macromagic.actions;

import com.intellij.ide.DataManager;
import com.intellij.ide.projectView.ProjectView;
import com.intellij.ide.projectView.impl.nodes.BasePsiNode;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretState;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.IdeFocusManager;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.PsiFile;
import io.github.askmeagain.macromagic.service.HelperService;
import io.github.askmeagain.macromagic.service.HistoryManagementService;
import io.github.askmeagain.macromagic.service.MacroManagementService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

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

  @SneakyThrows
  protected void runActionOnFiles(@NotNull AnActionEvent e, Consumer<AnActionEvent> consumer) {
    var activeToolWindowId = ToolWindowManager.getInstance(e.getProject()).getActiveToolWindowId();

    var fromFileTree = "Project".equals(activeToolWindowId);
    var fromWithinEditor = e.getData(CommonDataKeys.EDITOR) != null;

    if (fromWithinEditor) {
      consumer.accept(e);
    } else if (fromFileTree) {
      runActionOnSelectFiles(e, consumer);
    } else {
      focusEditor(e);
      var virtualFile = getFileDocumentManager().getFile(getSelectedTextEditor(e).getDocument());
      executionActionOnVirtualFile(virtualFile, e, consumer);
    }
  }

  protected void runActionOnSelectFiles(@NotNull AnActionEvent e, Consumer<AnActionEvent> consumer) {
    var project = e.getProject();
    Arrays.stream(ProjectView.getInstance(project)
            .getCurrentProjectViewPane()
            .getSelectedUserObjects())
        .map(x -> (PsiFileNode) x)
        .filter(Objects::nonNull)
        .map(BasePsiNode::getVirtualFile)
        .forEach(virtualFile -> executionActionOnVirtualFile(virtualFile, e, consumer));
  }

  protected void executionActionOnVirtualFile(VirtualFile virtualFile, @NotNull AnActionEvent e, Consumer<AnActionEvent> consumer) {
    var document = getFileDocumentManager().getDocument(virtualFile);

    var editor = getEditorFactory().createEditor(document, e.getProject());
    var context = getDataManager().getDataContext(editor.getContentComponent());

    //getDataManager().get(context, Key.create(CommonDataKeys.VIRTUAL_FILE.getName()), virtualFile);
    //var isNull = context.getData(CommonDataKeys.VIRTUAL_FILE);

    consumer.accept(e.withDataContext(context));
  }
}
