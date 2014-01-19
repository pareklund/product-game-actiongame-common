package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.annotation.FieldRef;
import com.anygine.core.common.client.annotation.Storable;
import com.anygine.core.common.client.geometry.Rectangle;
import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.core.common.client.input.Input;

// TODO: Possibly not have this as a standalone Entity/GameComponent
@Storable
public abstract class ProjectileBase
  <S extends ActionGameComponentState,
  L extends ActionGameLevel<?, ?>,
  A extends ActionGameActor<?, ?>>
  extends ActionGameComponentBase<S, L> 
  implements Projectile<S, A, L> {

	protected final int damage;
	protected final A owner;
	
	public ProjectileBase(
//	    long id, int version, 
			L level, String name, String type, 
			Vector2 position, int width, int height, A owner, 
			// TODO: Fix (relative to owner's face direction)
			@FieldRef(field="velocity", attribute="X") float speed, 
			int damage, 
			@FieldRef(field="texture", attribute="path") String spritePath) {
		super(
//		    id, version, 
				name, type, position, width, height, getVelocity(speed, owner), 
				0, level, spritePath);
		this.damage = damage;
		this.owner = owner;
		setStartPosition();
	}

/*	
	public ProjectileBase(ProjectileBase other, Object updateSpec) {
	  super(other, updateSpec);
	  this.damage = other.damage;
	  this.owner = copyEntityField(other.owner, updateSpec.getObject("owner"));
	}
*/
	
  // TODO: Implement with collision detection 
	@Override
	public boolean update(Input input, float delta) {
		Vector2 amt = velocity.mul(delta);
		position = getPosition().add(amt);
		position = new Vector2(
		    (float) Math.round(getPosition().X), 
		    (float) Math.round(getPosition().Y));
		return false;
	}

	@Override
	public A getOwner() {
		return owner;
	}
	
	@Override
	public int getDamage() {
		return damage;
	}

	
	private void setStartPosition() {
		Rectangle ownerBounds = owner.getBoundingRectangle(false);
		if (owner.getFaceDirection() == FaceDirection.Right) {
			position = new Vector2(ownerBounds.Right() + 5 * width, position.Y - ownerBounds.height / 1.5f);
		} else {
			position = new Vector2(ownerBounds.left - 5 * width, position.Y - ownerBounds.height / 1.5f);
		}
	}

	private static <A extends ActionGameActor<?, ?>>
  Vector2 getVelocity(float speed, A owner) {
		Vector2 velocity;
		if (owner.getFaceDirection() == FaceDirection.Left) {
			velocity = new Vector2(-speed, 0.0f);
		} else {
			velocity = new Vector2(speed, 0.0f);
		}
		return velocity;
	}

/*	
  @Override
  public JsonType getJsonType() {
    return JsonType.ProjectileBase;
  }

  @Override
  public void update(Json.Object jsonObj) {
    super.update(jsonObj);
    // TODO: Replace with ActorService to allow for other owners than Players
    updateEntity(Actor.class, owner, jsonObj, "owner");
  }
  
  @Override
  protected void _writeJson(Writer writer) {
    super._writeJson(writer);
    writer.value("damage", damage);
    write(owner, writer, "owner");
  }

  @Override
  protected void _write(EntityWriter entityWriter) {
    super._write(entityWriter);
    Json.Writer writer = entityWriter.getWriter();
    writer.value("damage", damage);
    writeEntity(owner, entityWriter, "owner");
  }
  
  @Override
  public <T extends Comparable<? extends T>> int compareTo(T attribute, T value) {
    // TODO: Implement
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public ProjectileBase entityCopy(Object updateSpec) {
    return new ProjectileBase(this, updateSpec);
  }
  */
  
}
