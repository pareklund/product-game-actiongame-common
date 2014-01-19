package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.SoundWithPath;
import com.anygine.core.common.client.annotation.Field;
import com.anygine.core.common.client.geometry.Vector2;

public abstract class EnemyBase
  <S extends ActionGameComponentState,
  L extends ActionGameLevel<?, ?>>
  extends ActionGameActorBase<S, L>
  implements Enemy<S, L> {

  @Field(name = "intermittentSound")
	protected final SoundWithPath intermittentSound;
	
	// TODO: Set points value from constructor argument
	public EnemyBase(
//	    long id, int version,
			String name, String type, L level, 
			Vector2 position, int width, 
			int height, int points, float moveSpeed, String spritePath, 
			String killedSoundPath, String intermittentSoundPath) {
		super(
//		    id, version,
				name, type, position, width, height, position /* velocity */, points, 
				moveSpeed, level, spritePath, killedSoundPath);
		intermittentSound = new SoundWithPath(intermittentSoundPath);
	}

/*	
	protected EnemyBase(EnemyBase other, Json.Object updateSpec) {
	  super(other, updateSpec);
	  this.intermittentSound = other.intermittentSound;
	}
	*/
	
  @Override
  public <A extends ActionGameActor<?, ?>> void onKilled(A killedBy) {
    killedBy.increaseScore(points);
		killedSound.play();
	}
	
	/*  
  @Override
  public void update(Json.Object jsonObj) {
    super.update(jsonObj);
    update(SoundWithPath.class, intermittentSound, jsonObj, "intermittentSound");
  }
  
  @Override
  protected void _writeJson(Json.Writer writer) {
    super._writeJson(writer);
    write(intermittentSound, writer, "intermittentSound");
  }
  
  @Override
  protected void _write(EntityWriter entityWriter) {
    super._write(entityWriter);
    Json.Writer writer = entityWriter.getWriter();
    write(intermittentSound, writer, "intermittentSound");
  }
  */
  
}
