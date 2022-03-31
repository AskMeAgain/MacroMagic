package io.github.askmeagain.macromagic.service;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.ui.components.JBList;
import io.github.askmeagain.macromagic.actions.internal.PressKeyAction;
import io.github.askmeagain.macromagic.entities.PersistedActionDto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public final class MacroMagicPersistingService {

  private final ActionManager actionManager = ActionManager.getInstance();

  @Getter
  private final DefaultListModel<String> persistedActions = new DefaultListModel<>();

  public JBList<String> createJbList(){
    return new JBList<>(persistedActions);
  }

  public void persistActions(DefaultListModel<AnAction> actions, String name) {
    var result = new ArrayList<PersistedActionDto>();

    for (int i = 0; i < actions.size(); i++) {
      result.add(serializeAction(actions.get(i)));
    }

    log.info("'{}' got persisted: {}", name, result);
  }

  public List<AnAction> loadActions(String name) {
    var list = List.of(
        PersistedActionDto.builder()
            .actionId("io.github.askmeagain.macromagic.actions.internal.PressKeyAction")
            .additionalInformation('a')
            .build(),
        PersistedActionDto.builder()
            .actionId("io.github.askmeagain.macromagic.actions.internal.PressKeyAction")
            .additionalInformation('a')
            .build()
    );

    return list.stream()
        .map(this::deserializeAction)
        .collect(Collectors.toList());
  }

  public AnAction deserializeAction(PersistedActionDto persistedActionDto) {
    var action = actionManager.getAction(persistedActionDto.getActionId());

    if (action instanceof PressKeyAction) {
      var castedAction = (PressKeyAction) action;
      castedAction.setKeyCode(persistedActionDto.getAdditionalInformation());
      return castedAction;
    }

    return action;
  }

  public PersistedActionDto serializeAction(AnAction anAction) {
    if (anAction instanceof PressKeyAction) {
      var castedAction = (PressKeyAction) anAction;
      return PersistedActionDto.builder()
          .actionId("io.github.askmeagain.macromagic.actions.internal.PressKeyAction")
          .isInternal(true)
          .additionalInformation(castedAction.getOriginalChar())
          .build();
    }

    return PersistedActionDto.builder()
        .isInternal(false)
        .actionId(actionManager.getId(anAction))
        .build();
  }

  public static MacroMagicPersistingService getInstance() {
    return ApplicationManager.getApplication()
        .getService(MacroMagicPersistingService.class);
  }
}
