package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.Animation;

public interface GroundBasedEnemy
/* <S extends ActionGameComponentState,
P extends ActionGamePlayer<S, P, L, GC, A, E>,
L extends ActionGameLevel<S, P, L, GC, A, E>,
GC extends ActionGameComponent<S, P, L, GC, A, E>,
A extends ActionGameActor<S, P, L, GC, A, E>,
E extends Enemy<S, P, L, GC, A, E>> */ 
  <S extends ActionGameComponentState,
  L extends ActionGameLevel<?>,
  A extends ActionGameActor<?>>
  extends Enemy<S> {
	float getWaitTime();
	Animation getIdleAnimation();
	Animation getRunAnimation();
}
