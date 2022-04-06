package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import io.github.askmeagain.macromagic.service.MacroManagementService;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public class RunPersistedMacroAction extends AnAction implements MacroMagicInternal {

  @Getter(lazy = true)
  private final MacroManagementService macroManagementService = MacroManagementService.getInstance();

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    var editor = e.getData(CommonDataKeys.EDITOR);

    if (editor == null) {
      return;
    }

    var context = DataManager.getInstance().getDataContext(editor.getComponent());

    var newContext = e.withDataContext(context);

    System.out.println(editor.getCaretModel().getAllCarets().size());
    getMacroManagementService().runSelected(newContext);
  }
}
