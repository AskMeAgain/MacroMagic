package io.github.askmeagain.macromagic.actions.gui;

import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.actions.MacroMagicInternal;
import io.github.askmeagain.macromagic.actions.internal.MacroMagicBaseAction;
import org.jetbrains.annotations.NotNull;

import javax.swing.JOptionPane;

public class CombineMacroAction extends MacroMagicBaseAction implements MacroMagicInternal {

  @Override
  public void update(@NotNull AnActionEvent e) {
    var isEmpty = getMacroManagementService().getCurrentSelectedMacros().isEmpty();

    e.getPresentation().setEnabled(!isEmpty);
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    var name = (String) JOptionPane.showInputDialog(null,
        "New Macro Name:",
        null,
        JOptionPane.PLAIN_MESSAGE,
        null,
        null,
        ""
    );

    if (name != null) {
      getMacroManagementService().combineSelected(name);
    }
  }
}
