package io.github.askmeagain.macromagic.windows;

import com.intellij.openapi.options.Configurable;
import io.github.askmeagain.macromagic.service.PersistenceManagementService;
import org.jetbrains.annotations.Nls;

import javax.swing.JComponent;

/**
 * Provides controller functionality for application settings.
 */
public class AppSettingsConfigurable implements Configurable {

  private AppSettingsComponent mySettingsComponent;

  // A default constructor with no arguments is required because this implementation
  // is registered as an applicationConfigurable EP

  @Nls(capitalization = Nls.Capitalization.Title)
  @Override
  public String getDisplayName() {
    return "SDK: Application Settings Example";
  }

  @Override
  public JComponent getPreferredFocusedComponent() {
    return mySettingsComponent.getPreferredFocusedComponent();
  }


  @Override
  public JComponent createComponent() {
    mySettingsComponent = new AppSettingsComponent();
    return mySettingsComponent.getPanel();
  }

  @Override
  public boolean isModified() {
    var settings = PersistenceManagementService.getInstance();
//    boolean modified = !mySettingsComponent.getUserNameText().equals(settings.getState().);
//    modified |= mySettingsComponent.getIdeaUserStatus() != settings.ideaStatus;
//    return modified;
    return false;
  }

  @Override
  public void apply() {
    var settings = PersistenceManagementService.getInstance();
    settings.getState().setHistorySize(Integer.parseInt(mySettingsComponent.getUserNameText()));
  }

  @Override
  public void reset() {
    var settings = PersistenceManagementService.getInstance();
    settings.getState().setHistorySize(40);
  }

  @Override
  public void disposeUIResources() {
    mySettingsComponent = null;
  }

}