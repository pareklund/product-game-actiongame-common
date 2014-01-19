package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.Animation;
import com.anygine.core.common.client.annotation.Field;
import com.anygine.core.common.client.annotation.FieldRef;
import com.anygine.core.common.client.annotation.Storable;
import com.anygine.core.common.client.geometry.Vector2;

@Storable
public class GroundBasedEnemyBase
  <S extends ActionGameComponentState,
  L extends ActionGameLevel<?, ?>,
  A extends ActionGameActor<?, ?>>
  extends EnemyBase<S, L>
  implements GroundBasedEnemy<S, L> {

  @Field(name = "maxWaitTime")
	protected final float maxWaitTime;
  
  @Field(name = "chargeSpeed")
	protected final float chargeSpeed;
	
  @Field(name = "runAnimation")
	protected final Animation runAnimation;

	// How long this enemy has been waiting before turning around.
  @Field(name = "waitTime")
	protected float waitTime;

	// TODO: Refactor out to EnemyState
  @Field(name = "charging")
	protected boolean charging;

	public GroundBasedEnemyBase(
//	    long id, int version, 
			String name, String type, L level, Vector2 position, int width, 
			int height, int points, float moveSpeed, 
			float maxWaitTime, float chargeSpeed, 
			// TODO: Place in PlatformerActorBase<S>
//			@FieldRef(field="fieldOfView", attribute="X") float fovHorizontal, 
//			@FieldRef(field="hearingRange", attribute="X") float hearHorizontal, 
			@FieldRef(field="defaultAnimation", attribute="path") String spritePath, 
			@FieldRef(field="killedSound", attribute="path") String killedSoundPath, 
			@FieldRef(field="intermittentSound", attribute="path") String intermittentSoundPath, 
			@FieldRef(field="runAnimation", attribute="path") String runAnimationPath) {
		super(
//		    id, version, 
				name, type, level, position, width, height, points, moveSpeed,
			// TODO: Place in PlatformerActorBase<S>
				/* fovHorizontal, hearHorizontal, */ 
				spritePath, killedSoundPath, intermittentSoundPath);
		// Air based enemies start out facing left (for now)
//		faceDirection = FaceDirection.Left;
		this.maxWaitTime = maxWaitTime;
		this.chargeSpeed = chargeSpeed;
		runAnimation = new Animation(runAnimationPath, width, height, 0.1f, true);
	}
	
/*
	public GroundBasedEnemyBase(GroundBasedEnemyBase other, Object updateSpec) {
	  super(other, updateSpec);
	  this.maxWaitTime = other.maxWaitTime;
	  this.chargeSpeed = other.chargeSpeed;
	  this.runAnimation = other.runAnimation;
	  this.waitTime = other.waitTime;
	  this.charging = other.charging;
	}
	*/

	protected float charge() {
		if (!charging) {
			intermittentSound.play();
		}
		charging = true;
		getAnimationPlayer().getAnimation().setFrameTime(0.07f);
		return chargeSpeed;
	}
	
	@Override
	public float getWaitTime() {
		return waitTime;
	}

	@Override
	public Animation getIdleAnimation() {
		return defaultAnimation;
	}

	@Override
	public Animation getRunAnimation() {
		return runAnimation;
	}

/*	
  @Override
  public JsonType getJsonType() {
    return JsonType.GroundBasedEnemyBase;
  }

  @Override
  public void update(Json.Object jsonObj) {
    super.update(jsonObj);
    update(Animation.class, runAnimation, jsonObj, "runAnimation");
    waitTime = update(waitTime, jsonObj, "waitTime");
    charging = update(charging, jsonObj, "charging");
  }
  
  @Override
  protected void _writeJson(Writer writer) {
    super._writeJson(writer);
    writer.value("maxWaitTime", maxWaitTime);
    writer.value("chargeSpeed", chargeSpeed);
    write(runAnimation, writer, "runAnimation");
    writer.value("waitTime", waitTime);
    writer.value("charging", charging);
  }

  @Override
  protected void _write(EntityWriter entityWriter) {
    super._write(entityWriter);
    Json.Writer writer = entityWriter.getWriter();
    writer.value("maxWaitTime", maxWaitTime);
    writer.value("chargeSpeed", chargeSpeed);
    write(runAnimation, writer, "runAnimation");
    writer.value("waitTime", waitTime);
    writer.value("charging", charging);
  }
  
  @Override
  public <T extends Comparable<? extends T>> int compareTo(T attribute, T value) {
    // TODO: Implement
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public GroundBasedEnemyBase entityCopy(Object updateSpec) {
    return new GroundBasedEnemyBase(this, updateSpec);
  }
*/
}
