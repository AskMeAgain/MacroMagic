package io.github.askmeagain.macromagic.actions.internal;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.ide.projectView.impl.nodes.BasePsiNode;
import com.intellij.ide.projectView.impl.nodes.ClassTreeNode;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import io.github.askmeagain.macromagic.actions.MacroMagicInternal;
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

    Queue<AnAction> queue;

    if (fromWithinEditor) {
      queue = getQueueFromMacroContainer();
    } else if (fromFileTree) {
      queue = runActionOnSelectFiles(e);
    } else {
      queue = new ArrayDeque<>();
    }

    new QueueAction(queue).actionPerformed(e);
  }

  private Queue<AnAction> getQueueFromMacroContainer() {
    return macroContainer.getActions()
        .stream()
        .map(getHelperService()::deserializeAction)
        .collect(Collectors.toCollection(ArrayDeque::new));
  }

  private ArrayDeque<AnAction> runActionOnSelectFiles(@NotNull AnActionEvent e) {
    var project = e.getProject();
    var queue = new ArrayDeque<AnAction>();

    Arrays.stream(ProjectView.getInstance(project)
            .getCurrentProjectViewPane()
            .getSelectedUserObjects())
        .map(file -> (ClassTreeNode) file)
        .filter(Objects::nonNull)
        .map(BasePsiNode::getVirtualFile)
        .forEach(virtualFile -> {
          queue.add(new OpenEditor(virtualFile, project, false));
          queue.addAll(getQueueFromMacroContainer());
        });

    return queue;
  }

  @Override
  public String toString() {
    return macroContainer.getMacroName();
  }
}
