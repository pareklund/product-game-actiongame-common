package com.anygine.actiongame.client.resource;


import playn.core.Json.Object;

public class GroundBasedEnemyInfo extends EnemyInfo {

	private static final String RUN_TEXTURE_PATH_ID = "runAnimationPath";
	
	protected final float maxWaitTime;
	protected final float chargeSpeed;

	public GroundBasedEnemyInfo(Object resourceInfo) {
		super(resourceInfo);
		maxWaitTime = (float) resourceInfo.getNumber("maxWaitTime");
		chargeSpeed = (float) resourceInfo.getNumber("chargeSpeed");
		putTexture(resourceInfo, RUN_TEXTURE_PATH_ID);
	}

	public float getMaxWaitTime() {
		return maxWaitTime;
	}

	public float getChargeSpeed() {
		return chargeSpeed;
	}

	public String getRunAnimationPath() {
		return images.get(RUN_TEXTURE_PATH_ID);
	}

}
