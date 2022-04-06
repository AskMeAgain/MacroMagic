package io.github.askmeagain.macromagic.actions.groups;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DynamicTestGroup extends ActionGroup {

  @Getter
  private final List<AnAction> actionList = new ArrayList<>();

  @Override
  public AnAction @NotNull [] getChildren(@Nullable AnActionEvent e) {
    return actionList.toArray(AnAction[]::new);
  }
}
