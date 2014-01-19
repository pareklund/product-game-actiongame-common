package com.anygine.actiongame.client.domain;

public interface InventoryListener
  <II extends InventoryItem<?, ?, ?>> {
  boolean onAdded(II item);
}
