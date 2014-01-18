package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.domain.GameComponent;

public interface Projectile
/* <S extends ActionGameComponentState,
P extends ActionGamePlayer<S, P, L, GC, A, E>,
L extends ActionGameLevel<S, P, L, GC, A, E>,
GC extends ActionGameComponent<S, P, L, GC, A, E>,
A extends ActionGameActor<S, P, L, GC, A, E>,
E extends Enemy<S, P, L, GC, A, E>> */
  <S extends ActionGameComponentState,
  A extends ActionGameActor<?>>
  extends GameComponent<S> {
  A getOwner();
  int getDamage();
}
