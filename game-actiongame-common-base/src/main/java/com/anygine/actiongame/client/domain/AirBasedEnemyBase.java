package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.Animation;
import com.anygine.core.common.client.annotation.FieldRef;
import com.anygine.core.common.client.annotation.Storable;
import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.core.common.client.input.Input;

@Storable
public class AirBasedEnemyBase
  <S extends ActionGameComponentState,
  L extends ActionGameLevel<?, ?>>
  extends EnemyBase<S, L>
  implements AirBasedEnemy<S, L> {

	public AirBasedEnemyBase(
	    String name, String type, L level, Vector2 position, int width, 
			int height, int points, float moveSpeed, 
/*
			// TODO: Move to platformer specific subclass
			@FieldRef(field="fieldOfView", attribute="X") float fovHorizontal, 
      // TODO: Move to platformer specific subclass
      @FieldRef(field="hearingRange", attribute="X") float hearHorizontal,
       */
      @FieldRef(field="texture", attribute="path") String spritePath, 
      @FieldRef(field="killedSound", attribute="path") String killedSoundPath,
      @FieldRef(field="intermittentSound", attribute="path") String intermittentSoundPath) {
		super(
				name, type, level, position, width, height, points, moveSpeed, 
				spritePath, killedSoundPath, intermittentSoundPath);
		// Air based enemies start out facing right (for now)
		faceDirection = FaceDirection.Right;
	}

  @Override
  public boolean update(Input input, float gameTime) {
    super.update(input, gameTime);
    Vector2 velocity = new Vector2(faceDirection.getValue() * moveSpeed * gameTime, 0.0f);
    position = position.add(velocity);
    return true;
  }
	
  @Override
	public Animation getFlightAnimation() {
		return defaultAnimation;
	}
}
