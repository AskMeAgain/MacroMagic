package io.github.askmeagain.macromagic.actions;

import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.entities.MacroContainer;
import io.github.askmeagain.macromagic.service.HelperService;
import io.github.askmeagain.macromagic.service.MacroManagementService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class ExecuteMacroAction extends AnAction {

  @Getter
  @Setter
  private MacroContainer macroContainer;

  @Getter(lazy = true)
  private final HelperService helperService = HelperService.getInstance();

  @Getter(lazy = true)
  private final MacroManagementService macroManagementService = MacroManagementService.getInstance();

  public ExecuteMacroAction(MacroContainer macroContainer) {
    this.macroContainer = macroContainer;
    this.getTemplatePresentation().setText(macroContainer.getMacroName());
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    if (ActionPlaces.isPopupPlace(e.getPlace())) {
      log.info("Should now work on files!");
    }
    getHelperService().executeActions(macroContainer.getActions(), e);
  }

  @Override
  public String toString() {
    return macroContainer.getMacroName();
  }
}
