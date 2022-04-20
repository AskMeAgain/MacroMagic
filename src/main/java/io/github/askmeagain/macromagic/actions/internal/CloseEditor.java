package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import io.github.askmeagain.macromagic.actions.MacroMagicInternal;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class CloseEditor extends MacroMagicBaseAction implements MacroMagicInternal {

  private final VirtualFile virtualFile;
  private final Project project;

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    FileEditorManager.getInstance(project).closeFile(virtualFile);
  }

  @Override
  public String toString() {
    return "Close Editor " + virtualFile.getName();
  }
}
