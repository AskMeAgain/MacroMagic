package io.github.askmeagain.macromagic.actions.internal.cursor;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiAnnotation;
import io.github.askmeagain.macromagic.actions.MacroMagicBaseAction;
import org.jetbrains.annotations.NotNull;

public class SelectAnnotation extends MacroMagicBaseAction {
  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    getPsiFile(e).accept(new JavaRecursiveElementVisitor() {
      @Override
      public void visitAnnotation(PsiAnnotation annotation) {
        selectWord(annotation.getTextRange(), e);
      }
    });
  }
}
