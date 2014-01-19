package com.anygine.actiongame.client.domain;

import static playn.core.PlayN.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import playn.core.Json;

import com.anygine.core.common.client.SoundWithPath;
import com.anygine.core.common.client.annotation.Storable;
import com.anygine.core.common.client.domain.LevelBase;
import com.anygine.core.common.client.domain.Resource;
import com.anygine.core.common.client.domain.Tile;
import com.anygine.core.common.client.domain.Tile.TileCollision;
import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.core.common.client.input.Input;
import com.anygine.core.common.client.resource.Info;
import com.anygine.core.common.client.resource.ResourceInfoMap;
import com.anygine.core.common.client.resource.TileInfo;

// TODO: Separate out sound handling to renderer class or correspondingly
@Storable
public abstract class ActionGameLevelBase
  <P extends ActionGamePlayer<?, ?>,
  GC extends ActionGameComponent<?, ?>>
  extends LevelBase<GC, P>
  implements ActionGameLevel<GC, P> {

  // These fields are "write once" or effectively immutable
  // Not final due to being set from level load callback
  protected int timeForLevel;
  protected int pointsPerSecond;
  Vector2 start;
  List<X> exits;
  SoundWithPath exitReachedSound;
  int numValuablesRequired;

  protected int timeRemaining;
  protected boolean exitReached; 

  protected List<E> enemies;
  List<CL> collectables;
  List<PR> projectiles;

  public ActionGameLevelBase(
      String levelJson, int levelIndex, P player) {

    super(levelJson, levelIndex, player);

    enemies = new ArrayList<E>();
    collectables = new ArrayList<CL>();
    projectiles = new ArrayList<PR>();
    exits = new ArrayList<X>();

    loadLevel(levelJson);

    // Load sounds.
    exitReachedSound = new SoundWithPath("Sounds/ExitReached");

    entering = true;
    player.onLevelEntered(this);
  }

  /*
  public LevelBase(Object fields) {
    super(fields);

    GameplayCommonInjector injector = 
      GameplayCommonInjectorManager.getInjector();
    gameComponentFactory = injector.getGameComponentFactory();
    resourceInfoMap = injector.getResourceInfoMap();

    player = entityService.getInstance(Player.class, fields.getObject("player"));
    enemies = jsonWritableFactory.newEntityList(
        Enemy.class, fields.getObject("enemies"));
    tiles = jsonWritableFactory.newArrayOfArrays(Tile.class, fields.getObject("tiles"));
  }
  
  public LevelBase(LevelBase other, Object updateSpec) {
    super(other, updateSpec);
    
    GameplayCommonInjector injector = 
      GameplayCommonInjectorManager.getInjector();
    gameComponentFactory = injector.getGameComponentFactory();
    resourceInfoMap = injector.getResourceInfoMap();

    this.player = copyEntityField(other.player, updateSpec.getObject("player"));
    this.enemies = copyEntityListField(other.enemies, updateSpec.getArray("enemies"));
    this.collectables = copyEntityListField(
        other.collectables, updateSpec.getArray("collectables"));
    this.searchables = copyEntityListField(
        other.searchables, updateSpec.getArray("searchables"));
    this.projectiles = copyEntityListField(
        other.projectiles, updateSpec.getArray("projectiles"));
    this.exits = copyEntityListField(other.exits, updateSpec.getArray("exits"));
    this.timeForLevel = other.timeForLevel;
    this.pointsPerSecond = other.pointsPerSecond;
    this.width = other.width;
    this.height = other.height;
    this.start = other.start;
    this.exitReachedSound = other.exitReachedSound;
    this.numValuablesRequired = other.numValuablesRequired;
    this.name = other.name;
    this.score = other.score;
    this.timeRemaining = other.timeRemaining;
    this.exitReached = other.exitReached;
    this.cameraPosition = other.cameraPosition;
    this.tiles = copyEntityArrayArrayField(other.tiles, updateSpec.getArray("tiles"));
    this.entering = other.entering;
  }
  */

  public void setWidth(int width) {
    this.width = width;
  }

  @Override
  public P getPlayer() {
    return player;
  }

  @Override
  public List<E> getEnemies() {
    return enemies;
  }

  @Override
  public List<CL> getCollectables() {
    return collectables;
  }

  @Override
  public List<X> getExits() {
    return exits;
  }

  public Tile[][] getTiles() {
    return tiles;
  }

  @Override
  public int getScore() {
    return score;
  }

  @Override
  public float getTimeRemaining() {
    return timeRemaining;
  }

  @Override
  public TileCollision getCollision(int x, int y) {
    // Prevent escaping past the level ends.
    if (x < 0 || x >= width) {
      return TileCollision.Impassable;
    }
    // Allow jumping past the level top and falling through the bottom.
    if (y < 0 || y >= height) {
      return TileCollision.Passable;
    }
    return tiles[x][y].getCollision();
  }

  @Override
  public void update(float gameTime, Input input) {
    super.update(gameTime, input);
    // Pause while the player is dead or time is expired.
    if (!player.getState().isAlive() || getTimeRemaining() == 0
        || entering) {
      // Still want to perform physics on the player.
      player.applyPhysics(gameTime);
    } else if (exitReached) {
      // Animate the time being converted into points.
      timeRemaining -= 60;
      player.increaseScore(pointsPerSecond);
    } else {
      timeRemaining -= gameTime;
      player.update(input, gameTime);

      // Falling off the bottom of the level kills the player.
      if (player.getBoundingRectangle(false).Center().Y >= getHeight() * Tile.Height) {
        onPlayerKilled(null);
      }

      updateCollectables(timeRemaining);
      updateEnemies(gameTime);
      updateProjectiles(gameTime);
      updateExits(gameTime);
    }

    // Clamp the time remaining at zero.
    if (timeRemaining < 0)
      timeRemaining = 0;
  }

  @Override
  public void startNewLife() {
    player.reset(start);
  }

  @Override
  public boolean isExitReached() {
    return exitReached;
  }

  protected boolean isImpassableAtPosition(Vector2 position) {
    Tile tile = getTileAtPosition(position);
    if (tile == null) {
      return false;
    }
    return tile.getCollision() == Tile.TileCollision.Impassable;
  }

  protected Tile getTileAtPosition(Vector2 position) {
    int tileX = (int) position.X / Tile.Width;
    int tileY = (int) position.Y / Tile.Height - 1;
    // Never a ladder outside level bounds
    if (!isOnVisibleTile(tileX, tileY)) {
      return null;
    }
    return tiles[tileX][tileY];
  }

  @Override
  public int numValuablesRequired() {
    return numValuablesRequired;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean isEntering() {
    return entering;
  }

  @Override
  public void setEntering(boolean entering) {
    this.entering = entering;
  }

  @Override
  public void addProjectiles(List<PR> newProjectiles) {
    projectiles.addAll(newProjectiles);
  }

  @Override
  public List<PR> getProjectiles() {
    return projectiles;
  }

  @Override
  public void addTime(P player, int time) {
    // TODO: Have separate times per player
    timeRemaining += (time * 60);
  }

  /*
  @Override
  public JsonType getJsonType() {
    return JsonType.LevelBase;
  }
*/
/*  
  @Override
  protected void _writeJson(Writer writer) {
    super._writeJson(writer);
    write(player, writer, "player");
    writer.value("score", score);
    writer.value("timeForLevel", timeForLevel);
    writer.value("timeRemaining", timeRemaining);
    writer.value("pointsPerSecond", pointsPerSecond);
    writer.value("width", width);
    writer.value("height", height);
    writer.value("exitReached", exitReached);
    writeList(enemies, writer, "enemies");
    writeList(collectables, writer, "collectables");
    writeList(searchables, writer, "searchables");
    writeList(projectiles, writer, "projectiles");
    write(start, writer, "start");
    writeList(exits, writer, "exits");
    write(exitReachedSound, writer, "exitReachedSound");
    write(cameraPosition, writer, "cameraPosition");
    writeArrayOfArrays(tiles, writer, "tiles");
    writer.value("numValuablesRequired", numValuablesRequired);
    writeString(name, writer, "name");
    writer.value("entering", entering);
  }
  
  @Override
  protected void _write(EntityWriter entityWriter) {
    super._write(entityWriter);
    Json.Writer writer = entityWriter.getWriter();
    writeEntity(player, entityWriter, "player");
    writer.value("score", score);
    writer.value("timeForLevel", timeForLevel);
    writer.value("timeRemaining", timeRemaining);
    writer.value("pointsPerSecond", pointsPerSecond);
    writer.value("width", width);
    writer.value("height", height);
    writer.value("exitReached", exitReached);
    writeEntityList(enemies, entityWriter, "enemies");
    writeEntityList(collectables, entityWriter, "collectables");
    writeEntityList(searchables, entityWriter, "searchables");
    writeEntityList(projectiles, entityWriter, "projectiles");
    write(start, writer, "start");
    writeEntityList(exits, entityWriter, "exits");
    write(exitReachedSound, writer, "exitReachedSound");
    write(cameraPosition, writer, "cameraPosition");
    writeEntityArrayOfArrays(tiles, entityWriter, "tiles");
    writer.value("numValuablesRequired", numValuablesRequired);
    writeString(name, writer, "name");
    writer.value("entering", entering);
  }
  
  @Override
  public void update(Json.Object jsonObj) {
    super.update(jsonObj);
    player = updateEntity(Player.class, player, jsonObj, "player");
    score = update(score, jsonObj, "score");
    timeRemaining = update(timeRemaining, jsonObj, "timeRemaining");
    exitReached = update(exitReached, jsonObj, "exitReached");
    cameraPosition = update(
        Vector2.class, cameraPosition, jsonObj, "cameraPosition");
    tiles = updateArrayOfArrays(Tile.class, tiles, jsonObj, "tiles");
    entering = update(entering, jsonObj, "entering");
    enemies = updateList(Enemy.class, enemies, jsonObj, "enemies");
    collectables = updateList(
        Collectable.class, collectables, jsonObj, "collectables");
    searchables = updateList(
        Searchable.class, searchables, jsonObj, "searchables");
    projectiles = updateList(
        ProjectileBase.class, projectiles, jsonObj, "projectiles");
  }

*/
  
  protected void updateCollectables(float gameTime) {
    List<CL> collected = new ArrayList<CL>();
    for (CL collectable : collectables) {
      collectable.update(Input.NoInput, gameTime);
      if (collectable.getBoundingRectangle(false).Intersects(player.getBoundingRectangle(false))) {
        collected.add(collectable);
        onCollectableCollected(collectable, player);
      }
    }
    collectables.removeAll(collected);
  }

  protected void updateEnemies(float gameTime) {
    List<E> killed = new ArrayList<E>();
    for (E enemy : enemies) {
      // TODO: Check for enemies being killed and add to killed list
      enemy.update(Input.NoInput, gameTime);
      if (enemy.getBoundingRectangle(false).Intersects(
          player.getBoundingRectangle(false))
          && !player.isInvulnerable()) {
        // TODO: Avoid casting
        onPlayerKilled(enemy);
      }
    }
    enemies.removeAll(killed);
  }

  protected void updateProjectiles(float gameTime) {
    List<E> killed = new ArrayList<E>();
    List<PR> hits = new ArrayList<PR>();
    for (PR projectile : projectiles) {
      // TODO: Check for enemies being killed and add to killed list
      projectile.update(Input.NoInput, gameTime);
      if (projectile.getBoundingRectangle(false).Intersects(
          player.getBoundingRectangle(false))
          && !player.isInvulnerable()) {
        onPlayerKilled(projectile.getOwner());
        hits.add(projectile);
      } else { // Only check for enemy hits if a player has not been hit by projectile
        for (E enemy : enemies) {
          if (projectile.getBoundingRectangle(false).Intersects(enemy.getBoundingRectangle(false))) {
            enemy.onKilled(projectile.getOwner());
            killed.add(enemy);
            hits.add(projectile);
            // Only kill one enemy with projectile
            // TODO: Choose enemy closest to projectile
            break;
          }
        }
      }
    }
    enemies.removeAll(killed);
    projectiles.removeAll(hits);
  }

  private void updateExits(float gameTime) {
    if (player.getState().isOpening()) {
      for (X exit : exits) {
        exit.update(Input.NoInput, gameTime);
        if (player.getState().isAlive() && isExitReachedCriteria(player)
            && player.getBoundingRectangle(false).Contains(exit.getPosition())) {
          if (player.getNumCollectedInLevel() >= numValuablesRequired 
              && exit.enter(player)) {
            onExitReached();
            break;
          }
        }
      }
    }
  }

  // Override for game type specific "exit reached" criteria
  protected abstract boolean isExitReachedCriteria(P player);
  
  // Override to perform other stuff when exit is reached
  protected void onExitReached() {
    exitReached = true;
  }

  // TODO: Potentially put these methods in a LevelListener class

  // Override to perform other stuff when player is killed
  protected <A2 extends ActionGameActor<?, ?, ?>> void onPlayerKilled(A2 killedBy) {
    player.onKilled(killedBy);
    // Logic for decreasing lives could be done in onKilled but that
    // would be assymetric to awarding a player an extra life (addLife())
    player.decreaseLife();
    timeRemaining = timeForLevel;
  }

  // Override to perform other stuff when collectable is collected
  protected void onCollectableCollected(CL collectable, P actor) {
    collectable.onCollected(actor);
    player.increaseScore(collectable.getPoints());
  }
/*
  protected 
    <CL extends Collectable<?, ?, ?, ?, A, ?>, 
    A extends ActionGameActor<?, ?, ?, ?, ?, ?>>
    void onCollectableCollected(CL collectable, A actor) {
    collectable.onCollected(actor);
    player.increaseScore(collectable.getPoints());
  }
*/
  // Iterates over every tile in the structure file and loads its
  // appearance and behavior. This method also validates that the
  // file is well-formed with a player start point, exit, etc.
  // <param name="fileStream">
  // A stream containing the tile data.
  // </param>

  private void loadLevel(String levelJson) {
    Json.Object jso = json().parse(levelJson);
    createTiles(jso.getArray("lines"));
    name = jso.getString("name");
    numValuablesRequired = jso.getInt("valuablesRequired");
    timeForLevel = jso.getInt("time") * 60;
    timeRemaining = timeForLevel;
    pointsPerSecond = jso.getInt("pointsPerSec");

  }

  private void createTiles(Json.Array lines) {
    int width = lines.getString(0).length();
    this.width = width;
    // Allocate the tile grid.
    tiles = new Tile[width][lines.length()];
    height = tiles[0].length;		

    // Loop over every tile position,
    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < width; ++x) {
        // to load each tile.
        char tileType = lines.getString(y).charAt(x);
        tiles[x][y] = createTile(tileType, x, y);
      }
    }

    // Verify that the level has a beginning and an end.
    if (player.getLevel() == null) {
      throw new RuntimeException("A level must have a starting point.");
    }
    if (exits.isEmpty()) {
      throw new RuntimeException("A level must have at least one exit.");
    }
  }

  @Override
  protected void validateTiles() {
    if (exits.isEmpty()) {
      throw new RuntimeException("A level must have at least one exit.");
    }
  }
  
  // TODO: Make Type extensible
  @Override
  protected Tile createTile(char tileId, int x, int y) {
    Info<? extends Resource> info = resourceInfoMap.getById(tileId);
    String type = info.getType();
    if (type.equals("Exit")) {
      return createExitTile(x, y, (Info<X>) info);
    } else if (Arrays.asList(
        "Exit", "Collectable", "Gun", "Ammo", "Consumable", "Valuable").contains(type)) {
      return createCollectableTile(x, y, (Info<CL>) info);
    } else {
      return super.createTile(tileId, x, y);
    }
  }

  protected Tile createExitTile(int x, int y, Info<X> info) {
    gameComponentFactory.createResource(
        exits, this, new Vector2(x, y), info, null);
    return gameComponentFactory.createTile(
        this, x, y, (TileInfo) resourceInfoMap.getByName(
            ResourceInfoMap.EMPTY_TILE_ID));
  }

  protected Tile createCollectableTile(int x, int y, Info<CL> info) {
    gameComponentFactory.createResource(
        collectables, this, new Vector2(x, y), info, null);
    return gameComponentFactory.createTile(
        this, x, y, (TileInfo) resourceInfoMap.getByName(
            ResourceInfoMap.EMPTY_TILE_ID));
  }

  protected Tile createEnemyTile(int x, int y, Info<E> info) {
    gameComponentFactory.createResource(
        enemies, this, new Vector2(x, y), info, null);
    return gameComponentFactory.createTile(
        this, x, y, (TileInfo) resourceInfoMap.getByName(
            ResourceInfoMap.EMPTY_TILE_ID));
  }

  private boolean isOnVisibleTile(int x, int y) {
    return x >= 0 && x < tiles.length && y >= 0 && y < tiles[0].length;
  }

/*  
  @Override
  public <T extends Comparable<? extends T>> int compareTo(T attribute, T value) {
    // TODO: Implement
    throw new UnsupportedOperationException("Not yet implemented");
  }
*/
/*  
  @Override
  public Level entityCopy(Object updateSpec) {
    return new LevelBase(this, updateSpec);
  }
*/
}
