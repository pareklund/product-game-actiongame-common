package com.anygine.actiongame.client.domain;

public interface Weapon
  <S extends ActionGameComponentState,
   L extends ActionGameLevel<?, ?>>
  extends InventoryItem<S, L> {
}
