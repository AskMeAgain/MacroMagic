package io.github.askmeagain.macromagic.listener;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import io.github.askmeagain.macromagic.MacroMagicService;
import io.github.askmeagain.macromagic.actions.RecordMacroAction;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class ActionRecorderListener implements AnActionListener {

  private final MacroMagicService macroMagicService;

  public ActionRecorderListener() {
    log.info("Registered ActionRecorderListener");
    this.macroMagicService = MacroMagicService.getInstance();
  }

  @Override
  public void beforeActionPerformed(@NotNull AnAction action, @NotNull AnActionEvent event) {
    if (macroMagicService.isRunning()) {
      if (!(action instanceof RecordMacroAction)) {
        log.info(action.getTemplateText());
        macroMagicService.addAction(action);
      }
    }
  }
}
