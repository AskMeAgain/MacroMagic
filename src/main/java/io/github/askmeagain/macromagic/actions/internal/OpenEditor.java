package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.CaretState;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import io.github.askmeagain.macromagic.actions.MacroMagicBaseAction;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@RequiredArgsConstructor
public class OpenEditor extends MacroMagicBaseAction implements MacroMagicInternal {

  private final VirtualFile virtualFile;
  private final Project project;

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    System.out.println("Executing Action on File: " + virtualFile.getName());
    var document = getFileDocumentManager().getDocument(virtualFile);

    var editor = getEditorFactory().createEditor(document, e.getProject());

    var range = new TextRange(0, 0);

    var caretPosition = editor.offsetToLogicalPosition(range.getStartOffset());
    var selectionEnd = editor.offsetToLogicalPosition(range.getEndOffset());

    var caretState = new CaretState(caretPosition, caretPosition, selectionEnd);
    editor.getCaretModel().setCaretsAndSelections(List.of(caretState));

    FileEditorManager.getInstance(project).openFile(virtualFile, true);

  }
}
