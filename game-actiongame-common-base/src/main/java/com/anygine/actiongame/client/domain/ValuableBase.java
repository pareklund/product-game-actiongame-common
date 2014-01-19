package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.annotation.FieldRef;
import com.anygine.core.common.client.annotation.Storable;
import com.anygine.core.common.client.geometry.Vector2;

@Storable
public abstract class ValuableBase
  <S extends ActionGameComponentState,
  L extends ActionGameLevel<?, ?>,
  A extends ActionGameActor<?, ?>>
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
}
