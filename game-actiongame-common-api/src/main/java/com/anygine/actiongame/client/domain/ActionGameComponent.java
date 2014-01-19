package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.domain.GameComponent;

public interface ActionGameComponent
  <S extends ActionGameComponentState,
  L extends ActionGameLevel<?, ?>>
  extends GameComponent<S, L> {
}
