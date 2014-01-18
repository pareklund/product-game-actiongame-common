package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.annotation.Storable;

@Storable
public interface Collectable
/*<S extends ActionGameComponentState,
P extends ActionGamePlayer<S, P, L, GC, A, E>,
L extends ActionGameLevel<S, P, L, GC, A, E>,
GC extends ActionGameComponent<S, P, L, GC, A, E>,
A extends ActionGameActor<S, P, L, GC, A, E>,
E extends Enemy<S, P, L, GC, A, E>>*/
  <S extends ActionGameComponentState>
  extends ActionGameComponent<S> {
  // TODO: Possibly make this method return an InventoryItem (typed to A)
  <A extends ActionGameActor<?>> void onCollected(A collectedBy);
//  void onCollected(P collectedBy);
}