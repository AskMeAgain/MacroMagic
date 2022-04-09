package io.github.askmeagain.macromagic.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import io.github.askmeagain.macromagic.actions.internal.MacroMagicInternal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
public class PressKeyAction extends MacroMagicBaseAction implements MacroMagicInternal {

  @Getter
  @Setter
  private String originalString;

  public PressKeyAction(String s) {
    originalString = s;
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
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

  @Override
  public String toString() {
    return "Press Key(" + originalString + ")";
  }
}
