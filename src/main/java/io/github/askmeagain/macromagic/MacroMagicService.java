package io.github.askmeagain.macromagic;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.util.messages.MessageBus;
import com.intellij.util.messages.MessageBusConnection;

import java.util.ArrayList;
import java.util.List;

@Service
public final class MacroMagicService {
  private boolean running = false;
  private final MessageBus CONNECT;
  private MessageBusConnection CONNECTION;
  private final List<AnAction> actions = new ArrayList<>();

  public MacroMagicService() {
    CONNECT = ProjectManager.getInstance()
        .getDefaultProject()
        .getMessageBus();
  }

  public boolean isRunning() {
    return running;
  }

  public void setRunning() {
    running = true;
    CONNECTION = CONNECT.connect();
    CONNECTION.subscribe(AnActionListener.TOPIC, new RecordingListener(actions));
    System.out.println("Started now");
  }

  public void stopRunning() {
    running = false;
    CONNECTION.disconnect();
    System.out.println("Stopped now");
  }

  public void runActions(AnActionEvent e) {
    actions.forEach(x -> x.actionPerformed(e));
  }
}
