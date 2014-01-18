package com.anygine.actiongame.client.domain;

import java.util.List;

public interface Ammo
/* <S extends ActionGameComponentState,
P extends ActionGamePlayer<S, P, L, GC, A, E>,
L extends ActionGameLevel<S, P, L, GC, A, E>,
GC extends ActionGameComponent<S, P, L, GC, A, E>,
A extends ActionGameActor<S, P, L, GC, A, E>,
E extends Enemy<S, P, L, GC, A, E>> */
  <S extends ActionGameComponentState>
  extends Collectable<S> {
	boolean isEmpty();
	// Projectile is typed to A, whereas Ammo is not
	<A extends ActionGameActor<?>> 
	  List<Projectile<S, A>> use(int num, float speed, A actor);
	void add(int num);
	int getAmount();
}
