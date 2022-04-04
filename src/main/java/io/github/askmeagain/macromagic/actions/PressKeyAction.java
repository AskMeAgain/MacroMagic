package io.github.askmeagain.macromagic.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.actions.internal.MacroMagicInternal;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class PressKeyAction extends AnAction implements MacroMagicInternal {

  @Getter
  @Setter
  private String originalString;
  private Robot robot;

  public PressKeyAction(String s) {
    this();
    originalString = s;
  }

  public PressKeyAction() {
    try {
      robot = new Robot();
    } catch (AWTException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    for (var c : originalString.toCharArray()) {
      var extendedKeyCodeForChar = KeyEvent.getExtendedKeyCodeForChar(c);
      robot.keyPress(extendedKeyCodeForChar);
      robot.keyRelease(extendedKeyCodeForChar);
    }
  }

  @Override
  public String toString() {
    return "Press Key(" + originalString + ")";
  }
}
