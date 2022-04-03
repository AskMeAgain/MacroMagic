package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.service.MacroMagicHistoryService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

@RequiredArgsConstructor
public class CreateNewMacroAction extends AnAction implements MacroMagicInternal {

  private final MacroMagicHistoryService macroMagicHistoryService;

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
      macroMagicHistoryService.persistMacro(name);
    }
  }
}
