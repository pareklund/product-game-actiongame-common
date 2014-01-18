package com.anygine.actiongame.client.domain;

import java.util.ArrayList;
import java.util.List;

import com.anygine.core.common.client.annotation.FieldRef;
import com.anygine.core.common.client.annotation.Storable;
import com.anygine.core.common.client.geometry.Vector2;

@Storable
public abstract class AmmoBase
/* <S extends ActionGameComponentState,
P extends ActionGamePlayer<S, P, L, GC, A, E>,
L extends ActionGameLevel<S, P, L, GC, A, E>,
GC extends ActionGameComponent<S, P, L, GC, A, E>,
A extends ActionGameActor<S, P, L, GC, A, E>,
E extends Enemy<S, P, L, GC, A, E>> */ 
  <S extends ActionGameComponentState,
  L extends ActionGameLevel<?, ?, ?, ?, ?, ?, ?>,
  A extends ActionGameActor<?, ?, ?>>
  extends InventoryItemBase<S, L, A> 
  implements Ammo<S, L, A> {

  protected final String bulletName;

  protected int amount;

  public AmmoBase(
      //	    long id, int version, 
      L level, String name, String type, Vector2 position, int width, 
      int height, int points, float bounceHeight, float bounceRate, 
      float bounceSync, int amount, String bulletName, 
      @FieldRef(field="texture", attribute="path") String spritePath, 
      @FieldRef(field="collectedSound", attribute="path") String collectedSoundPath) {
    super(
        //		    id, version, 
        level, name, type, position, width, height, points, 
        bounceHeight, bounceRate, bounceSync, spritePath, 
        collectedSoundPath);
    this.amount = amount;
    this.bulletName = bulletName;
  }

  @Override
  public void onCollected(A collectedBy) {
    ownedBy = collectedBy;
    collectedBy.getInventory().addItem(this);
  }


  @Override
  public List<InventoryListener> getListeners() {
    List<InventoryListener> listeners = 
        new ArrayList<InventoryListener>(1);
    listeners.add(new InventoryListener() {

      // If Gun added to inventory, add this Ammo to Gun and 
      // remove itself (Ammo) from inventory
      @Override
      public <II extends InventoryItem<?, ?, ?>> 
      boolean onAdded(II item) {
        if (item.getType().equals("Gun")) {
          Gun<S, L, A> gun = (Gun<S, L, A>) item;
          if (gun.acceptsAmmo(AmmoBase.this)) {
            gun.addAmmo(AmmoBase.this);
            ownedBy.getInventory().removeNamedItem(name);
          }
        }
        return true;
      }
    });
    return listeners;
  }
  @Override
  public boolean isEmpty() {
    return amount == 0;
  }

  @Override
  public void add(int num) {
    amount += num;
  }

  @Override
  public int getAmount() {
    return amount;
  }

  @Override
  public List<Projectile<S, L, A>> use(int num, float speed) {
    List<Projectile<S, L, A>> projectiles = 
        new ArrayList<Projectile<S, L, A>>();
    for (int i = 0; i < num; i++) {
      gameComponentFactory.createResource(
          projectiles, level, ownedBy.getPosition(), bulletName, ownedBy);
    }
    return projectiles;
  }

  /*	
	@Override
	public JsonType getJsonType() {
	  return JsonType.AmmoBase;
	}

	@Override
	protected void _writeJson(Json.Writer writer) {
	  super._writeJson(writer);
	  writeString(bulletName, writer, "bulletName");
	  writer.value("amount", amount);
	}

	@Override
	protected void _write(EntityWriter entityWriter) {
	  super._write(entityWriter);
	  Json.Writer writer = entityWriter.getWriter();
    writeString(bulletName, writer, "bulletName");
    writer.value("amount", amount);
	}

	@Override
	public void update(Json.Object jsonObj) {
	  super.update(jsonObj);
	  update(amount, jsonObj, "amount");
	}
   */

}
