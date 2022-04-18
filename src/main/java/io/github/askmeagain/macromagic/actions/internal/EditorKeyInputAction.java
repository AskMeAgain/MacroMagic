package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import io.github.askmeagain.macromagic.actions.MacroMagicInternal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditorKeyInputAction extends MacroMagicBaseAction implements MacroMagicInternal {

  private String originalString;

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

  public EditorKeyInputAction merge(EditorKeyInputAction action) {
    return new EditorKeyInputAction(this.getOriginalString() + action.getOriginalString());
  }

  @Override
  public String toString() {
    return "Press Key(" + originalString + "), Editor(true)";
  }
}
