package com.anygine.actiongame.client.domain;

public interface Valuable
  <S extends ActionGameComponentState,
   L extends ActionGameLevel<?, ?>>
  extends Collectable<S, L> {
}
