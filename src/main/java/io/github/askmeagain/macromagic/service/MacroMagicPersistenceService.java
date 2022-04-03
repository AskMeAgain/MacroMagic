package io.github.askmeagain.macromagic.service;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import io.github.askmeagain.macromagic.entities.MacroMagicState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Service
@State(name = "MacroMagicState", storages = @Storage("macromagic.xml"))
public final class MacroMagicPersistenceService implements PersistentStateComponent<MacroMagicState> {

  public static MacroMagicPersistenceService getInstance() {
    return ApplicationManager.getApplication().getService(MacroMagicPersistenceService.class);
  }

  private MacroMagicState macroMagicState = new MacroMagicState();

  @Override
  public @Nullable MacroMagicState getState() {
    return macroMagicState;
  }

  @Override
  public void loadState(@NotNull MacroMagicState state) {
    macroMagicState = state;
  }
}
