package com.anygine.actiongame.client.domain;

public interface Gun 
  <S extends ActionGameComponentState,
   L extends ActionGameLevel<?, ?>>
  extends Weapon<S, L> {
	boolean acceptsAmmo(Ammo<?, ?, ?> ammo);
	void addAmmo(Ammo<?, ?, ?> ammo);
}
