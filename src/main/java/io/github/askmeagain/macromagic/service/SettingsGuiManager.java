package io.github.askmeagain.macromagic.service;

import com.intellij.openapi.options.Configurable;
import io.github.askmeagain.macromagic.windows.MacroMagicSettingsWindow;
import lombok.Getter;
import org.jetbrains.annotations.Nls;

import javax.swing.JComponent;

public class SettingsGuiManager implements Configurable {

  @Getter(lazy = true)
  private final PersistenceManagementService persistenceManagementService = PersistenceManagementService.getInstance();
  private MacroMagicSettingsWindow settingsComponent;

  @Nls(capitalization = Nls.Capitalization.Title)
  @Override
  public String getDisplayName() {
    return "Macro Magic Settings";
  }

  @Override
  public JComponent getPreferredFocusedComponent() {
    return settingsComponent.getPreferredFocusedComponent();
  }

  @Override
  public JComponent createComponent() {
    var state = getPersistenceManagementService().getState();

    settingsComponent = new MacroMagicSettingsWindow();

    settingsComponent.setHistorySize(state.getHistorySize());
    settingsComponent.setNestedDepth(state.getNestedDepth());

    return settingsComponent.getPanel();
  }

  @Override
  public boolean isModified() {
    var state = getPersistenceManagementService().getState();

    var historySizeChanged = !settingsComponent.getHistorySize().equals(state.getHistorySize());
    var recursiveDepthChanged = !settingsComponent.getNestedDepth().equals(state.getNestedDepth());

    return historySizeChanged || recursiveDepthChanged;
  }

  @Override
  public void apply() {
    var state = getPersistenceManagementService().getState();

    state.setHistorySize(settingsComponent.getHistorySize());
    state.setNestedDepth(settingsComponent.getNestedDepth());
  }

  @Override
  public void reset() {
    var state = getPersistenceManagementService().getState();

    state.setHistorySize(30);
    state.setNestedDepth(10);
  }

  @Override
  public void disposeUIResources() {
    settingsComponent = null;
  }

}