package com.anygine.actiongame.client.domain;

public interface Valuable
  <S extends ActionGameComponentState,
   L extends ActionGameLevel<?, ?>,
   A extends ActionGameActor<?, ?>>
  extends Collectable<S, L, A> {
}
