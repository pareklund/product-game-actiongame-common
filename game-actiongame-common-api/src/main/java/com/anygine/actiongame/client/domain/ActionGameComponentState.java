package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.domain.GameComponentState;

public class ActionGameComponentState extends GameComponentState {
  
  private boolean invulnerable;
  private boolean dying;
  private boolean firing;
  private boolean alive;
  private boolean opening;
  
  public ActionGameComponentState() {
    super();
    invulnerable = false;   // Potentially true to avoid spawn deaths
    dying = false;
    firing = false;
    alive = true;
    opening = false;
  }
  
  public ActionGameComponentState(boolean invulnerable, boolean dying,
      boolean firing, boolean alive, boolean opening) {
    super();
    this.invulnerable = invulnerable;
    this.dying = dying;
    this.firing = firing;
    this.alive = alive;
    this.opening = opening;
  }

  public boolean isInvulnerable() {
    return invulnerable;
  }

  public void setInvulnerable(boolean invulnerable) {
    this.invulnerable = invulnerable;
  }

  public boolean isDying() {
    return dying;
  }

  public void setDying(boolean dying) {
    this.dying = dying;
  }

  public boolean isFiring() {
    return firing;
  }

  public void setFiring(boolean firing) {
    this.firing = firing;
  }

  public boolean isAlive() {
    return alive;
  }

  public void setAlive(boolean alive) {
    this.alive = alive;
  }
  
  public boolean isOpening() {
    return opening;
  }
  
  public void setOpening(boolean opening) {
    this.opening = opening;
  }
}
