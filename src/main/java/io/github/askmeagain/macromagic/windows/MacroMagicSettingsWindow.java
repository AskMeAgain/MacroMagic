package io.github.askmeagain.macromagic.windows;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class MacroMagicSettingsWindow {

  private final JPanel settingsPanel;
  private final JBTextField fieldHistorySize = new JBTextField();
  private final JCheckBox autoCollapseCheckBox = new JBCheckBox("Auto collapse key presses");

  public MacroMagicSettingsWindow() {
    settingsPanel = FormBuilder.createFormBuilder()
        .addLabeledComponent(new JBLabel("Enter History Size: "), fieldHistorySize, 1, false)
        .addComponent(autoCollapseCheckBox, 1)
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

  public void setHistorySize(Integer newHistory) {
    fieldHistorySize.setText(String.valueOf(newHistory));
  }

  public Boolean getAutoCollapse() {
    return autoCollapseCheckBox.isSelected();
  }

  public void setAutoCollapse(Boolean autoCollapse) {
    autoCollapseCheckBox.setSelected(autoCollapse);
  }
}