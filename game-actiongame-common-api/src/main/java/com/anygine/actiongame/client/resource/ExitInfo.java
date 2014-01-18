package com.anygine.actiongame.client.resource;


import com.anygine.actiongame.client.domain.Exit;
import com.anygine.core.common.client.resource.SpriteInfo;

import playn.core.Json.Object;

public class ExitInfo extends SpriteInfo<Exit> {

	private static final String EXITING_SOUND_PATH_ID = "exitingSoundPath";
	private static final String LOCKED_SOUND_PATH_ID = "lockedSoundPath";
	private static final String UNLOCKED_SOUND_PATH_ID = "unlockedSoundPath";

	private final boolean locked;
	private final String keyRequired;

	public ExitInfo(Object resourceInfo) {
		super(resourceInfo);
		locked = resourceInfo.getBoolean("locked");
		keyRequired = expandVariables(
				resourceInfo, resourceInfo.getString("keyRequired"));
		putSound(resourceInfo, EXITING_SOUND_PATH_ID);
		putSound(resourceInfo, LOCKED_SOUND_PATH_ID);
		putSound(resourceInfo, UNLOCKED_SOUND_PATH_ID);
	}

	public boolean isLocked() {
		return locked;
	}

	public String getKeyRequired() {
		return keyRequired;
	}

	public String getExitingSoundPath() {
		return sounds.get(EXITING_SOUND_PATH_ID);
	}

	public String getLockedSoundPath() {
		return sounds.get(LOCKED_SOUND_PATH_ID);
	}

	public String getUnlockedSoundPath() {
		return sounds.get(UNLOCKED_SOUND_PATH_ID);
	}

  @Override
  public Class<Exit> getKlass() {
    return Exit.class;
  }

}
