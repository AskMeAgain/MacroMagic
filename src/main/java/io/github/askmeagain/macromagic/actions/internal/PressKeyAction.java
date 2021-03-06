package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.actions.MacroMagicInternal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.awt.Robot;
import java.awt.event.KeyEvent;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PressKeyAction extends MacroMagicBaseAction implements MacroMagicInternal {

  private String originalString;

  @SneakyThrows
  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    var robot = new Robot();
    for (var c : originalString.toCharArray()) {
      var extendedKeyCodeForChar = KeyEvent.getExtendedKeyCodeForChar(c);
      robot.keyPress(extendedKeyCodeForChar);
      robot.delay(10);
      robot.keyRelease(extendedKeyCodeForChar);
    }

    robot.delay(100);
  }

  public boolean tryRemoveLatestKeyPress() {
    originalString = originalString.substring(0, originalString.length() - 1);

    return originalString.length() != 0;
  }

  public PressKeyAction merge(PressKeyAction action) {
    return new PressKeyAction(this.getOriginalString() + action.getOriginalString());
  }

  @Override
  public String toString() {
    return "Press Dialog Key(" + originalString + ")";
  }
}
