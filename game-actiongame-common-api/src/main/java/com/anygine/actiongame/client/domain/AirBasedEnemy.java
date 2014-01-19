package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.Animation;

public interface AirBasedEnemy
  <S extends ActionGameComponentState,
   L extends ActionGameLevel<?, ?>>
  extends Enemy<S, L> {
	Animation getFlightAnimation();
}
