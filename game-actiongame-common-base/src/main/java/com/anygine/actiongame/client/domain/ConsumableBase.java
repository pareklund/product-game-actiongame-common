package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.SoundWithPath;
import com.anygine.core.common.client.annotation.FieldRef;
import com.anygine.core.common.client.annotation.Storable;
import com.anygine.core.common.client.geometry.Vector2;

@Storable
public abstract class ConsumableBase 
  <S extends ActionGameComponentState, 
  L extends ActionGameLevel<?, ?>,
  A extends ActionGameActor<?, ?>>
  extends InventoryItemBase<S, L, A> 
  implements Consumable<S, L> {

	private final Effect effect;
	private final SoundWithPath consumingSound;
	private final boolean soundLooping;
	
	
	public ConsumableBase(
//	    long id, int version, 
			L level, String name, String type, Vector2 position, int width, 
			int height, int points, float bounceHeight, float bounceRate, 
			float bounceSync, Effect effect, 
			boolean soundLooping, 
			@FieldRef(field="texture", attribute="path") String spritePath, 
			@FieldRef(field="collectedSound", attribute="path") String collectedSoundPath, 
			@FieldRef(field="consumingSound", attribute="path") String consumingSoundPath) {
		super(
//		    id, version, 
				level, name, type, position, width, height, points, 
				bounceHeight, bounceRate, bounceSync, spritePath, 
				collectedSoundPath);
		this.effect = effect;
		consumingSound = new SoundWithPath(consumingSoundPath);
		this.soundLooping = soundLooping;
	}

/*	
	@Override
	public void onCollected(P collectedBy) {
		super.onCollected(collectedBy);
	}
	*/
	
	@Override
	public void use() {
		ConsumptionListener listener = null;
		if (soundLooping) {
			consumingSound.setLooping(true);
			listener = new ConsumptionListener() {
				
				@Override
				public void onDone() {
					consumingSound.stop();
				}
			};
		}
		ownedBy.onConsume(this, listener);
		ownedBy.getInventory().removeSelectedItem();
		consumingSound.play();
	}

	@Override
	public Effect getEffect() {
		return effect;
	}

	SoundWithPath getConsumingSound() {
	  return consumingSound;
	}
	
	boolean isSoundLooping() {
	  return soundLooping;
	}
	
/*	
	@Override
	public JsonType getJsonType() {
	  return JsonType.ConsumableBase;
	}
	
	@Override
	public void update(Json.Object jsonObj) {
	  super.update(jsonObj);
	  update(SoundWithPath.class, consumingSound, jsonObj, "consumingSound");
	}
	
	@Override
	protected void _writeJson(Json.Writer writer) {
	  super._writeJson(writer);
	  write(effect, writer, "effect");
	  write(consumingSound, writer, "consumingSound");
	  writer.value("soundLooping", soundLooping);
	}

	@Override
	protected void _write(EntityWriter entityWriter) {
	  super._write(entityWriter);
	  Json.Writer writer = entityWriter.getWriter();
    write(effect, writer, "effect");
    write(consumingSound, writer, "consumingSound");
    writer.value("soundLooping", soundLooping);
	}
	*/
	
}
