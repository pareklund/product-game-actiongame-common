package com.anygine.actiongame.client.domain;

import java.util.List;

public interface Ammo
  <S extends ActionGameComponentState,
   L extends ActionGameLevel<?, ?>,
   A extends ActionGameActor<?, ?>>
  extends Collectable<S, L, A> {
	boolean isEmpty();
	// Projectile is typed to A, whereas Ammo is not
	List<Projectile<S, A, L>> use(int num, float speed);
	void add(int num);
	int getAmount();
}
