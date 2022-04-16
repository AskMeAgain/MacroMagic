package io.github.askmeagain.macromagic.actions.gui;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.keymap.impl.ui.EditKeymapsDialog;
import io.github.askmeagain.macromagic.actions.MacroMagicInternal;
import io.github.askmeagain.macromagic.actions.internal.MacroMagicBaseAction;
import org.jetbrains.annotations.NotNull;

import static io.github.askmeagain.macromagic.service.HelperService.macroActionPrefix;

public class RegisterShortcutAction extends MacroMagicBaseAction implements MacroMagicInternal {
  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    var macros = getMacroManagementService().getCurrentSelectedMacros();
    if (macros.size() > 1) {
      return;
    }

    var dialog = new EditKeymapsDialog(null, macroActionPrefix + macros.get(0).getMacroName());
    dialog.show();
  }
}
