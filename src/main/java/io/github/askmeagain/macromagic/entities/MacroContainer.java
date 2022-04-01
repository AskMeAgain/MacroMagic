package io.github.askmeagain.macromagic.entities;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class MacroContainer {

  String macroName;
  List<PersistedActionDto> actions;

  @Override
  public String toString() {
    return macroName;
  }
}
