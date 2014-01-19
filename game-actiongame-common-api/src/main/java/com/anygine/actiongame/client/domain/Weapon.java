package com.anygine.actiongame.client.domain;

public interface Weapon
  <S extends ActionGameComponentState,
   L extends ActionGameLevel<?, ?>,
   A extends ActionGameActor<?, ?>>
  extends InventoryItem<S, L, A> {
}
