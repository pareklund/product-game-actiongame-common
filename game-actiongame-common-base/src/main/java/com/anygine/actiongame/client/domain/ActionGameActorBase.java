package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.SoundWithPath;
import com.anygine.core.common.client.domain.ActorBase;
import com.anygine.core.common.client.geometry.Vector2;

// Used as simulated trait, thus cannot be abstract
public class ActionGameActorBase
/* <S extends ActionGameComponentState, 
P extends ActionGamePlayer<S, P, L, GC, A, E>,
L extends ActionGameLevel<S, P, L, GC, A, E>, 
GC extends ActionGameComponent<S, P, L, GC, A, E>,
A extends ActionGameActor<S, P, L, GC, A, E>,
E extends Enemy<S, P, L, GC, A, E>> */
  <S extends ActionGameComponentState, 
  L extends ActionGameLevel<?>, 
  A extends ActionGameActor<?>>
  extends ActorBase<S> 
  implements ActionGameActor<S> {

  protected final SoundWithPath killedSound;

  protected Inventory inventory;

  protected final float moveSpeed;

  protected transient ConsumptionListener consumptionListener;

  protected Effect effect;

  protected FaceDirection faceDirection;
  
  public ActionGameActorBase(
//      long id, int version,
      String name, String type, Vector2 position, int width, int height, 
      Vector2 velocity, int points, float moveSpeed, 
      // TODO: Move to PlatformerActorBase
      // float fovHorizontal, float hearHorizontal, 
      L level, String spritePath, String killedSoundPath) {
    super(
//        id, version,
        name, type, position, width, height, velocity, points, level, 
        spritePath);
    this.moveSpeed = moveSpeed;
    killedSound = new SoundWithPath(killedSoundPath);
    inventory = new Inventory();
  }

/*  
  protected ActorBase(ActorBase other, Json.Object updateSpec) {
    super(other, updateSpec);
    this.moveSpeed = other.moveSpeed;
    this.fieldOfView = other.fieldOfView;
    this.hearingRange = other.hearingRange;
    this.score = other.score;
    this.faceDirection = other.faceDirection;
    this.inventory = other.inventory.entityHolderCopy(
        updateSpec.getObject("inventory"));
    this.killedSound = other.killedSound;
  }
*/
  
  @Override
  public FaceDirection getFaceDirection() {
    return faceDirection;
  }
  
  @Override
  public SoundWithPath getKilledSound() {
    return killedSound;
  }

  @Override
  public <A extends ActionGameActor<?>> void onKilled(A killedBy) {
    killedBy.increaseScore(points);
    killedSound.play();
  }
  
  @Override
  public Inventory getInventory() {
    return inventory;
  }

  @Override
  public float getMoveSpeed() {
    return moveSpeed;
  }
  
  @Override
  public Effect getEffect() {
    return effect;
  }

  @Override
  public void onConsume(Consumable<?, ?> consumable,
      ConsumptionListener listener) {
    consumptionListener = listener;
    effect = consumable.getEffect();
  }

  // Should really be protected but accessed through interface
  // TODO: Possibly make interface package private or protected
  @Override
  public ConsumptionListener getConsumptionListener() {
    return consumptionListener;
  }
  
  @Override
  public void resetConsumptionListener() {
    consumptionListener = null;
  }

  @Override
  public void setFaceDirection(FaceDirection faceDirection) {
    this.faceDirection = faceDirection;
  }

  @Override
  public S newState() {
    throw new UnsupportedOperationException(
        "Method only here because class used as trait and cannot be abstract");
  }

/*  
  @Override
  public void update(Json.Object jsonObj) {
    super.update(jsonObj);
    update(SoundWithPath.class, killedSound, jsonObj, "killedSound");
    score = update(score, jsonObj, "score");
    faceDirection = updateEnum(faceDirection, jsonObj, "faceDirection");
    inventory = update(Inventory.class, inventory, jsonObj, "inventory");
    // TODO: Handle serialization of ConsumptionListeners
    effect = update(Effect.class, effect, jsonObj, "effect");
  }
  
  @Override
  protected void _writeJson(Json.Writer writer) {
    super._writeJson(writer);
    writer.value("moveSpeed", moveSpeed);
    write(fieldOfView, writer, "fieldOfView");
    write(hearingRange, writer, "hearingRange");
    write(killedSound, writer, "killedSound");
    writer.value("score", score);
    writeEnum(faceDirection, writer, "faceDirection");
    write(inventory, writer, "inventory");
    // TODO: Handle writes of ConsumptionListeners
    write(effect, writer, "effect");
  }
  
  @Override
  protected void _write(EntityWriter entityWriter) {
    super._write(entityWriter);
    Json.Writer writer = entityWriter.getWriter();
    writer.value("moveSpeed", moveSpeed);
    write(fieldOfView, writer, "fieldOfView");
    write(hearingRange, writer, "hearingRange");
    write(killedSound, writer, "killedSound");
    writer.value("score", score);
    writeEnum(faceDirection, writer, "faceDirection");
    writeEntityHolder(inventory, entityWriter, "inventory");
    // TODO: Handle writes of ConsumptionListeners
    write(effect, writer, "effect");
  }
  */
  
}
