package io.github.askmeagain.macromagic.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.Service;
import io.github.askmeagain.macromagic.actions.internal.MacroMagicInternal;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.velocity.runtime.directive.Foreach;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
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
