package io.github.askmeagain.macromagic.listener;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import io.github.askmeagain.macromagic.actions.internal.MacroMagicInternal;
import io.github.askmeagain.macromagic.service.MacroMagicHistoryService;
import io.github.askmeagain.macromagic.actions.internal.PressKeyAction;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class ActionRecorderListener implements AnActionListener {

  private final MacroMagicHistoryService macroMagicHistoryService;

  public ActionRecorderListener() {
    log.info("Registered ActionRecorderListener");
    this.macroMagicHistoryService = MacroMagicHistoryService.getInstance();
  }

  @Override
  public void beforeEditorTyping(char c, @NotNull DataContext dataContext) {
    if (macroMagicHistoryService.isRunning()) {
      log.info(String.valueOf(c));
      macroMagicHistoryService.addAction(new PressKeyAction(String.valueOf(c)));
    }
  }

  @Override
  public void beforeActionPerformed(@NotNull AnAction action, @NotNull AnActionEvent event) {
    if (macroMagicHistoryService.isRunning()) {
      if (!(action instanceof MacroMagicInternal)) {
        log.info(action.getTemplateText());
        macroMagicHistoryService.addAction(action);
      }
    }
  }
}
