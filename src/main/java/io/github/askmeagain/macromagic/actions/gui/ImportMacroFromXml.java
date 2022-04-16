package io.github.askmeagain.macromagic.actions.gui;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserFactory;
import io.github.askmeagain.macromagic.actions.MacroMagicInternal;
import io.github.askmeagain.macromagic.actions.internal.MacroMagicBaseAction;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

public class ImportMacroFromXml extends MacroMagicBaseAction implements MacroMagicInternal {
  @SneakyThrows
  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    var descriptor = new FileChooserDescriptor(true, false, false, false, false, false);
    var fileChooser = FileChooserFactory.getInstance().createFileChooser(descriptor, e.getProject(), null);

    var xml = new String(fileChooser.choose(e.getProject())[0].getInputStream().readAllBytes());
    getMacroManagementService().importMacros(xml);
  }
}
