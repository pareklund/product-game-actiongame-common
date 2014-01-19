package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.annotation.Storable;

@Storable
public interface Collectable
  <S extends ActionGameComponentState,
   L extends ActionGameLevel<?, ?>>
  extends ActionGameComponent<S, L> {
  // TODO: Possibly make this method return an InventoryItem (typed to A)
  <A extends ActionGameActor<S, L>> void onCollected(A collectedBy);
//  void onCollected(P collectedBy);
}