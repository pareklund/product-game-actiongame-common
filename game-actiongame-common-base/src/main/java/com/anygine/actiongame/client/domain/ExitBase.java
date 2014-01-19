package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.SoundWithPath;
import com.anygine.core.common.client.annotation.FieldRef;
import com.anygine.core.common.client.annotation.Storable;
import com.anygine.core.common.client.domain.GameComponentBase;
import com.anygine.core.common.client.geometry.Vector2;

@Storable
public abstract class ExitBase
  <S extends ActionGameComponentState,
  P extends ActionGamePlayer<?, ?>,
  L extends ActionGameLevel<?, ?>>
  extends GameComponentBase<S, L> 
  implements Exit<S, L, P> {

	protected final SoundWithPath exitingSound;
	protected final SoundWithPath lockedSound;
	protected final SoundWithPath unlockedSound;

	protected boolean locked;
	protected String keyRequired;

	
	public ExitBase(
//	    long id, int version, 
			L level, String name, String type, Vector2 position, int width, 
			int height, boolean locked, String keyRequired, 
			@FieldRef(field="texture", attribute="path") String spritePath,
			@FieldRef(field="exitingSound", attribute="path") String exitingSoundPath, 
			@FieldRef(field="lockedSound", attribute="path") String lockedSoundPath,
			@FieldRef(field="unlockedSound", attribute="path") String unlockedSoundPath) {
		super(
//		    id, version, 
				name, type, position, width, height, Vector2.Zero, 0, level, 
				spritePath);
		this.locked = locked;
		this.keyRequired = keyRequired;
		// TODO: Add animation for opening door
		exitingSound = new SoundWithPath(exitingSoundPath);
		// TODO: Possibly handle this by type distinction (LockableExit)
		if (lockedSoundPath != null) {
			lockedSound = new SoundWithPath(lockedSoundPath);
			unlockedSound = new SoundWithPath(unlockedSoundPath);
		} else {
		  lockedSound = null;
		  unlockedSound = null;
		}
	}

/*	
	public ExitBase(ExitBase other, Object updateSpec) {
	  super(other, updateSpec);
	  this.exitingSound = other.exitingSound;
	  this.lockedSound = other.lockedSound;
	  this.unlockedSound = other.unlockedSound;
	  this.locked = other.locked;
	  this.keyRequired = other.keyRequired;
	}
*/
	
  @Override
	public boolean isLocked() {
		return locked;
	}

	@Override
	public boolean enter(P player) {
		if (!locked) {
			player.onReachedExit();
			exitingSound.play();
			return true;
		} else {
			Inventory playerInventory = player.getInventory();
			if (playerInventory.isEmpty()) {
				return false;
			}
			InventoryItem<?, ?> inventoryItem = playerInventory.getSelectedItem();
			if ("Key".equals(inventoryItem.getName())) {
				locked = false;
				unlockedSound.play();
				return false;
			}
			lockedSound.play();
			return false;
		}
	}

/*	
  @Override
  public JsonType getJsonType() {
    return JsonType.ExitBase;
  }

  @Override
  public void update(Json.Object jsonObj) {
    super.update(jsonObj);
    update(SoundWithPath.class, exitingSound, jsonObj, "exitingSound");
    update(SoundWithPath.class, lockedSound, jsonObj, "lockedSound");
    update(SoundWithPath.class, unlockedSound, jsonObj, "unlockedSound");
    locked = update(locked, jsonObj, "locked");
    keyRequired = update(keyRequired, jsonObj, "keyRequired");
  }
  
  @Override
  protected void _writeJson(Writer writer) {
    super._writeJson(writer);
    write(exitingSound, writer, "exitingSound");
    write(lockedSound, writer, "lockedSound");
    write(unlockedSound, writer, "unlockedSound");
    writer.value("locked", locked);
    writeString(keyRequired, writer, "keyRequired");
  }

  @Override
  protected void _write(EntityWriter entityWriter) {
    super._write(entityWriter);
    Json.Writer writer = entityWriter.getWriter();
    write(exitingSound, writer, "exitingSound");
    write(lockedSound, writer, "lockedSound");
    write(unlockedSound, writer, "unlockedSound");
    writer.value("locked", locked);
    writeString(keyRequired, writer, "keyRequired");
  }
  
  @Override
  public <T extends Comparable<? extends T>> int compareTo(T attribute, T value) {
    // TODO: Implement
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public Entity entityCopy(Object updateSpec) {
    return new ExitBase(this, updateSpec);
  }
  */
  
}
