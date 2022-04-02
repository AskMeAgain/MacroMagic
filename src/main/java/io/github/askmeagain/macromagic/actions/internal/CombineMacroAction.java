package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.service.MacroMagicPersistingService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

@RequiredArgsConstructor
public class CombineMacroAction extends AnAction implements MacroMagicInternal {

  private final MacroMagicPersistingService macroMagicPersistingService;

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
      macroMagicPersistingService.combineSelected(name);
    }
  }
}
