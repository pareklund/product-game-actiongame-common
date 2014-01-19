package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.Animation;

public interface GroundBasedEnemy
  <S extends ActionGameComponentState,
  L extends ActionGameLevel<?, ?>>
  extends Enemy<S, L> {
	float getWaitTime();
	Animation getIdleAnimation();
	Animation getRunAnimation();
}
