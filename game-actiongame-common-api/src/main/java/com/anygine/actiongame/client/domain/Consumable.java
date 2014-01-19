package com.anygine.actiongame.client.domain;

public interface Consumable
  <S extends ActionGameComponentState,
  L extends ActionGameLevel<?, ?>,
  A extends ActionGameActor<?, ?>>
  extends InventoryItem<S, L, A> {
	Effect getEffect();
}
