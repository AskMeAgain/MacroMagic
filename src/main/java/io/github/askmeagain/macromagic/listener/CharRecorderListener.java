package io.github.askmeagain.macromagic.listener;

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import io.github.askmeagain.macromagic.MacroMagicService;
import io.github.askmeagain.macromagic.actions.PressKeyAction;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class CharRecorderListener extends TypedHandlerDelegate {

  private final MacroMagicService macroMagicService;

  public CharRecorderListener() {
    log.info("Registered CharRecorderListener");
    this.macroMagicService = MacroMagicService.getInstance();
  }

  @Override
  public Result charTyped(char c, Project project, Editor editor, PsiFile file) {
    if (macroMagicService.isRunning()) {
      log.info(String.valueOf(c));
      macroMagicService.addAction(new PressKeyAction(c));
    }
    return Result.CONTINUE;
  }
}
