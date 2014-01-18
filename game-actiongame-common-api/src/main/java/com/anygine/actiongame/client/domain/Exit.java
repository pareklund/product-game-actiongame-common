package com.anygine.actiongame.client.domain;


public interface Exit
/* <S extends ActionGameComponentState,
P extends ActionGamePlayer<S, P, L, GC, A, E>,
L extends ActionGameLevel<S, P, L, GC, A, E>,
GC extends ActionGameComponent<S, P, L, GC, A, E>,
A extends ActionGameActor<S, P, L, GC, A, E>,
E extends Enemy<S, P, L, GC, A, E>> */
  <S extends ActionGameComponentState,
  L extends ActionGameLevel<?>>
  extends ActionGameComponent<S> {
	boolean isLocked();
	<P extends ActionGamePlayer<?, ?>> boolean enter(P player);
}
