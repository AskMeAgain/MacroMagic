package io.github.askmeagain.macromagic.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;

public interface ActionEventChanger {

  AnActionEvent getNewActionEvent();
}
