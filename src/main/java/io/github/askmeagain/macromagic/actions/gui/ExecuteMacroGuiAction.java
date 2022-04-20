package io.github.askmeagain.macromagic.actions.gui;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.askmeagain.macromagic.actions.internal.MacroMagicBaseAction;
import io.github.askmeagain.macromagic.actions.internal.OpenEditor;
import io.github.askmeagain.macromagic.actions.internal.QueueAction;
import io.github.askmeagain.macromagic.entities.MacroContainer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class ExecuteMacroGuiAction extends MacroMagicBaseAction {
  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    var project = e.getProject();
    var queue = new ArrayDeque<AnAction>();

    var macroContainers = getMacroManagementService().getCurrentSelectedMacros();

    var document = getSelectedTextEditor(e).getDocument();
    var virtualFile = getFileDocumentManager().getFile(document);

    queue.add(new OpenEditor(virtualFile, project, true));
    queue.addAll(getQueueFromMacroContainers(macroContainers));

    new QueueAction(queue,
        0,
        getPersistenceManagementService().getState().getNestedDepth()
    ).actionPerformed(e);
  }

  private Queue<AnAction> getQueueFromMacroContainers(List<MacroContainer> macroContainers) {
    return macroContainers.stream()
        .map(MacroContainer::getActions)
        .flatMap(Collection::stream)
        .map(getHelperService()::deserializeAction)
        .collect(Collectors.toCollection(ArrayDeque::new));
  }
}
