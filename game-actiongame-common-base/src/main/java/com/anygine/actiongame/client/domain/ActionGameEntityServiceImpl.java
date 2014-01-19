package com.anygine.actiongame.client.domain;

import com.anygine.core.common.client.api.EntityFactory;
import com.anygine.core.common.client.api.EntityService;
import com.anygine.core.common.client.api.EntityStorage;
import com.anygine.core.common.client.domain.EntityServiceImpl;
import com.anygine.core.common.codegen.api.JsonWritableInternal;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import playn.core.Json.Object;

@Singleton
public class ActionGameEntityServiceImpl
        extends EntityServiceImpl implements EntityService {

  @Inject
  protected ActionGameEntityServiceImpl(
          EntityStorage storage, EntityFactory factory) {
    super(storage, factory);
  }

  @Override
  public <JW extends JsonWritableInternal> Class<JW> getClass(Object jsonObj) {
    switch (JsonWritableInternal.JsonType.valueOf(jsonObj.getString("type"))) {
      case Inventory:
        return (Class<JW>) Inventory_Storable.class;
      default:
        return super.getClass(jsonObj);
    }
  }

  @Override
  public <T> Class<T> getEntityClass(Object jsonObj) {
    // TODO: Implement
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
