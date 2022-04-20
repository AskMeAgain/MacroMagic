package io.github.askmeagain.macromagic.windows;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class MacroMagicSettingsWindow {

  private final JPanel settingsPanel;

  private final JBTextField fieldHistorySize = new JBTextField();
  private final JBTextField nestedDepth = new JBTextField();

  public MacroMagicSettingsWindow() {
    settingsPanel = FormBuilder.createFormBuilder()
        .addLabeledComponent(new JBLabel("Enter History Size: "), fieldHistorySize, 1, false)
        .addLabeledComponent(new JBLabel("Recursive Execution Depth: "), nestedDepth, 1, false)
        .addComponentFillVertically(new JPanel(), 0)
        .getPanel();
  }

  public JPanel getPanel() {
    return settingsPanel;
  }

  public JComponent getPreferredFocusedComponent() {
    return fieldHistorySize;
  }

  public Integer getHistorySize() {
    return Integer.parseInt(fieldHistorySize.getText());
  }

  public Integer getNestedDepth() {
    return Integer.parseInt(nestedDepth.getText());
  }

  public void setHistorySize(Integer newHistory) {
    fieldHistorySize.setText(String.valueOf(newHistory));
  }

  public void setNestedDepth(Integer newRecursiveDepth) {
    nestedDepth.setText(String.valueOf(newRecursiveDepth));
  }
}