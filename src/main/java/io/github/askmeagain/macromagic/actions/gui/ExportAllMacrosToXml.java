package io.github.askmeagain.macromagic.actions.gui;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileChooserFactory;
import com.intellij.openapi.fileChooser.FileSaverDescriptor;
import io.github.askmeagain.macromagic.actions.MacroMagicInternal;
import io.github.askmeagain.macromagic.actions.internal.MacroMagicBaseAction;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.FileOutputStream;
import java.time.LocalDate;

public class ExportAllMacrosToXml extends MacroMagicBaseAction implements MacroMagicInternal {

  @Override
  public void update(@NotNull AnActionEvent e) {
    var isEmpty = getMacroManagementService().getCurrentSelectedMacros().isEmpty();

    e.getPresentation().setEnabled(!isEmpty);
  }

  @SneakyThrows
  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    var macro = getMacroManagementService().exportAllMacros();
    FileSaverDescriptor descriptor = new FileSaverDescriptor("Store Macros", "Enter location", "xml");
    var dialog = FileChooserFactory.getInstance().createSaveFileDialog(descriptor, e.getProject());
    var file = dialog.save("Macros-" + LocalDate.now()).getFile();
    try (var outputStream = new FileOutputStream(file)) {
      outputStream.write(macro.getBytes());
    }
  }
}
