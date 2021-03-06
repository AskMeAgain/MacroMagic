package io.github.askmeagain.macromagic.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersistedActionDto {

  private String actionId;
  private String additionalInformation;

}
