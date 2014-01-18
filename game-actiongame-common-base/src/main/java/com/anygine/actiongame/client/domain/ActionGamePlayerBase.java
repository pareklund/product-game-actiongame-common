package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.Animation;
import com.anygine.core.common.client.Profile;
import com.anygine.core.common.client.SoundWithPath;
import com.anygine.core.common.client.TimerCallback;
import com.anygine.core.common.client.annotation.FieldRef;
import com.anygine.core.common.client.annotation.Storable;
import com.anygine.core.common.client.domain.Level;
import com.anygine.core.common.client.domain.PlayerBase;
import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.core.common.client.input.Input;

@Storable
public abstract class ActionGamePlayerBase
/* <S extends ActionGameComponentState, 
P extends ActionGamePlayer<S, P, L, GC, A, E>,
L extends ActionGameLevel<S, P, L, GC, A, E>,
GC extends ActionGameComponent<S, P, L, GC, A, E>,
A extends ActionGameActor<S, P, L, GC, A, E>,
E extends Enemy<S, P, L, GC, A, E>> */
  <S extends ActionGameComponentState, 
  L extends ActionGameLevel<?>,
  A extends ActionGameActor<?>>
  extends PlayerBase<S> 
  implements ActionGamePlayer<S, A>, ActionGameActor<S> {

  // TODO: Generate as trait
  private final ActionGameActorBase<S, L, A> actionGameActorTrait;
  
  // Constants for controlling horizontal movement
  private static final float MoveAccelerationX = 13000.0f;
  protected static final float MoveAccelerationY = 8000.0f;
  // Doesn't have effect now (speed maxes out at roughly 200)
  //	private static final float MaxMoveSpeed = 1750.0f;

  private final int startLives;

  final Animation dieAnimation;

  // Current user movement input.
  protected Vector2 movement;

  int lives;

  int numCollectedInLevel;

  protected float currentMoveAccelerationX;

  boolean invulnerable;

  public ActionGamePlayerBase(
//      long id, int version, 
      String name, String type, 
      Vector2 position, int width, int height, Vector2 velocity, 
      int points, float moveSpeed, 
      @FieldRef(field="fieldOfView", attribute="X") float fovHorizontal, 
      @FieldRef(field="hearingRange", attribute="X") float hearHorizontal, 
      L level, int lives, Profile profile,
      @FieldRef(field="texture", attribute="path") String spritePath, 
      @FieldRef(field="killedSound", attribute="path") String killedSoundPath,
      @FieldRef(field="dieAnimation", attribute="path") String dieAnimationPath) {
    // TODO: Possibly don't inherit fov attributes to player
    // TODO: Don't inherit moveSpeed
    // TODO: Use unique player names (from a player DB) to facilitate
    //       multiplayer behaviour
    // TODO: Do this construction in a more generic way which allows for
    //       multiplay scenarios
    super(
//        id, version, 
        name, type, position, width, height, velocity, points, 
        level, profile, spritePath);
    
    this.actionGameActorTrait = new ActionGameActorBase<S, L, A>(
        name, type, position, width, height, velocity, points, 
        moveSpeed, level, spritePath, killedSoundPath);

    this.lives = lives;
    startLives = lives;
    reset(position);
    currentMoveAccelerationX = MoveAccelerationX;
    invulnerable = false;

    dieAnimation = new Animation(dieAnimationPath, width, height, 0.1f, false);
  }
  
  @Override
  public void init() {
    lives = startLives;
    velocity = new Vector2();
    score = 0;
    resetSomeState();
    animationPlayer.playAnimation(defaultAnimation);
  }

  // Handles input, performs physics, and animates the player sprite.
  // <remarks>
  // We pass in all of the input states so that our game is only polling the
  // hardware
  // once per frame. We also pass the game's orientation because when using
  // the accelerometer,
  // we need to reverse our motion when the orientation is in the
  // LandscapeRight orientation.
  // </remarks>
  //  public void Update(float gameTime, KeyboardState keyboardState, GamePadState gamePadState,
  //	      TouchCollection touchState, AccelerometerState accelState, DisplayOrientation orientation) {
  @Override
  public boolean update(Input input, float gameTime) {
    // TODO: Handle returned boolean (or remove it from signature)
    super.update(input, gameTime);
    resetSomeState();
    getInput(input, gameTime);
    applyPhysics(gameTime);
    // TODO: Check whether returning a boolean is necessary
    return true;
  }

  // "Trait method"
  @Override
  public Inventory getInventory() {
    return actionGameActorTrait.getInventory();
  }
  
  @Override
  public int getLives() {
    return lives;
  }

  @Override
  public void decreaseLife() {
    lives--;
  }

  @Override
  public void addLife() {
    lives++;  
  }

  @Override
  public void reset(Vector2 position) {
    super.reset(position);
    this.movement = Vector2.Zero;
  }

  @Override
  public int getNumCollectedInLevel() {
    return numCollectedInLevel;
  }

  @Override
  public void increaseNumCollectedInLevel() {
    numCollectedInLevel++;
  }

  
  protected void resetSomeState() {
    // TODO: super.resetSomeState()
    state.setAlive(true);
    movement = new Vector2();
  }

  @Override
  public <A2 extends ActionGameActor<?>> void onKilled(A2 killedBy) {
    state.setAlive(false);
    if (killedBy != null) {
      actionGameActorTrait.getKilledSound().play();
    }
    animationPlayer.playAnimation(dieAnimation);
    movement = new Vector2();
  }

  
  public float getCurrentMoveAccelerationX() {
    return currentMoveAccelerationX;
  }

  @Override
  public void onConsume(
      Consumable<?, ?> consumable, ConsumptionListener listener) {
    // TODO: 
    // 1) Handle all effect types
    // 2) Limit effect to duration
    actionGameActorTrait.onConsume(consumable, listener);
  }

  @Override
  public boolean isInvulnerable() {
    return invulnerable;
  }

/*  
  @Override
  public JsonType getJsonType() {
    return JsonType.Player;
  }

  @Override
  protected void _writeJson(Json.Writer writer) {
    super._writeJson(writer);
    // Once
    writer.value("startLives", startLives);
    // writeEntity should be used as per method _write() below 
    writer.value("profile", profile.getId());
    writer.value("movement", movement.toString());
    writer.value("wasJumping", wasJumping);
    writer.value("wasUsing", wasUsing);
    writer.value("lives", lives);
    writer.value("numCollectedInLevel", numCollectedInLevel);
    writer.value("currentMoveAccelerationX", currentMoveAccelerationX);
    writer.value("jumpLaunchVelocity", jumpLaunchVelocity);
    writer.value("invulnerable", invulnerable);
  }

  @Override
  protected void _write(EntityWriter entityWriter) {
    super._write(entityWriter);
    Json.Writer writer = entityWriter.getWriter();
    // Once
    writer.value("startLives", startLives);
    // Consider using "xId" or "xRef" for entity references 
    writeEntity(profile, entityWriter, "profile");
    write(movement, writer, "movement");
    writer.value("wasJumping", wasJumping);
    writer.value("wasUsing", wasUsing);
    writer.value("lives", lives);
    writer.value("numCollectedInLevel", numCollectedInLevel);
    writer.value("currentMoveAccelerationX", currentMoveAccelerationX);
    writer.value("jumpLaunchVelocity", jumpLaunchVelocity);
    writer.value("invulnerable", invulnerable);  
  }
*/
  protected void applyEffect() {
    final Effect effect = actionGameActorTrait.getEffect();
    switch (effect.getType()) {
      case SPEED:
        final float normalMoveAccelerationX = MoveAccelerationX;
        currentMoveAccelerationX *= effect.getAmount();
        timerService.registerCallback(
            effect.getDuration(),
            effect.getWarnAt(),
            new TimerCallback() {
              @Override
              public void onTimer() {
                currentMoveAccelerationX = normalMoveAccelerationX;
//                effect = null;
              }
            });
        break;
      case INVULNERABILITY:
        invulnerable = true;
        timerService.registerCallback(
            effect.getDuration(),
            effect.getWarnAt(),
            new TimerCallback() {
              @Override
              public void onTimer() {
                invulnerable = false;
              }
            });
        break;
      case TIME:
        // TODO: Avoid casting
        level.addTime(this, (int) effect.getAmount());
        break;
      case LIFE:
        lives++;
        break;
        // TODO: 
        //	* Invisibility
        //	* ...
    }
  }
  
/*
  @Override
  public <T extends Comparable<? extends T>> int compareTo(T attribute, T value) {
    // TODO: Implement
    throw new UnsupportedOperationException("Not yet implemented");
  }


  @Override
  public PlatformerPlayer entityCopy(Object updateSpec) {
    return new PlatformerPlayer(this, updateSpec);
  }
*/

  @Override
  public <L extends Level<?>> void placeInLevel(L level, Vector2 position) {
    // TODO: Implement
    super.placeInLevel(level, position);
  }
  
  @Override
  public <L extends Level<?>> void onLevelEntered(L level) {
    numCollectedInLevel = 0;
  }

  @Override
  public FaceDirection getFaceDirection() {
    return actionGameActorTrait.getFaceDirection();
  }

  @Override
  public SoundWithPath getKilledSound() {
    return actionGameActorTrait.getKilledSound();
  }

  @Override
  public float getMoveSpeed() {
    return actionGameActorTrait.getMoveSpeed();
  }

  @Override
  public Effect getEffect() {
    return actionGameActorTrait.getEffect();
  }

  @Override
  public void onReachedExit() {
  }
}
