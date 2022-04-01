package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.service.MacroMagicService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class MoveActionDownAction extends AnAction  implements MacroMagicInternal{

  private final MacroMagicService macroMagicService;

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    macroMagicService.moveSelectionUp();
  }
}
