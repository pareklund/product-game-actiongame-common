package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.domain.GameComponentBase;
import com.anygine.core.common.client.geometry.Vector2;

public abstract class ActionGameComponentBase
/* <S extends ActionGameComponentState, 
P extends ActionGamePlayer<S, P, L, GC, A, E>,
L extends ActionGameLevel<S, P, L, GC, A, E>, 
GC extends ActionGameComponent<S, P, L, GC, A, E>,
A extends ActionGameActor<S, P, L, GC, A, E>,
E extends Enemy<S, P, L, GC, A, E>> */ 
  <S extends ActionGameComponentState, 
  L extends ActionGameLevel<?>>
  extends GameComponentBase<S> 
  implements ActionGameComponent<S> {

  public ActionGameComponentBase(
      String name, String type, Vector2 position, int width, int height, 
      Vector2 velocity, int points, L level, String spritePath) {
    super(name, type, position, width, height, velocity, points, level, spritePath);
  }
}
