package com.anygine.actiongame.client.domain;

public interface Gun 
/* <S extends ActionGameComponentState,
P extends ActionGamePlayer<S, P, L, GC, A, E>,
L extends ActionGameLevel<S, P, L, GC, A, E>,
GC extends ActionGameComponent<S, P, L, GC, A, E>,
A extends ActionGameActor<S, P, L, GC, A, E>,
E extends Enemy<S, P, L, GC, A, E>> */ 
  <S extends ActionGameComponentState>
  extends Weapon<S> {
	boolean acceptsAmmo(Ammo<S> ammo);
	void addAmmo(Ammo<S> ammo);
}
