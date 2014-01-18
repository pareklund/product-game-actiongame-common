package com.anygine.actiongame.client.resource;

import playn.core.Json.Object;

import com.anygine.actiongame.client.domain.Enemy;
import com.anygine.core.common.client.resource.ActorInfo;

public class EnemyInfo extends ActorInfo<Enemy> {

	private static final String INTERMITTENT_SOUND_PATH_ID = "intermittentSoundPath";
	
	public EnemyInfo(Object resourceInfo) {
		super(resourceInfo);
		putSound(resourceInfo, INTERMITTENT_SOUND_PATH_ID);
	}

	public String getIntermittentSoundPath() {
		return sounds.get(INTERMITTENT_SOUND_PATH_ID);
	}

  @Override
  public Class<Enemy> getKlass() {
    return Enemy.class;
  }
}
