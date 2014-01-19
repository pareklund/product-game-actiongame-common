package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.annotation.Storable;

@Storable
public interface Collectable
  <S extends ActionGameComponentState,
   L extends ActionGameLevel<?, ?>,
   A extends ActionGameActor<?, ?>>
  extends ActionGameComponent<S, L> {
  // TODO: Possibly make this method return an InventoryItem (typed to A)
  void onCollected(A collectedBy);
}