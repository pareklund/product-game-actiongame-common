package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.SoundWithPath;
import com.anygine.core.common.client.domain.Actor;

public interface ActionGameActor
/*<S extends ActionGameComponentState, 
P extends ActionGamePlayer<S, P, L, GC, A, E>,
L extends ActionGameLevel<S, P, L, GC, A, E>, 
GC extends ActionGameComponent<S, P, L, GC, A, E>,
A extends ActionGameActor<S, P, L, GC, A, E>,
E extends Enemy<S, P, L, GC, A, E>> */
  <S extends ActionGameComponentState,
  L extends ActionGameLevel<?, ?>>
  extends Actor<S, L> {
  FaceDirection getFaceDirection();
  void setFaceDirection(FaceDirection faceDirection);
  SoundWithPath getKilledSound();
  // An actor of type A can be killed by an actor of type A2, where A != A2
  <A extends ActionGameActor<?, ?>> void onKilled(A killedBy);
//  <AC extends ActionGameActor<?, ?, ?>> void onKilledHelper(AC killedBy);
  Inventory getInventory();
  float getMoveSpeed();
  void onConsume(
      Consumable<?, ?> consumable, ConsumptionListener listener);
  ConsumptionListener getConsumptionListener();
  void resetConsumptionListener();
  Effect getEffect();
}
