package io.github.askmeagain.macromagic.listener;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import io.github.askmeagain.macromagic.actions.internal.MacroMagicInternal;
import io.github.askmeagain.macromagic.service.HistoryManagementService;
import io.github.askmeagain.macromagic.actions.PressKeyAction;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class ActionRecorderListener implements AnActionListener {

  private final HistoryManagementService historyManagementService;

  public ActionRecorderListener() {
    log.info("Registered ActionRecorderListener");
    this.historyManagementService = HistoryManagementService.getInstance();
  }

  @Override
  public void beforeEditorTyping(char c, @NotNull DataContext dataContext) {
    if (historyManagementService.isRunning()) {
      log.info(String.valueOf(c));
      historyManagementService.addAction(new PressKeyAction(String.valueOf(c)));
    }
  }

  @Override
  public void beforeActionPerformed(@NotNull AnAction action, @NotNull AnActionEvent event) {
    if (historyManagementService.isRunning()) {
      if (!(action instanceof MacroMagicInternal)) {
        log.info(action.getTemplateText());
        historyManagementService.addAction(action);
      }
    }
  }
}
