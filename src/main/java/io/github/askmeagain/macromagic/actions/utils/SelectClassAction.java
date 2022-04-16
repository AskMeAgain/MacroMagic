package io.github.askmeagain.macromagic.actions.utils;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiIdentifier;
import io.github.askmeagain.macromagic.actions.internal.MacroMagicBaseAction;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class SelectClassAction extends MacroMagicBaseAction {
  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    var actionEvent = focusEditor(e);

    getPsiFile(actionEvent).accept(new JavaRecursiveElementVisitor() {
      @Override
      public void visitClass(PsiClass clazz) {
        Arrays.stream(clazz.getChildren())
            .filter(child -> child instanceof PsiIdentifier)
            .findFirst()
            .ifPresent(identifier -> selectWord(identifier.getTextRange(), actionEvent));
      }
    });
  }
}
