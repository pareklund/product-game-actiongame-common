package com.anygine.actiongame.client.resource;

import com.anygine.actiongame.client.domain.Projectile;
import com.anygine.core.common.client.resource.SpriteInfo;

import playn.core.Json.Object;

public class ProjectileInfo extends SpriteInfo<Projectile> {

	private final float speed;
	private final int damage;
	
	public ProjectileInfo(Object resourceInfo) {
		super(resourceInfo);
		speed = (float) resourceInfo.getNumber("speed");
		damage = resourceInfo.getInt("damage");
	}

	public float getSpeed() {
		return speed;
	}

	public int getDamage() {
		return damage;
	}

  @Override
  public Class<Projectile> getKlass() {
    // TODO Auto-generated method stub
    return null;
  }
}
