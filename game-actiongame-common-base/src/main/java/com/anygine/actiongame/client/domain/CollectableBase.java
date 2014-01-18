package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.SoundWithPath;
import com.anygine.core.common.client.annotation.FieldRef;
import com.anygine.core.common.client.annotation.Storable;
import com.anygine.core.common.client.domain.GameComponentBase;
import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.core.common.client.input.Input;

@Storable
public abstract class CollectableBase 
/* <S extends ActionGameComponentState, 
P extends ActionGamePlayer<S, P, L, GC, A, E>,
L extends ActionGameLevel<S, P, L, GC, A, E>,
GC extends ActionGameComponent<S, P, L, GC, A, E>,
A extends ActionGameActor<S, P, L, GC, A, E>,
E extends Enemy<S, P, L, GC, A, E>> */
  <S extends ActionGameComponentState, 
  L extends ActionGameLevel<?>>
  extends GameComponentBase<S> 
  implements Collectable<S> {

	protected final float bounceHeight;
	protected final float bounceRate;
	protected final float bounceSync;
	
	protected final SoundWithPath collectedSound;
	
	protected ActionGameActor<?> ownedBy;
	protected float bounce;
	
	public CollectableBase(
//	    long id, int version, 
			L level, String name, String type, Vector2 position, int width, 
			int height, int points, float bounceHeight, float bounceRate, 
			float bounceSync, 
			@FieldRef(field="texture", attribute="path") String spritePath, 
			@FieldRef(field="collectedSound", attribute="path") String collectedSoundPath) {
		super(
//		    id, version, 
				name, type, position, width, height, Vector2.Zero, points, 
				level, spritePath);
		this.bounceHeight = bounceHeight;
		this.bounceRate = bounceRate;
		this.bounceSync = bounceSync;
		collectedSound = new SoundWithPath(collectedSoundPath);
	}

/*	
	public CollectableBase(CollectableBase other, Object updateSpec) {
	  super(other, updateSpec);
	  this.bounceHeight = other.bounceHeight;
	  this.bounceRate = other.bounceRate;
	  this.bounceSync = other.bounceSync;
	  this.collectedSound = other.collectedSound;
	}
*/
	
  @Override
	public <A extends ActionGameActor<?>> void onCollected(A collectedBy) {
		collectedSound.play();
	}

/*  
  @Override
  public void onCollected(P collectedBy) {
    collectedSound.play();
  }
*/
  
	@Override
	public boolean update(Input input, float gameTime) {
		// Bounce along a sine curve over time.
		// Include the X coordinate so that neighboring gems bounce in a nice wave
		// pattern.
		double t = (gameTime / 60.0f) * bounceRate + getPosition().X * bounceSync;
		bounce = (float) Math.sin(t) * bounceHeight * getTexture().height();
		return true;
	}

	@Override
	public Vector2 getPosition() {
		return position.add(new Vector2(0.0f, bounce));
	}

/*	
  @Override
  public JsonType getJsonType() {
    return JsonType.CollectableBase;
  }

  protected void _writeJson(Writer writer) {
    super._writeJson(writer);
    writer.value("bounceHeight", bounceHeight);
    writer.value("bounceRate", bounceRate);
    writer.value("bounceSync", bounceSync);
    write(collectedSound, writer, "collectedSound");
    write(ownedBy, writer, "ownedBy");
    writer.value("bounce", bounce);
   }

  @Override
  protected void _write(EntityWriter entityWriter) {
    super._write(entityWriter);
    Json.Writer writer = entityWriter.getWriter();
    writer.value("bounceHeight", bounceHeight);
    writer.value("bounceRate", bounceRate);
    writer.value("bounceSync", bounceSync);
    write(collectedSound, writer, "collectedSound");
    writeEntity(ownedBy, entityWriter, "ownedBy");
    writer.value("bounce", bounce);
  }
  
  @Override
  public void update(Json.Object jsonObj) {
    super.update(jsonObj);
    update(SoundWithPath.class, collectedSound, jsonObj, "collectedSound");
    ownedBy = updateEntity(Actor.class, ownedBy, jsonObj, "ownedBy");
    bounce = update(bounce, jsonObj, "bounce");
  }

  @Override
  public <T extends Comparable<? extends T>> int compareTo(T attribute, T value) {
    // TODO: Implement
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public Entity entityCopy(Object updateSpec) {
    return new CollectableBase(this, updateSpec);
  }
*/
  
}
