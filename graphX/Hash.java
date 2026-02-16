package com.graphX.graphX;

import com.graphX.graphX.helper.Pair;
import com.graphX.graphX.helper.Triple;

import java.util.Map;
import java.util.HashMap;

public class Hash{
    private final Map<Triple, Integer> hashTable = new HashMap<>();

    public Hash() {
    }

    public int hash(int x) {
        return hash(new Triple<>(x,0,0));
    }

    public int hash(Pair<Integer, Integer> pair) {
        return hash(new Triple<>(pair.first, pair.second, 0));
    }

    public int hash(Triple<Integer,Integer,Integer> triple) {
        if (hashTable.containsKey(triple)) {
            return hashTable.get(triple);
        }
        int hashVal = hashTable.size();
        hashTable.put(triple, hashVal);
        return hashVal;
    }
}
