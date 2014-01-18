package com.anygine.actiongame.client.resource;


import com.anygine.actiongame.client.domain.Ammo;

import playn.core.Json.Object;

public class AmmoInfo extends CollectableInfo<Ammo> {

	private final int initialAmount;
	private final String bulletName;
	
	public AmmoInfo(Object resourceInfo) {
		super(resourceInfo);
		initialAmount = resourceInfo.getInt("initialAmount");
		bulletName = resourceInfo.getString("bullet");
	}

	public int getInitialAmount() {
		return initialAmount;
	}

	public String getBulletName() {
		return bulletName;
	}

  @Override
  public Class<Ammo> getKlass() {
    // TODO Auto-generated method stub
    return null;
  }
}
