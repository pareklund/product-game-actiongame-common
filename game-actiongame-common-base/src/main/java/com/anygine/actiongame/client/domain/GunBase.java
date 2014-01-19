package com.anygine.actiongame.client.domain;

import java.util.ArrayList;
import java.util.List;

import com.anygine.core.common.client.SoundWithPath;
import com.anygine.core.common.client.annotation.Embeddable;
import com.anygine.core.common.client.annotation.FieldRef;
import com.anygine.core.common.client.annotation.Storable;
import com.anygine.core.common.client.geometry.Vector2;

@Storable
public abstract class GunBase
  <S extends ActionGameComponentState,
  L extends ActionGameLevel<?, ?>,
  A extends ActionGameActor<?, ?>>
  extends InventoryItemBase<S, L, A> 
  implements Gun<S, L> {

  // TODO: Ensure that an Embeddable class is generated for this inner class
  @Embeddable
	public static class AmmoSupply
    <S extends ActionGameComponentState,
    L extends ActionGameLevel<?, ?>,
    A extends ActionGameActor<?, ?>>
  {
	  
		private List<Ammo<S, L, A>> ammos;
		private int selectedIndex;
		
		public AmmoSupply() {
			ammos = new ArrayList<Ammo<S, L, A>>();
		}
		
/*		
		public AmmoSupply(Object fields) {
		  super(fields);
		  ammos = jsonWritableFactory.newList(Ammo.class, fields);
		  selectedIndex = fields.getInt("selectedIndex");
    }
*/
		
    public boolean isAmmoEmpty() {
			return ammos.isEmpty() || ammos.get(selectedIndex).isEmpty();
		}
		
		public void addAmmo(Ammo<S, L, A> ammo) {
			boolean ammoPresent = false;
			for (Ammo<S, L, A> a : ammos) {
				if (a.getClass().equals(ammo.getClass())) {
					a.add(ammo.getAmount());
					ammoPresent = true; 
					break;
				}
			}
			if (!ammoPresent) {
				ammos.add(ammo);
			}
			ammos.add(ammo);
		}
		
		public List<Projectile<S, A, L>> useAmmo(
            int num, float projectileSpeed, A ownedBy) {
			Ammo<S, L, A> ammo = ammos.get(selectedIndex);
			List<Projectile<S, A, L>> projectiles =
              ammo.use(num, projectileSpeed, ownedBy);
			if (ammo.isEmpty()) {
				ammos.remove(selectedIndex);
				if (selectedIndex > ammos.size()) {
					selectedIndex--;
				}
			}
			return projectiles;
		}

/*		
    @Override
    public JsonType getJsonType() {
      return JsonType.AmmoSupply;
    }

    @Override
    protected void _writeJson(Writer writer) {
      writeList(ammos, writer, "ammos");
      writer.value("selectedIndex", selectedIndex);
    }

    @Override
    public void update(Object jsonObj) {
      ammos = updateList(Ammo.class, ammos, jsonObj, "ammos");
      selectedIndex = update(selectedIndex, jsonObj, "selectedIndex");
    }
*/
		
	}

	protected final int roundsPerShot;
	protected final float projectileSpeed;
	protected final String ammoName;

	protected final AmmoSupply<S, L, A> ammoSupply;

	protected final SoundWithPath outOfAmmoSound;
	protected final SoundWithPath firingSound;

	// TODO: Place each FieldRef only at "originating" constructor
	public GunBase(
//	    long id, int version, 
	    L level, String name, String type, Vector2 position, 
			int width, int height, int points, float bounceHeight, 
			float bounceRate, float bounceSync, int roundsPerShot, 
			float projectileSpeed, String ammoName, 
			@FieldRef(field="texture", attribute="path") String spritePath, 
			@FieldRef(field="collectedSound", attribute="path") String collectedSoundPath, 
			@FieldRef(field="firingSound", attribute="path") String firingSoundPath, 
			@FieldRef(field="outOfAmmoSound", attribute="path") String outOfAmmoSoundPath) {
		super(
//		    id, version, 
				level, name, type, position, width, height, points, 
				bounceHeight, bounceRate, bounceSync, spritePath, 
				collectedSoundPath);
		this.roundsPerShot = roundsPerShot;
		this.projectileSpeed = projectileSpeed;
		this.ammoName = ammoName;
		ammoSupply = new AmmoSupply<S, L, A>();
		firingSound = new SoundWithPath(firingSoundPath);
		outOfAmmoSound = new SoundWithPath(outOfAmmoSoundPath);
	}

	@Override
	public void use() {
		if (ammoSupply.isAmmoEmpty()) {
			outOfAmmoSound.play();
			return;
		}
		// TODO: Add projectile type
		List<Projectile<S, A, L>> projectiles = ammoSupply.useAmmo(
				roundsPerShot, projectileSpeed, (A) ownedBy);
		ownedBy.getLevel().addProjectiles(projectiles);
		firingSound.play();
	}

	@Override
	public void addAmmo(Ammo<S, L, A> ammo) {
		ammoSupply.addAmmo(ammo);
	}

	@Override
	public boolean acceptsAmmo(Ammo<S, L, A> ammo) {
		return ammoName.equals(ammo.getName());
	}
	
	@Override
	public List<InventoryListener> getListeners() {
		List<InventoryListener> listeners = 
		    new ArrayList<InventoryListener>(1);
		listeners.add(new InventoryListener() {
			
			// If GunAmmo added to inventory, add it to Gun and 
			// don't add it separately to inventory
			@Override
			public <II extends InventoryItem<?, ?, ?>> boolean onAdded(II item) {
				if (item.getType().equals("Ammo")) {
				  // TODO: Possibly make InventoryItem type parameterized (not just
				  //       method), to avoid casting
				  Ammo<S, L, A> ammo = (Ammo<S, L, A>) item;
					if (acceptsAmmo(ammo)) {
						addAmmo(ammo);
						return false;
					}
				}
				return true;
			}
		});
		return listeners;
	}

/*	
	@Override
	public JsonType getJsonType() {
	  return JsonType.GunBase;
	}

	@Override
	public void update(Json.Object jsonObj) {
	  // Final, non-immutable attributes also need to be updated
	  update(AmmoSupply.class, ammoSupply, jsonObj, "ammoSupply");
	  update(SoundWithPath.class, outOfAmmoSound, jsonObj, "outOfAmmoSound");
	  update(SoundWithPath.class, firingSound, jsonObj, "firingSound");
	}
	
  @Override
	protected void _writeJson(Json.Writer writer) {
	  super._writeJson(writer);
	  writer.value("roundsPerShot", roundsPerShot);
	  writer.value("projectileSpeed", projectileSpeed);
	  writeString(ammoName, writer, "ammoName");
	  write(ammoSupply, writer, "ammoSupply");
	  write(outOfAmmoSound, writer, "outOfAmmoSound");
	  write(firingSound, writer, "firingSound");
	}

  @Override
  protected void _write(EntityWriter entityWriter) {
    super._write(entityWriter);
    Json.Writer writer = entityWriter.getWriter();
    writer.value("roundsPerShot", roundsPerShot);
    writer.value("projectileSpeed", projectileSpeed);
    writeString(ammoName, writer, "ammoName");
    write(ammoSupply, writer, "ammoSupply");
    write(outOfAmmoSound, writer, "outOfAmmoSound");
    write(firingSound, writer, "firingSound");
  }
  */
  
}
