package com.anygine.actiongame.client.domain;

import java.util.Collection;

import com.anygine.actiongame.client.resource.AmmoInfo;
import com.anygine.actiongame.client.resource.CollectableInfo;
import com.anygine.actiongame.client.resource.ConsumableInfo;
import com.anygine.actiongame.client.resource.EnemyInfo;
import com.anygine.actiongame.client.resource.ExitInfo;
import com.anygine.actiongame.client.resource.GroundBasedEnemyInfo;
import com.anygine.actiongame.client.resource.GunInfo;
import com.anygine.actiongame.client.resource.ProjectileInfo;
import com.anygine.core.common.client.api.TimerService;
import com.anygine.core.common.client.domain.Actor;
import com.anygine.core.common.client.domain.GameComponent;
import com.anygine.core.common.client.domain.GameComponentFactory;
import com.anygine.core.common.client.domain.Level;
import com.anygine.core.common.client.geometry.Vector2;
import com.anygine.core.common.client.resource.Info;
import com.anygine.core.common.client.resource.ResourceInfoMap;
import com.google.inject.Inject;

// TODO: Clean up / Improve
public abstract class ActionGameComponentFactoryImpl extends GameComponentFactory {
  
  @Inject
  public ActionGameComponentFactoryImpl(
      ResourceInfoMap resourceInfoMap, TimerService timerService) {
    super(resourceInfoMap, timerService);
  }
  
  @Override
  public
  <GC extends GameComponent<?, ?, ?, ?>,
  L extends Level<?, ?, ?, ?>,
  A extends Actor<?, ?, ?, ?>>
  void createResource(
    Collection<GC> collection, L level, Vector2 position, Info<GC> resourceInfo, A owner) {
    if (resourceInfo.getType().equals("Exit")) {
      ExitInfo info = (ExitInfo) resourceInfo;
      collection.add((GC) new ExitBase(
//        persistenceService.getNextId(), 1, 
        (ActionGameLevel) level, info.getName(), info.getType(),
        position, info.getWidth(), info.getHeight(), info.isLocked(),
        info.getKeyRequired(), info.getDefaultSpritePath(), 
        info.getExitingSoundPath(), info.getLockedSoundPath(),
        info.getUnlockedSoundPath()));
    } 
    /* Abstract - not instantiable
    else if (resourceInfo.getType().equals("Projectile")) {
      ProjectileInfo info = (ProjectileInfo) resourceInfo;
      collection.add((GC) new ProjectileBase(
//        persistenceService.getNextId(), 1, 
        (ActionGameLevel) level, info.getName(), info.getType(), 
        position, info.getWidth(), info.getHeight(), 
        (ActionGameActor) owner, info.getSpeed(), 
        info.getDamage(), info.getDefaultSpritePath()));
    } 
    */
    else if (resourceInfo.getType().equals("AirBasedEnemy")) {
      EnemyInfo info = (EnemyInfo) resourceInfo;
      collection.add((GC) new AirBasedEnemyBase(
//        persistenceService.getNextId(), 1, 
        info.getName(), info.getType(), (ActionGameLevel) level, 
        position, info.getWidth(), info.getHeight(),
        info.getPoints(), info.getMoveSpeed(), 
//        info.getFovHorizontal(), info.getHearHorizontal(),
        info.getDefaultSpritePath(), info.getKilledSoundPath(),
        info.getIntermittentSoundPath()));      
    } else if (resourceInfo.getType().equals("GroundBasedEnemyBase")) {
      GroundBasedEnemyInfo info = (GroundBasedEnemyInfo) resourceInfo;
      collection.add((GC) new GroundBasedEnemyBase(
//          persistenceService.getNextId(), 1, 
          info.getName(), info.getType(), (ActionGameLevel) level,
          position, info.getWidth(), info.getHeight(),
          info.getPoints(), info.getMoveSpeed(), 
          info.getMaxWaitTime(), info.getChargeSpeed(),
          info.getDefaultSpritePath(), 
          info.getKilledSoundPath(),
          info.getIntermittentSoundPath(),
          info.getRunAnimationPath()));
    } 
    /* Abstract - not instantiable
    else if (resourceInfo.getType().equals("ValuableBase")) {
      CollectableInfo info = (CollectableInfo) resourceInfo;
      collection.add((GC) new ValuableBase(
//        persistenceService.getNextId(), 1, 
        (ActionGameLevel) level, info.getName(), info.getType(), 
        position, info.getWidth(), 
        info.getHeight(), info.getPoints(), info.getBounceHeight(), 
        info.getBounceRate(), info.getBounceSync(), 
        info.getDefaultSpritePath(), info.getCollectedSoundPath()));
    } */
    else if (resourceInfo.getType().equals("GunBase")) {
      GunInfo info = (GunInfo) resourceInfo;
      collection.add((GC) new GunBase(
//          persistenceService.getNextId(), 1, 
          (ActionGameLevel) level, info.getName(), info.getType(), 
          position, info.getWidth(), info.getHeight(), info.getPoints(), 
          info.getBounceHeight(), info.getBounceRate(), 
          info.getBounceSync(), info.getRoundsPerShot(), 
          info.getProjectileSpeed(), info.getAmmoName(), 
          info.getDefaultSpritePath(), 
          info.getCollectedSoundPath(), 
          info.getFiringSoundPath(),
          info.getOutOfAmmoSoundPath()));
    } else if (resourceInfo.getType().equals("AmmoBase")) {
      AmmoInfo info = (AmmoInfo) resourceInfo;
      collection.add((GC) new AmmoBase(
//        persistenceService.getNextId(), 1, 
        (ActionGameLevel) level, info.getName(), info.getType(), 
        position, info.getWidth(), info.getHeight(), 
        info.getPoints(), 
        info.getBounceHeight(), info.getBounceRate(), 
        info.getBounceSync(), info.getInitialAmount(),
        info.getBulletName(), info.getDefaultSpritePath(), 
        info.getCollectedSoundPath()));      
    } else if (resourceInfo.getType().equals("ConsumableBase")) {
      ConsumableInfo info = (ConsumableInfo) resourceInfo;
      collection.add((GC) new ConsumableBase(
//        persistenceService.getNextId(), 1, 
        (ActionGameLevel) level, info.getName(), info.getType(), 
        position, info.getWidth(), info.getHeight(), info.getPoints(), 
        info.getBounceHeight(), 
        info.getBounceRate(), 
        info.getBounceSync(), info.getEffect(),
        info.isLoopingSound(),
        info.getDefaultSpritePath(), 
        info.getCollectedSoundPath(),
        info.getConsumingSoundPath()));
    } 
    // Abstract - cannot be instantiated 
/*
    else if (resourceInfo.getType().equals("InventoryItemBase")) {
      CollectableInfo info = (CollectableInfo) resourceInfo;
      collection.add((GC) new InventoryItemBase(
        (ActionGameLevel) level, info.getName(), info.getType(), 
        position, info.getWidth(), info.getHeight(), info.getPoints(), 
        info.getBounceHeight(), info.getBounceRate(), 
        info.getBounceSync(), info.getDefaultSpritePath(), 
        info.getCollectedSoundPath()));
    }
    */
    throw new UnsupportedOperationException("NYI");
  }
  
/*  
  public Collectable createCollectable(
      Level level, int x, int y, CollectableInfo info) {
    return createCollectable(
        level,
        getPosition(
            x, y, info.getWidth(),info.getHeight()), info);
  }

  public Collectable createCollectable(
      Level level, Vector2 position, String name) {
    return createCollectable(
        level, position, 
        (CollectableInfo) resourceInfoMap.getByName(name));
  }  
  
  public abstract Collectable createCollectable(
      Level level, Vector2 position, CollectableInfo info);
*/
}
