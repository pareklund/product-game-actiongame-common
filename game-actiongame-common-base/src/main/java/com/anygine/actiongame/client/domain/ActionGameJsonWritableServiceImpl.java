package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.api.JsonWritableService;
import com.anygine.core.common.client.domain.JsonWritableServiceImpl;
import com.anygine.core.common.codegen.api.JsonWritableInternal;
import com.anygine.core.common.codegen.api.JsonWritableInternal.TypeOfData;
import playn.core.Json.Object;

public class ActionGameJsonWritableServiceImpl
        extends JsonWritableServiceImpl implements JsonWritableService {

  @Override
  public <JW extends JsonWritableInternal> Class<JW> getClass(Object jsonObj) {
    Object fields = jsonObj.getObject(TypeOfData.Object.name());
    switch (JsonWritableInternal.JsonType.valueOf(jsonObj.getString("type"))) {
    case Effect:
      return (Class<JW>) Effect_Embeddable.class;
    case AmmoSupply:
      throw new UnsupportedOperationException("Not yet implemented");
      //        return (Class<JW>) GunBase.AmmoSupply.class;
    default:
      return super.getClass(jsonObj);
    }
  }

  @Override
  public Class<?> getClass(String string) {
    // TODO: Implement
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
