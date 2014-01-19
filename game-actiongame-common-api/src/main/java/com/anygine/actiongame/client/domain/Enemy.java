package com.anygine.actiongame.client.domain;

public interface Enemy
  <S extends ActionGameComponentState,
   L extends ActionGameLevel<?, ?>>
  extends ActionGameActor<S, L> {
}
