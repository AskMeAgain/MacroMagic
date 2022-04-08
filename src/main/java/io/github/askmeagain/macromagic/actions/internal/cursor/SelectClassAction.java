package io.github.askmeagain.macromagic.actions.internal.cursor;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiIdentifier;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class SelectClassAction extends SelectTextBaseAction {
  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    getPsiFile(e).accept(new JavaRecursiveElementVisitor() {
      @Override
      public void visitClass(PsiClass aClass) {
        Arrays.stream(aClass.getChildren())
            .filter(child -> child instanceof PsiIdentifier)
            .findFirst()
            .ifPresent(identifier -> selectWord(identifier.getTextRange(), e));
      }
    });
  }
}
