package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.annotation.Embeddable;

@Embeddable
public class Effect {

  // TODO: Change to make it non-platformer specific
	public static enum EffectType {
		SPEED, JUMP_HEIGHT, INVULNERABILITY, TIME, LIFE;
		
		public static EffectType getEffectType(String effectType) {
			if ("speed".equalsIgnoreCase(effectType)) {
				return SPEED;
			} else if ("jumpHeight".equalsIgnoreCase(effectType)){
				return JUMP_HEIGHT;
			} else if ("invulnerability".equalsIgnoreCase(effectType)){
				return INVULNERABILITY;
			} else if ("time".equalsIgnoreCase(effectType)){
				return TIME;
			} else if ("life".equalsIgnoreCase(effectType)){
				return LIFE;
			} 
			throw new RuntimeException("Illegal effect type: " + effectType);
		}
	}
	
	private final EffectType type;
	private final float amount;
	private final float duration;
	private final float warnAt;
	
	public Effect(
			EffectType type, float amount, float duration, float warnAt) {
		super();
		this.type = type;
		this.amount = amount;
		this.duration = duration;
		this.warnAt = warnAt;
	}

	/*
	public Effect(Object fields) {
	  super(fields);
	  type = EffectType.valueOf(fields.getString("type"));
	  amount = (float) fields.getNumber("amount");
	  duration = (float) fields.getNumber("duration");
	  warnAt = (float) fields.getNumber("warnAt");
	}
*/
	
  public EffectType getType() {
		return type;
	}

	public float getAmount() {
		return amount;
	}

	public float getDuration() {
		return duration;
	}

	public float getWarnAt() {
		return warnAt;
	}

/*	
  @Override
  public JsonType getJsonType() {
    return JsonType.Effect;
  }

  @Override
  protected void _writeJson(Writer writer) {
    writeEnum(type, writer, "type");
    writer.value("amount", amount);
    writer.value("duration", duration);
    writer.value("warnAt", warnAt);
  }

  @Override
  public void update(Object jsonObj) {
    // All fields immutable, possible throw or warn
  }
*/
	
}
