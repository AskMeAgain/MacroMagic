package io.github.askmeagain.macromagic.actions;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.ide.projectView.impl.nodes.BasePsiNode;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import io.github.askmeagain.macromagic.entities.MacroContainer;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

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

    var fromFileTree = ActionPlaces.isPopupPlace(e.getPlace());
    var fromWithinEditor = e.getData(CommonDataKeys.EDITOR) != null;

    if (fromWithinEditor) {
      getCollapsedAction().actionPerformed(e);
    } else if (fromFileTree) {
      runActionOnSelectFiles(e);
    }
  }

  private AnAction getCollapsedAction() {
    var actions = macroContainer.getActions()
        .stream()
        .map(getHelperService()::deserializeAction)
        .collect(Collectors.toList());

    var resultAction = new IntelligentAction(actions.get(0), null);
    for (var i = 1; i < actions.size(); i++) {
      resultAction = resultAction.withNextAction(actions.get(i));
    }

    return resultAction;
  }

  private void runActionOnSelectFiles(@NotNull AnActionEvent e) {
    var project = e.getProject();
    var virtualFiles = Arrays.stream(ProjectView.getInstance(project)
            .getCurrentProjectViewPane()
            .getSelectedUserObjects())
        .map(x -> (PsiFileNode) x)
        .filter(Objects::nonNull)
        .map(BasePsiNode::getVirtualFile)
        .collect(Collectors.toList());

    var temp = new OpenEditorAndRunActionAction(virtualFiles.get(0), getCollapsedAction(), e.getProject());
    for (var i = 1; i < virtualFiles.size(); i++) {
      temp = new OpenEditorAndRunActionAction(virtualFiles.get(i), new SingleActionWrapper(getCollapsedAction(), temp), e.getProject());
    }

    temp.actionPerformed(e);
  }

  @Override
  public String toString() {
    return macroContainer.getMacroName();
  }
}
