package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.entities.MacroContainer;
import io.github.askmeagain.macromagic.service.HelperService;
import io.github.askmeagain.macromagic.service.MacroManagementService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class ExecuteMacroAction extends AnAction {

  @Getter
  @Setter
  private MacroContainer macroContainer;

  @Getter(lazy = true)
  private final HelperService helperService = HelperService.getInstance();

  @Getter(lazy = true)
  private final MacroManagementService macroManagementService = MacroManagementService.getInstance();

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    getHelperService().executeActions(macroContainer.getActions(), getMacroManagementService(), e);
  }

  @Override
  public String toString() {
    return macroContainer.getMacroName();
  }
}
