package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.service.HistoryManagementService;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class CreateNewMacroAction extends AnAction implements MacroMagicInternal {

  @Getter(lazy = true)
  private final HistoryManagementService historyManagementService = HistoryManagementService.getInstance();

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
