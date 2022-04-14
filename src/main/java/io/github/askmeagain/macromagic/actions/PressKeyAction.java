package io.github.askmeagain.macromagic.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import io.github.askmeagain.macromagic.actions.internal.MacroMagicInternal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.awt.Robot;
import java.awt.event.KeyEvent;

@NoArgsConstructor
public class PressKeyAction extends MacroMagicBaseAction implements MacroMagicInternal {

  @Getter
  @Setter
  private String originalString;
  private boolean inEditor;

  public PressKeyAction(String s, boolean inEditor) {
    originalString = s;
    this.inEditor = inEditor;
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    if (inEditor) {
      writeInEditor(e);
    } else {
      writeViaKeyPresses();
    }
  }

  @SneakyThrows
  private void writeViaKeyPresses() {
    var robot = new Robot();
    for (var c : originalString.toCharArray()) {
      var extendedKeyCodeForChar = KeyEvent.getExtendedKeyCodeForChar(c);
      robot.keyPress(extendedKeyCodeForChar);
      robot.keyRelease(extendedKeyCodeForChar);
    }
  }

  private void writeInEditor(@NotNull AnActionEvent e) {
    var editor = getEditor(e);
    var document = editor.getDocument();

    editor.getCaretModel().runForEachCaret(caret -> {
      var selectionStart = caret.getSelectionStart();
      var selectionEnd = caret.getSelectionEnd();

      WriteCommandAction.runWriteCommandAction(e.getProject(), () -> {
            document.replaceString(selectionStart, selectionEnd, originalString);
            caret.moveToOffset(selectionEnd + originalString.length());
          }
      );
    });
  }

  public PressKeyAction appendKeyPress(PressKeyAction action) {
    return new PressKeyAction(originalString + action.getOriginalString(), inEditor);
  }

  @Override
  public String toString() {
    return "Press Key(" + originalString + "), Editor(" + inEditor + ")";
  }
}
