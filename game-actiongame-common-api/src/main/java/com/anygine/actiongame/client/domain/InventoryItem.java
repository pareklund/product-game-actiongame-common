package com.anygine.actiongame.client.domain;

import java.util.List;

import com.anygine.core.common.client.annotation.Storable;

@Storable
public interface InventoryItem
  <S extends ActionGameComponentState,
   L extends ActionGameLevel<?, ?>>
  extends Collectable<S, L> {
	void use();
	List<InventoryListener> getListeners();
}
