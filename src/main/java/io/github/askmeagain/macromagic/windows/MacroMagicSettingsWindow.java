package io.github.askmeagain.macromagic.windows;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class MacroMagicSettingsWindow {

  private final JPanel settingsPanel;
  private final JBTextField fieldHistorySize = new JBTextField();

  public MacroMagicSettingsWindow() {
    settingsPanel = FormBuilder.createFormBuilder()
        .addLabeledComponent(new JBLabel("Enter History Size: "), fieldHistorySize, 1, false)
        .addComponentFillVertically(new JPanel(), 0)
        .getPanel();
  }

  public JPanel getPanel() {
    return settingsPanel;
  }

  public JComponent getPreferredFocusedComponent() {
    return fieldHistorySize;
  }

  public Integer getUserNameText() {
    return Integer.parseInt(fieldHistorySize.getText());
  }

  public void setHistorySize(Integer newHistory) {
    fieldHistorySize.setText(String.valueOf(newHistory));
  }

}