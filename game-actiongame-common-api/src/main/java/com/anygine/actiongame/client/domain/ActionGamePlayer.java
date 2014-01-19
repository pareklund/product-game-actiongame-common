package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.domain.Player;

public interface ActionGamePlayer
  <S extends ActionGameComponentState,
  L extends ActionGameLevel<?, ?>>
  extends Player<S, L>, ActionGameActor<S, L> {
  int getLives();
  void decreaseLife();
  void addLife();
  void onReachedExit();
  int getNumCollectedInLevel();
  void increaseNumCollectedInLevel();
  boolean isInvulnerable();
}
