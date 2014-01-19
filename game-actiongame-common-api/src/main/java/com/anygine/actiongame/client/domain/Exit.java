package com.anygine.actiongame.client.domain;


public interface Exit
  <S extends ActionGameComponentState,
  L extends ActionGameLevel<?, ?>,
  P extends ActionGamePlayer<?, ?>>
  extends ActionGameComponent<S, L> {
	boolean isLocked();
	boolean enter(P player);
}
