package com.anygine.actiongame.client.domain;

import java.util.List;

import com.anygine.core.common.client.annotation.Storable;

@Storable
public interface InventoryItem
  <S extends ActionGameComponentState,
   L extends ActionGameLevel<?, ?>,
   A extends ActionGameActor<?, ?>>
  extends Collectable<S, L, A> {
	void use();
	List<InventoryListener> getListeners();
}
