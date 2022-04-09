package io.github.askmeagain.macromagic.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.entities.MacroContainer;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class ExecuteMacroAction extends MacroMagicBaseAction {

  @Getter
  @Setter
  private MacroContainer macroContainer;

  public ExecuteMacroAction(MacroContainer macroContainer) {
    this.macroContainer = macroContainer;
    this.getTemplatePresentation().setText(macroContainer.getMacroName());
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    runActionOnFiles(e, ev -> getHelperService().executeActions(macroContainer.getActions(), ev));
  }

  @Override
  public String toString() {
    return macroContainer.getMacroName();
  }
}
