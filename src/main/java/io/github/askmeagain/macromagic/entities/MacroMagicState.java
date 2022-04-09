package io.github.askmeagain.macromagic.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MacroMagicState {

  List<MacroContainer> macros = new ArrayList<>();
  Boolean running = false;
  Integer historySize;

}
