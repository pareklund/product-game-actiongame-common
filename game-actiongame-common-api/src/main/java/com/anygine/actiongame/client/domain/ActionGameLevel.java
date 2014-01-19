package com.anygine.actiongame.client.domain;

import java.util.List;

import com.anygine.core.common.client.domain.Level;
import com.anygine.core.common.client.domain.Tile;
import com.anygine.core.common.client.domain.Tile.TileCollision;

public interface ActionGameLevel
  <GC extends ActionGameComponent<?, ?>,
  P extends ActionGamePlayer<?, ? super ActionGameLevel<?, ?>>>
  extends Level<GC, P> {
  
  <E extends Enemy<?, ?>> List<E> getEnemies();
  
  <CL extends Collectable<?, ?, ?>> List<CL> getCollectables();
  
  <X extends Exit<?, ?, ?>> List<X> getExits();

  // TODO: Generify
  Tile[][] getTiles();

  boolean isExitReached();

  float getTimeRemaining();

  // Gets the collision mode of the tile at a particular location.
  // This method handles tiles outside of the levels boundries by making it
  // impossible to escape past the left or right edges, but allowing things
  // to jump beyond the top of the level and fall off the bottom.
  TileCollision getCollision(int x, int y);

  // Restores the player to the starting point to try the level again.
  void startNewLife();

  void handleCollisions(GC player);

  int numValuablesRequired();
  
  // TODO: Generify Projectile type
  <PR extends Projectile<?, ?, ?>> void addProjectiles(List<PR> projectiles);
  
  <PR extends Projectile<?, ?, ?>> List<PR> getProjectiles();

  <P extends ActionGamePlayer<?, ?>> void addTime(P player, int time);
}
