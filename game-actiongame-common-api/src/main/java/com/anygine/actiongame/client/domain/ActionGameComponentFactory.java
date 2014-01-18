package com.anygine.actiongame.client.domain;

import com.anygine.actiongame.client.resource.EnemyInfo;
import com.anygine.core.common.client.domain.Level;

public interface ActionGameComponentFactory {
  Enemy createEnemy(Level level, int x, int y, EnemyInfo info);
}
