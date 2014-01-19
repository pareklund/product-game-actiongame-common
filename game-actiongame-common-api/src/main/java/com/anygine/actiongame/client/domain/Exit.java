package com.anygine.actiongame.client.domain;


public interface Exit
  <S extends ActionGameComponentState,
  L extends ActionGameLevel<?, ?>>
  extends ActionGameComponent<S, L> {
	boolean isLocked();
	<P extends ActionGamePlayer<?, ?>> boolean enter(P player);
}
