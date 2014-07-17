package net.acomputerdog.OBFUtil.util;

import java.util.HashMap;
import java.util.Map;

public class MultiBind<C, K, V> {
    private final Map<C, Map<K, V>> bindData = new HashMap<C, Map<K, V>>();

    public void put(C category, K key, V value) {
        getOrCreateCategory(category).put(key, value);
    }

    public V getValue(C category, K key) {
        Map<K, V> cat = getCategory(category);
        if (cat == null) {
            return null;
        }
        return cat.get(key);
    }

    public Map<K, V> getCategory(C category) {
        return bindData.get(category);
    }

    public boolean categoryExists(C category) {
        return bindData.containsKey(category);
    }

    public boolean keyExists(C category, K key) {
        Map<K, V> cat = getCategory(category);
        if (cat == null) {
            return false;
        }
        return cat.containsKey(key);
    }

    private Map<K, V> getOrCreateCategory(C category) {
        Map<K, V> cat = getCategory(category);
        if (cat == null) {
            bindData.put(category, cat = new HashMap<K, V>());
        }
        return cat;
    }
}
