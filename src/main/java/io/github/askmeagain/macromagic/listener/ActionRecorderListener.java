package io.github.askmeagain.macromagic.listener;

import com.intellij.ide.IdeEventQueue;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import io.github.askmeagain.macromagic.actions.MacroMagicInternal;
import io.github.askmeagain.macromagic.actions.internal.EditorKeyInputAction;
import io.github.askmeagain.macromagic.actions.internal.PressKeyAction;
import io.github.askmeagain.macromagic.service.HistoryManagementService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyEvent;
import java.util.Set;

@Slf4j
public class ActionRecorderListener implements AnActionListener {

  private static final Set<Integer> notAllowedKeys = Set.of(8, 10, 27, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123);

  @Getter(lazy = true)
  private final HistoryManagementService historyManagementService = HistoryManagementService.getInstance();

  public ActionRecorderListener() {
    log.info("Registered ActionRecorderListener");

    IdeEventQueue.getInstance().addDispatcher(e -> {
      if (e instanceof KeyEvent && getHistoryManagementService().isRunning()) {

        var newEvent = (KeyEvent) e;

        if (!newEvent.isControlDown() && newEvent.getID() == KeyEvent.KEY_PRESSED) {
          if (!notAllowedKeys.contains(newEvent.getKeyCode())) {
            getHistoryManagementService().addAction(new PressKeyAction((String.valueOf(newEvent.getKeyChar()))));
          }
        }
      }

      return false;
    }, null);
  }

  @Override
  public void beforeEditorTyping(char c, @NotNull DataContext dataContext) {
    if (getHistoryManagementService().isRunning()) {
      getHistoryManagementService().removeLatestAction();
      getHistoryManagementService().addAction(new EditorKeyInputAction(String.valueOf(c)));
    }
  }

  @Override
  public void beforeActionPerformed(@NotNull AnAction action, @NotNull AnActionEvent event) {
    if (getHistoryManagementService().isRunning()) {
      if (!(action instanceof MacroMagicInternal)) {
        if (!"MacroMagic Utils".equals(action.getTemplateText())) {
          getHistoryManagementService().addAction(action);
        }
      }
    }
  }
}
