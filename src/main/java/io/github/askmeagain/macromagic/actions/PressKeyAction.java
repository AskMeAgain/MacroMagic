package io.github.askmeagain.macromagic.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PressKeyAction extends AnAction {

  private final int extendedKeyCodeForChar;
  private Robot robot;

  public PressKeyAction(char c) {
    extendedKeyCodeForChar = KeyEvent.getExtendedKeyCodeForChar(c);

    try {
      robot = new Robot();
    } catch (AWTException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    robot.keyPress(extendedKeyCodeForChar);
    robot.keyRelease(extendedKeyCodeForChar);
  }
}
