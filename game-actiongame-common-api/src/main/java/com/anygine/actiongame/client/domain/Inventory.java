package com.anygine.actiongame.client.domain;

import java.util.ArrayList;
import java.util.List;

import com.anygine.core.common.client.annotation.Storable;

// TODO: Implement EntityHolder(Internal) in generated class
//       based upon discovering that the class holds an entity (duh)
@Storable
public class  Inventory {

  private static final int NOT_FOUND = -1;

//  InventoryItem test;
  List<InventoryItem<?, ?>> items;
  int selectedIndex;
  private transient List<InventoryListener> listeners;

  public Inventory() {
    items = new ArrayList<InventoryItem<?, ?>>();
    listeners = new ArrayList<InventoryListener>();
  }

  /*
  private Inventory(Inventory other, Json.Object updateSpec) {
    this.items = copyEntityListField(other.items, updateSpec.getArray("items"));
    this.selectedIndex = other.selectedIndex;
    this.listeners = other.listeners; 
  }

  public Inventory(Object fields) {
    items = jsonWritableFactory.newList(
        InventoryItem.class, fields.getObject("items"));
    selectedIndex = fields.getInt("selectedIndex");
    // TODO: Handle inventory listeners (anon classes) in some way
  }
*/
  
  public boolean isEmpty() {
    return items.size() == 0;
  }

  // Before adding item, run all inventory listeners
  // If any of them returns false, don't add item to inventory
  // and don't run any possible subsequent listeners
  public void addItem(InventoryItem<?, ?> item) {
    for (InventoryListener listener: listeners) {
/*
      if (onAdded(listener, item)) {
        return;
      }
      */
      if (!listener.onAdded(item)) {
        return;
      }
    }
    items.add(item);
    listeners.addAll(item.getListeners());
  }

  public List<? extends InventoryItem<?, ?>> getItems() {
    return items;
  }

  public int getSelectedIndex() {
    return selectedIndex;
  }

  public InventoryItem<?, ?> getSelectedItem() {
    return items.get(selectedIndex);
  }

  public void selectNextItem() {
    if (items.size() == 0) {
      return;
    }
    selectedIndex = (selectedIndex + 1) % items.size(); 
  }

  public void selectPreviousItem() {
    if (items.size() == 0) {
      return;
    }
    selectedIndex = (selectedIndex + items.size() - 1) % items.size(); 
  }

  public void removeSelectedItem() {
    items.remove(selectedIndex);
    selectedIndex = (items.size() == 0 ? 0 : selectedIndex % items.size()); 
  }

  public void removeNamedItem(String name) {
    int index = getIndexForNamedItem(name);
    if (index != NOT_FOUND) {  
      items.remove(index);
      selectedIndex = (index <= selectedIndex ? Math.max(0, selectedIndex - 1) : selectedIndex); 
    }
  }

  private int getIndexForNamedItem(String name) {
    for (int i = 0; i < items.size(); i++) {
      if (name.equals(items.get(i).getName())) {
        return i;
      }
    }
    return NOT_FOUND;
  }

  public List<Gun<?, ?>> getGuns() {
    List<Gun<?, ?>> guns = new ArrayList<Gun<?, ?>>();
    for (InventoryItem<?, ?> item : items) {
      // TODO: Or is it the other way around...?
      if (Gun.class.isAssignableFrom(item.getClass())) {
        guns.add((Gun<?, ?>) item);
      }
    }
    return guns;
  }

  /*
  @Override
  public JsonType getJsonType() {
    return JsonType.Inventory;
  }

  @Override
  public void update(Object jsonObj) {
    items = updateList(InventoryItem.class, items, jsonObj, "items");
    selectedIndex = update(selectedIndex, jsonObj, "selectedIndex");
    // TODO: Handle inventory listeners (anon classes) in some way
  }

  @Override
  protected void _writeJson(Writer writer) {
    writeList(items, writer, "items");
    writer.value("selectedIndex", selectedIndex);
    // TODO: Handle inventory listeners (anon classes) in some way
  }

  @Override
  public void write(EntityWriter entityWriter, String key) {
    Json.Writer writer = entityWriter.getWriter();
    _writeJsonHeader(writer, key, TypeOfData.Object);
    _write(entityWriter);
    _writeJsonFooter(writer);
  }

  protected void _write(EntityWriter entityWriter) {
    Json.Writer writer = entityWriter.getWriter();
    writeEntityList(items, entityWriter, "items");
    writer.value("selectedIndex", selectedIndex);
  }

  // Only copy if update spec holds changes to held entities
  @Override
  public Inventory entityHolderCopy(Object updateSpec) {
//    return new Inventory(this, updateSpec);
    return null;
  }
*/

}