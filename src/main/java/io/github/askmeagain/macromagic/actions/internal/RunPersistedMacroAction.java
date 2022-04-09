package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.ide.DataManager;
import com.intellij.ide.projectView.ProjectView;
import com.intellij.ide.projectView.impl.nodes.BasePsiNode;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import io.github.askmeagain.macromagic.actions.MacroMagicBaseAction;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class RunPersistedMacroAction extends MacroMagicBaseAction implements MacroMagicInternal {

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    focusEditor(e);

    runActionOnSelectFiles(e);

    getMacroManagementService().runSelected(e);
  }

  private void runActionOnSelectFiles(@NotNull AnActionEvent e) {
    var project = e.getProject();
    Arrays.stream(ProjectView.getInstance(project)
            .getCurrentProjectViewPane()
            .getSelectedUserObjects())
        .map(x -> (PsiFileNode) x)
        .filter(Objects::nonNull)
        .map(BasePsiNode::getVirtualFile)
        .forEach(virtualFile -> {
          var document = FileDocumentManager.getInstance().getDocument(virtualFile);
          var editor = EditorFactory.getInstance().createEditor(document, project);

          var context = DataManager.getInstance().getDataContext(editor.getComponent());

          var newActionEvent = e.withDataContext(context);
          var isNull = newActionEvent.getData(CommonDataKeys.EDITOR);

        });
  }
}
