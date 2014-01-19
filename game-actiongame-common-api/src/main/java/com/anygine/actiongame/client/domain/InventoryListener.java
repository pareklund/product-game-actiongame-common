package com.anygine.actiongame.client.domain;



public interface InventoryListener
/* <S extends ActionGameComponentState,
P extends ActionGamePlayer<S, P, L, GC, A, E>,
L extends ActionGameLevel<S, P, L, GC, A, E>,
GC extends ActionGameComponent<S, P, L, GC, A, E>,
A extends ActionGameActor<S, P, L, GC, A, E>,
E extends Enemy<S, P, L, GC, A, E>> */ 
/*
<S extends ActionGameComponentState,
  P extends ActionGamePlayer<?, ?, ?, ?, ?, ?>,
  L extends ActionGameLevel<?, ?, ?, ?, ?, ?, ?, ?, ?>,
  GC extends ActionGameComponent<?, ?, ?, ?, ?, ?>,
  A extends ActionGameActor<?, ?, ?, ?, ?, ?>,
  E extends Enemy<?, ?, ?, ?, ?, ?>,
  II extends InventoryItem<?, ?, ?, ?, ?, ?>> */
//<II extends InventoryItem<?, ?, ?>>
{
//	boolean onAdded(InventoryItem<S, P, L, GC, A, E> item);
  <II extends InventoryItem<?, ?>> boolean onAdded(II item);
}
