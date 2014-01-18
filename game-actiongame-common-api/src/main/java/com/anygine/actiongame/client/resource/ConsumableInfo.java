package com.anygine.actiongame.client.resource;

import playn.core.Json.Object;

import com.anygine.actiongame.client.domain.Consumable;
import com.anygine.actiongame.client.domain.Effect;
import com.anygine.actiongame.client.domain.Effect.EffectType;

public class ConsumableInfo<C extends Consumable> extends CollectableInfo<C> {

	private static final String CONSUMING_SOUND_PATH_ID = "consumingSoundPath";

	private final Effect effect;
	private final boolean loopingSound;
	
	public ConsumableInfo(Object resourceInfo) {
		super(resourceInfo);
		loopingSound = resourceInfo.getBoolean("loopingSound");
		Object effectInfo = resourceInfo.getObject("effect");
		EffectType effectType = EffectType.getEffectType(
				effectInfo.getString("type"));
		float amount = (float) effectInfo.getNumber("amount");
		float duration = (float) effectInfo.getNumber("duration");
		float warnAt = (float) effectInfo.getNumber("warnAt");
		effect = new Effect(effectType, amount, duration, warnAt);
		putSound(resourceInfo, CONSUMING_SOUND_PATH_ID);
	}

	public Effect getEffect() {
		return effect;
	}

	public boolean isLoopingSound() {
		return loopingSound;
	}

	public String getConsumingSoundPath() {
		return sounds.get(CONSUMING_SOUND_PATH_ID);
	}

  @Override
  public Class<C> getKlass() {
    return (Class<C>) Consumable.class;
  }
}
