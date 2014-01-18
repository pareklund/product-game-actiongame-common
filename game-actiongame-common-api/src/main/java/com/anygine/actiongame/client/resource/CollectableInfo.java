package com.anygine.actiongame.client.resource;

import playn.core.Json.Object;

import com.anygine.actiongame.client.domain.Collectable;
import com.anygine.core.common.client.resource.SpriteInfo;

public class CollectableInfo<C extends Collectable> extends SpriteInfo<C> {

	private static final String COLLECTED_SOUND_PATH_ID = "collectedSoundPath";
	
	private final float bounceHeight;
	private final float bounceRate;
	private final float bounceSync;

	public CollectableInfo(Object resourceInfo) {
		super(resourceInfo);
		Object bounce = resourceInfo.getObject("bounce");
		if (bounce != null) {
			bounceHeight = (float) bounce.getNumber("height");
			bounceRate = (float) bounce.getNumber("rate");
			bounceSync = (float) bounce.getNumber("sync");
		} else {
			bounceHeight = 0.0f;
			bounceRate = 0.0f;
			bounceSync = 0.0f;
		}
		putSound(resourceInfo, COLLECTED_SOUND_PATH_ID);
	}

	public String getCollectedSoundPath() {
		return sounds.get(COLLECTED_SOUND_PATH_ID);
	}

	public float getBounceHeight() {
		return bounceHeight;
	}

	public float getBounceRate() {
		return bounceRate;
	}

	public float getBounceSync() {
		return bounceSync;
	}

  @Override
  public Class<C> getKlass() {
    return (Class<C>) Collectable.class;
  }

}
