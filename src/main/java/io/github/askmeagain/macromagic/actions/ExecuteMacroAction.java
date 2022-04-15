package io.github.askmeagain.macromagic.actions;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.ide.projectView.impl.nodes.BasePsiNode;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import io.github.askmeagain.macromagic.actions.internal.MacroMagicInternal;
import io.github.askmeagain.macromagic.actions.internal.OpenEditor;
import io.github.askmeagain.macromagic.entities.MacroContainer;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;

@Slf4j
public class ExecuteMacroAction extends MacroMagicBaseAction implements MacroMagicInternal {

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

    Queue<AnAction> queue = null;

    if (fromWithinEditor) {
      queue = getCollapsedAction();
    } else if (fromFileTree) {
      queue = runActionOnSelectFiles(e);
    } else {
      queue = new ArrayDeque<>();
    }

    System.out.println("----------------------------------");

    new QueueAction(queue).actionPerformed(e);
  }

  private Queue<AnAction> getCollapsedAction() {
    return macroContainer.getActions()
        .stream()
        .map(getHelperService()::deserializeAction)
        .collect(Collectors.toCollection(ArrayDeque::new));
  }

  private ArrayDeque<AnAction> runActionOnSelectFiles(@NotNull AnActionEvent e) {
    var project = e.getProject();
    var virtualFiles = Arrays.stream(ProjectView.getInstance(project)
            .getCurrentProjectViewPane()
            .getSelectedUserObjects())
        .map(x -> (PsiFileNode) x)
        .filter(Objects::nonNull)
        .map(BasePsiNode::getVirtualFile)
        .collect(Collectors.toList());

    var queue = new ArrayDeque<AnAction>();

    for (var i = 0; i < virtualFiles.size(); i++) {
      queue.add(new OpenEditor(virtualFiles.get(i), e.getProject()));
      queue.addAll(getCollapsedAction());
    }

    return queue;
  }

  @Override
  public String toString() {
    return macroContainer.getMacroName();
  }
}
