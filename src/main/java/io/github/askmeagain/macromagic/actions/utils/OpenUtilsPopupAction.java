package io.github.askmeagain.macromagic.actions.utils;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import io.github.askmeagain.macromagic.actions.internal.MacroMagicBaseAction;
import org.jetbrains.annotations.NotNull;

public class OpenUtilsPopupAction extends MacroMagicBaseAction {

  private static final String MACRO_UTILS_GROUP = "io.github.askmeagain.macromagic.actions.groups.utils";

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {

    var result = getJbPopupFactory().createActionGroupPopup("MacroMagic Utils",
        (ActionGroup) ActionManager.getInstance().getAction(MACRO_UTILS_GROUP),
        e.getDataContext(),
        JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
        true
    );

    result.showInBestPositionFor(e.getRequiredData(CommonDataKeys.EDITOR));
  }
}
