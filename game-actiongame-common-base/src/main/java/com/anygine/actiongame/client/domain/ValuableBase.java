package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.annotation.FieldRef;
import com.anygine.core.common.client.annotation.Storable;
import com.anygine.core.common.client.geometry.Vector2;

@Storable
public abstract class ValuableBase
/* <S extends ActionGameComponentState,
P extends ActionGamePlayer<S, P, L, GC, A, E>,
L extends ActionGameLevel<S, P, L, GC, A, E>,
GC extends ActionGameComponent<S, P, L, GC, A, E>,
A extends ActionGameActor<S, P, L, GC, A, E>,
E extends Enemy<S, P, L, GC, A, E>> */
  <S extends ActionGameComponentState,
  L extends ActionGameLevel<?, ?, ?, ?, ?, ?, ?>,
  A extends ActionGameActor<?, ?, ?>>
  extends CollectableBase<S, L, A> 
  implements Valuable<S, L, A> {

	public ValuableBase(
//	    long id, int version, 
			L level, String name, String type, Vector2 position, int width, 
			int height, int points, float bounceHeight, float bounceRate,
			float bounceSync, 
			@FieldRef(field="texture", attribute="path") String spritePath, 
			@FieldRef(field="collectedSound", attribute="path") String collectedSoundPath) {
		super(
//		    id, version, 
				level, name, type, position, width, height, points, 
				bounceHeight, bounceRate, bounceSync, spritePath, 
				collectedSoundPath);
	}

	@Override
	public void onCollected(A collectedBy) {
		super.onCollected(collectedBy);
	}
	
/*	
  @Override
  public void onCollected(P collectedBy) {
    super.onCollected(collectedBy);
    collectedBy.increaseNumCollectedInLevel();
  }
  */
}
