package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.SoundWithPath_Embeddable;
import com.anygine.core.common.client.api.JsonWritableFactory;
import com.anygine.core.common.client.domain.JsonWritableFactoryImpl;
import com.anygine.core.common.codegen.api.JsonWritableInternal;
import com.anygine.core.common.codegen.api.JsonWritableInternal.TypeOfData;
import com.google.inject.Singleton;
import playn.core.Json;
import playn.core.Json.Object;

// TODO: Code generate!

// TODO: Place in same package in order to instantiate non-public classes
// TODO: Possibly place stuff in common base class
@Singleton
public class ActionGameJsonWritableFactoryImpl
        extends JsonWritableFactoryImpl
        implements JsonWritableFactory {

  @Override
  public <JW extends JsonWritableInternal> JW newInstance(
      Class<JW> clazz, Object jsonObj) {
    Json.Object fields = jsonObj.getObject(TypeOfData.Object.name());
    switch (JsonWritableInternal.JsonType.valueOf(jsonObj.getString("type"))) {
    case Effect:
      return (JW) new Effect_Embeddable(fields);
    case AmmoSupply:
      throw new UnsupportedOperationException("Not yet implemented");
      //        return (JW) new GunBase.AmmoSupply(fields);
    case SoundWithPath:
      return (JW) new SoundWithPath_Embeddable(jsonObj);
    default:
      return super.newInstance(clazz, jsonObj);
    }
  }

  @Override
  public <JW> JW newInstance(JW object) {
    JW instance = super.newInstance(object);
    if (instance != null) {
      return instance;
    }
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
