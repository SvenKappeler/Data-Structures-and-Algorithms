package com.skappele;

import java.io.Serializable;

public class Pair<K, V> implements Serializable {
    K key;
    V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public Business getValue() {
        return (Business) value;
    }

    public String getKey() {
        return (String) key;
    }
}
