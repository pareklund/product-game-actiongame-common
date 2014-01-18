package com.anygine.actiongame.client.resource;

import playn.core.Json;

import com.anygine.core.common.client.resource.Info;
import com.anygine.core.common.client.resource.ResourceInfoMap;

public abstract class ActionGameResourceInfoMap extends ResourceInfoMap {

	public ActionGameResourceInfoMap() {
	  super();
	}

	@Override
  protected Info<?> getResourceInfo(Json.Object resourceInfoObj) {
    String type = resourceInfoObj.getString("type");
    if ("GroundBasedEnemy".equals(type)) {
      return new GroundBasedEnemyInfo(resourceInfoObj);
    } else if ("AirBasedEnemy".equals(type)) {
      return new EnemyInfo(resourceInfoObj);
    } else if ("Collectable".equals(type) || "Valuable".equals(type)) {
      return new CollectableInfo(resourceInfoObj);
    } else if ("Gun".equals(type)) {
      return new GunInfo(resourceInfoObj);
    } else if ("Ammo".equals(type)) {
      return new AmmoInfo(resourceInfoObj);
    } else if ("Exit".equals(type)) {
      return new ExitInfo(resourceInfoObj);
    } else if ("Projectile".equals(type)) {
      return new ProjectileInfo(resourceInfoObj);
    } else if ("Consumable".equals(type)) {
      return new ConsumableInfo(resourceInfoObj);
    } else {
      return super.getResourceInfo(resourceInfoObj);
    }
	}
}
