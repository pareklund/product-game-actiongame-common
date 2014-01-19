package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.domain.GameComponent;

public interface Projectile
  <S extends ActionGameComponentState,
  A extends ActionGameActor<?, ?>,
  L extends ActionGameLevel<?, ?>>
  extends GameComponent<S, L> {
  A getOwner();
  int getDamage();
}
