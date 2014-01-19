package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.domain.GameComponentBase;
import com.anygine.core.common.client.geometry.Vector2;

public abstract class ActionGameComponentBase
  <S extends ActionGameComponentState,
  L extends ActionGameLevel<?, ?>>
  extends GameComponentBase<S, L>
  implements ActionGameComponent<S, L> {

  public ActionGameComponentBase(
      String name, String type, Vector2 position, int width, int height, 
      Vector2 velocity, int points, L level, String spritePath) {
    super(name, type, position, width, height, velocity, points, level, spritePath);
  }
}
