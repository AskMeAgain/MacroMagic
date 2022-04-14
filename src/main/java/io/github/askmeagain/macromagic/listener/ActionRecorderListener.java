package io.github.askmeagain.macromagic.listener;

import com.intellij.ide.IdeEventQueue;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import io.github.askmeagain.macromagic.actions.PressKeyAction;
import io.github.askmeagain.macromagic.actions.internal.MacroMagicInternal;
import io.github.askmeagain.macromagic.service.HistoryManagementService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyEvent;

@Slf4j
public class ActionRecorderListener implements AnActionListener {

  @Getter(lazy = true)
  private final HistoryManagementService historyManagementService = HistoryManagementService.getInstance();

  public ActionRecorderListener() {
    log.info("Registered ActionRecorderListener");

    IdeEventQueue.getInstance().addDispatcher(e -> {
      if (e instanceof KeyEvent && getHistoryManagementService().isRunning()) {

        var newEvent = (KeyEvent) e;

        if (!newEvent.isControlDown() && newEvent.getID() == KeyEvent.KEY_PRESSED) {
          if (newEvent.getKeyChar() == '\n') {
            System.out.println("Enter key key!");
          } else if (newEvent.getKeyCode() == 27) {
            System.out.println("Escape pressed!");
          } else if (newEvent.getKeyCode() == 8) {
            System.out.println("Backspace pressed!");
          } else {
            System.out.println("Pressed: " + newEvent.getKeyChar() + " code: " + newEvent.getKeyCode());
            getHistoryManagementService().addAction(new PressKeyAction((String.valueOf(newEvent.getKeyChar())), false));
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
      getHistoryManagementService().addAction(new PressKeyAction(String.valueOf(c), true));
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
