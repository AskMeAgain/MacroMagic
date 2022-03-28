package io.github.askmeagain.macromagic;

import com.intellij.openapi.actionSystem.ex.AnActionListener;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.util.messages.MessageBusConnection;

@Service
public final class MacroMagicState {
  private boolean running = false;
  private final MessageBusConnection CONNECT;

  public MacroMagicState() {
    CONNECT = ProjectManager.getInstance()
        .getDefaultProject()
        .getMessageBus()
        .connect();
  }

  public boolean isRunning() {
    return running;
  }

  public void setRunning() {
    running = true;
    CONNECT.subscribe(AnActionListener.TOPIC, new CustomActionListener());
    System.out.println("Started now");
  }

  public void stopRunning() {
    running = false;
    CONNECT.disconnect();
    System.out.println("Stopped now");
  }
}
