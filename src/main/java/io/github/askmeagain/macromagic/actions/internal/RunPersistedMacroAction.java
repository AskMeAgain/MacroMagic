package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.actions.MacroMagicBaseAction;
import org.jetbrains.annotations.NotNull;

public class RunPersistedMacroAction extends MacroMagicBaseAction implements MacroMagicInternal {

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    //  var editor = getEditor(e);
    focusEditor(e);

    //var context = DataManager.getInstance().getDataContext(editor.getComponent());

    //   var project = e.getRequiredData(CommonDataKeys.PROJECT);

//    var psiFile = FilenameIndex.getFilesByName(project, "asd3.java", GlobalSearchScope.everythingScope(project));
//    DataManager.getInstance().saveInDataContext(context, Key.create("psi.File"), psiFile);

    //var newActionEvent = e.withDataContext(context);

    getMacroManagementService().runSelected(e);
  }
}
