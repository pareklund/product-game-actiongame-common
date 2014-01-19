package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.api.EntityFactory;
import com.anygine.core.common.client.api.EntityService;
import com.anygine.core.common.client.domain.EntityFactoryImpl;
import com.anygine.core.common.codegen.api.EntityInternal;
import com.anygine.core.common.codegen.api.JsonWritableInternal;
import com.anygine.core.common.codegen.api.JsonWritableInternal.TypeOfData;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import playn.core.Json.Object;

// TODO: Code generate
@Singleton
public class ActionGameEntityFactoryImpl
        extends EntityFactoryImpl implements EntityFactory {

  @Inject
  public ActionGameEntityFactoryImpl(EntityService entityService) {
    super(entityService);
  }

  @Override
  public <T> EntityInternal<T> newEntity(T object, long id, int version) {
    if (object.getClass().getName().equals("com.anygine.core.common.client.Inventory")) {
      Inventory inventory = (Inventory) object;
      return (EntityInternal<T>) new Inventory_Storable(inventory, id, version);
    }
    // ...
    return super.newEntity(object, id, version);
  }

  @Override
  public <JW extends JsonWritableInternal> JW newInstance(
      Class<JW> clazz, Object jsonObj) {
    Object fields = jsonObj.getObject(TypeOfData.Object.name());
    switch (JsonWritableInternal.JsonType.valueOf(jsonObj.getString("type"))) {
      case Inventory:
        return (JW) new Inventory_Storable(fields);
      default:
        return super.newInstance(clazz, jsonObj);
    }
  }

  @Override
  public <T> EntityInternal<T> newEntity(Class<T> clazz, Object jsonObj) {
    // TODO: Implement
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
