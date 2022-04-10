package io.github.askmeagain.macromagic.entities;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class ExportEntity {

  @Singular
  List<MacroContainer> macros;
}
