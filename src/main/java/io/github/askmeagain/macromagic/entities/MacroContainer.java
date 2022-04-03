package io.github.askmeagain.macromagic.entities;

import com.intellij.openapi.actionSystem.ShortcutSet;
import com.intellij.util.xmlb.annotations.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MacroContainer {

  private String macroName;

  @Transient
  @With
  private final ShortcutSet shortcutSet = null;

  private List<PersistedActionDto> actions = new ArrayList<>();

  @Override
  public String toString() {
    return macroName;
  }
}
