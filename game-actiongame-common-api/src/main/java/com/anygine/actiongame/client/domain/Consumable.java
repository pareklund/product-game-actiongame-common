package com.anygine.actiongame.client.domain;

public interface Consumable
  <S extends ActionGameComponentState,
  L extends ActionGameLevel<?, ?>>
  extends InventoryItem<S, L> {
	Effect getEffect();
}
