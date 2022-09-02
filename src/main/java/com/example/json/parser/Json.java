package com.example.json.parser;

import java.util.*;

public class Json implements Map<String,Value>{
   private final Map<String,Value> map;

   public Json(){
      this.map = new LinkedHashMap<>();
   }
   public Json(List<Pair> pairList) {
      this.map = new LinkedHashMap<>(pairList.size());
      pairList.forEach(pair->{
         this.map.put(pair.key.key,pair.value);
      });
   }

   @Override
   public boolean isEmpty() {
      return map.isEmpty();
   }

   @Override
   public boolean containsKey(Object key) {
      return map.containsKey(key);
   }

   @Override
   public boolean containsValue(Object value) {
      return map.containsValue(value);
   }

   @Override
   public Value get(Object key) {
      return map.get(key);
   }

   @Override
   public Value put(String key, Value value) {
      return map.put(key,value);
   }

   @Override
   public Value remove(Object key) {
      return map.remove(key);
   }

   @Override
   public void putAll(Map<? extends String, ? extends Value> m) {
      map.putAll(m);
   }

   @Override
   public void clear() {
      map.clear();
   }

   @Override
   public Set<String> keySet() {
      return map.keySet();
   }

   @Override
   public Collection<Value> values() {
      return map.values();
   }

   @Override
   public Set<Entry<String, Value>> entrySet() {
      return map.entrySet();
   }

   @Override
   public boolean equals(Object o) {
      return map.equals(o);
   }

   @Override
   public int hashCode() {
      return map.hashCode();
   }

   @Override
   public int size() {
      return map.size();
   }
}
