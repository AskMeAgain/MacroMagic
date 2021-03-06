package io.github.askmeagain.macromagic.actions.gui;

import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.actions.MacroMagicInternal;
import io.github.askmeagain.macromagic.actions.internal.MacroMagicBaseAction;
import org.jetbrains.annotations.NotNull;

import javax.swing.JOptionPane;

public class SaveMacroAction extends MacroMagicBaseAction implements MacroMagicInternal {

  @Override
  public void update(@NotNull AnActionEvent e) {
    var isEmpty = getHistoryManagementService().getSelectedValuesList().isEmpty();

    e.getPresentation().setEnabled(!isEmpty);
  }

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
