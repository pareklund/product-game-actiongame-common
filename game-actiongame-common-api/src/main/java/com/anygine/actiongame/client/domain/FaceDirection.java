package com.anygine.actiongame.client.domain;

// TODO: Replace with interface to allow for game-type specific 
//       direction sets
public enum FaceDirection {
  Left(-1), Right(1);

  private float value;
  
  FaceDirection(float value) {
    this.value = value;
  }
  
  public float getValue() {
    return value;
  }
  
  public String toString() {
    return String.valueOf(value);
  }
}
