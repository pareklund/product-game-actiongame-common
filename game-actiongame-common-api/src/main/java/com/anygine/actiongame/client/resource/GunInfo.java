package com.anygine.actiongame.client.resource;



import playn.core.Json.Object;

import com.anygine.actiongame.client.domain.Gun;

public class GunInfo extends CollectableInfo<Gun> {

	private static final String OUT_OF_AMMO_SOUND_PATH_ID = "outOfAmmoSoundPath";
	private static final String FIRING_SOUND_PATH_ID = "firingSoundPath";

	private final int roundsPerShot;
	private final float projectileSpeed;
	private final String ammoName;

	public GunInfo(Object resourceInfo) {
		super(resourceInfo);
		
		roundsPerShot = resourceInfo.getInt("roundsPerShot");
		projectileSpeed = (float) resourceInfo.getNumber("projectileSpeed");
		ammoName = resourceInfo.getString("ammo");
		
		putSound(resourceInfo, OUT_OF_AMMO_SOUND_PATH_ID);
		putSound(resourceInfo, FIRING_SOUND_PATH_ID);
	}

	public int getRoundsPerShot() {
		return roundsPerShot;
	}

	public float getProjectileSpeed() {
		return projectileSpeed;
	}

	public String getOutOfAmmoSoundPath() {
		return sounds.get(OUT_OF_AMMO_SOUND_PATH_ID);
	}

	public String getFiringSoundPath() {
		return sounds.get(FIRING_SOUND_PATH_ID);
	}

	public String getAmmoName() {
		return ammoName;
	}

  @Override
  public Class<Gun> getKlass() {
    return Gun.class;
  }
}
