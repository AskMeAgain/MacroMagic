package io.github.askmeagain.macromagic;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public final class MacroMagicService {
  private boolean running = false;
  private final List<AnAction> actions = new ArrayList<>();

  public boolean isRunning() {
    return running;
  }

  public void setRunning() {
    running = true;
    log.info("Started now");
  }

  public void stopRunning() {
    running = false;
    log.info("Stopped now");
  }

  public void runActions(AnActionEvent e) {
    actions.forEach(x -> x.actionPerformed(e));
  }

  public void addAction(AnAction action){
    actions.add(action);
  }

  public static MacroMagicService getInstance() {
    return ApplicationManager.getApplication()
        .getService(MacroMagicService.class);
  }
}
