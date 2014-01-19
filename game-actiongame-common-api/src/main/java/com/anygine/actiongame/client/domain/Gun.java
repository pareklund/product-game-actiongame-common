package com.anygine.actiongame.client.domain;

public interface Gun 
  <S extends ActionGameComponentState,
   L extends ActionGameLevel<?, ?>,
   A extends ActionGameActor<?, ?>>
  extends Weapon<S, L, A> {
	boolean acceptsAmmo(Ammo<S, L, A> ammo);
	void addAmmo(Ammo<S, L, A> ammo);
}
