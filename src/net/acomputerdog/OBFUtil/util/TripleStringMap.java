package net.acomputerdog.OBFUtil.util;

import net.acomputerdog.core.hash.Hash;

import java.util.HashMap;
import java.util.Map;

public class TripleStringMap {

    private final Map<String, TSMItem> item1Map = new HashMap<String, TSMItem>();
    private final Map<String, TSMItem> item2Map = new HashMap<String, TSMItem>();
    private final Map<String, TSMItem> item3Map = new HashMap<String, TSMItem>();

    public void addItems(String string1, String string2, String string3) {
        TSMItem items = new TSMItem(string1, string2, string3);
        putItem(item1Map, string1, items);
        putItem(item2Map, string2, items);
        putItem(item3Map, string3, items);
    }

    private void putItem(Map<String, TSMItem> cat, String item, TSMItem items) {
        if (item != null && !item.trim().isEmpty()) {
            cat.put(item, items);
        }
    }

    public TSMItem getFrom1(String item) {
        return item1Map.get(item);
    }

    public TSMItem getFrom2(String item) {
        return item2Map.get(item);
    }

    public TSMItem getFrom3(String item) {
        return item3Map.get(item);
    }

    public boolean hasItem1(String item1) {
        return item1Map.containsKey(item1);
    }

    public boolean hasItem2(String item2) {
        return item2Map.containsKey(item2);
    }

    public boolean hasItem3(String item3) {
        return item3Map.containsKey(item3);
    }

    public static class TSMItem {
        private String item1;
        private String item2;
        private String item3;

        private TSMItem(String item1, String item2, String item3) {
            this.item1 = item1;
            this.item2 = item2;
            this.item3 = item3;
        }

        public String getItem1() {
            return item1;
        }

        public String getItem2() {
            return item2;
        }

        public String getItem3() {
            return item3;
        }

        @Override
        public int hashCode() {
            int hash = Hash.SEED;
            hash = Hash.hash(hash, item1);
            hash = Hash.hash(hash, item2);
            hash = Hash.hash(hash, item3);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) return false;
            if (obj == this) return true;
            if (obj instanceof TSMItem) {
                TSMItem other = (TSMItem) obj;
                return doesEqual(other.item1, this.item1) && doesEqual(other.item2, this.item2) && doesEqual(other.item3, this.item3);
            }
            return false;
        }

        @Override
        public String toString() {
            return "[" + item1 + ',' + item2 + ',' + item3 + ']';
        }

        private boolean doesEqual(Object obj1, Object obj2) {
            if (obj1 == null) {
                return obj2 == null;
            }
            return obj1.equals(obj2);
        }
    }
}
