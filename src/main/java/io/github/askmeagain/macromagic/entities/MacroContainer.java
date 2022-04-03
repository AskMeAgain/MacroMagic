package io.github.askmeagain.macromagic.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MacroContainer {

  private String macroName;
  private List<PersistedActionDto> actions = new ArrayList<>();

  @Override
  public String toString() {
    return macroName;
  }
}
