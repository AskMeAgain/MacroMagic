package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.actions.MacroMagicBaseAction;
import org.jetbrains.annotations.NotNull;

import javax.swing.JOptionPane;

public class CreateNewMacroAction extends MacroMagicBaseAction implements MacroMagicInternal {

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {

    var name = (String) JOptionPane.showInputDialog(null,
        "Macro Name:",
        null,
        JOptionPane.PLAIN_MESSAGE,
        null,
        null,
        ""
    );

    if (name != null) {
      getHistoryManagementService().persistMacro(name);
    }
  }
}
