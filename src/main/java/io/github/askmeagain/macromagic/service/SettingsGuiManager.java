package io.github.askmeagain.macromagic.service;

import com.intellij.openapi.options.Configurable;
import io.github.askmeagain.macromagic.windows.MacroMagicSettingsWindow;
import lombok.Getter;
import org.jetbrains.annotations.Nls;

import javax.swing.JComponent;

/**
 * Provides controller functionality for application settings.
 */
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
    settingsComponent = new MacroMagicSettingsWindow();
    settingsComponent.setHistorySize(getPersistenceManagementService().getState().getHistorySize());
    return settingsComponent.getPanel();
  }

  @Override
  public boolean isModified() {
    return !settingsComponent.getUserNameText().equals(getPersistenceManagementService().getState().getHistorySize());
  }

  @Override
  public void apply() {
    getPersistenceManagementService().getState().setHistorySize(settingsComponent.getUserNameText());
  }

  @Override
  public void reset() {
    getPersistenceManagementService().getState().setHistorySize(30);
  }

  @Override
  public void disposeUIResources() {
    settingsComponent = null;
  }

}