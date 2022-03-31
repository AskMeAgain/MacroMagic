package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PressKeyAction extends AnAction {

  @Getter
  private int extendedKeyCodeForChar;
  @Getter
  private char originalChar;
  private Robot robot;

  public PressKeyAction(char c) {
    this();
    setKeyCode(c);
  }

  public PressKeyAction() {
    try {
      robot = new Robot();
    } catch (AWTException e) {
      e.printStackTrace();
    }
  }

  public void setKeyCode(char c) {
    extendedKeyCodeForChar = KeyEvent.getExtendedKeyCodeForChar(c);
    originalChar = c;
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    robot.keyPress(extendedKeyCodeForChar);
    robot.keyRelease(extendedKeyCodeForChar);
  }

  @Override
  public String toString() {
    return "Press Key(" + originalChar + ")";
  }
}
