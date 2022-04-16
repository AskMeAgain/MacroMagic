package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.CaretState;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import io.github.askmeagain.macromagic.actions.ActionEventChanger;
import io.github.askmeagain.macromagic.actions.MacroMagicInternal;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@RequiredArgsConstructor
public class OpenEditor extends MacroMagicBaseAction implements MacroMagicInternal, ActionEventChanger {

  private final VirtualFile virtualFile;
  private final Project project;
  private AnActionEvent anActionEvent;

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {

    System.out.println("Open File: " + virtualFile.getName());

    var range = new TextRange(0, 0);

    var editor = FileEditorManager.getInstance(project).openTextEditor(new OpenFileDescriptor(e.getProject(), virtualFile), true);
    var posi = editor.offsetToLogicalPosition(range.getStartOffset());
    editor.getCaretModel().setCaretsAndSelections(List.of(new CaretState(posi, posi, posi)));

    var context = getDataManager().getDataContext(editor.getContentComponent());

    anActionEvent = e.withDataContext(context);
  }

  @Override
  public AnActionEvent getNewActionEvent() {
    return anActionEvent;
  }

  @Override
  public String toString() {
    return "Open Editor " + virtualFile.getName();
  }
}
