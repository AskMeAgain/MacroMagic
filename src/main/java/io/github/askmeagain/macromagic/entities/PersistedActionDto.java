package io.github.askmeagain.macromagic.entities;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PersistedActionDto {

  String actionId;
  Boolean isInternal;
  char additionalInformation;

}
