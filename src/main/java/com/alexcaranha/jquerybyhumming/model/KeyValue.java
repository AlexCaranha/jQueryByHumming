package com.alexcaranha.jquerybyhumming.model;

/**
 *
 * @author alexcaranha
 */
public final class KeyValue<K, V> {
    private K key;
    private V value;

    public KeyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setKey(K value) {
        this.key = value;
    }

    public void setValue(V value) {
        this.value = value;
    }
    
    @Override
    public KeyValue clone() {
        return new KeyValue(key, value);
    }
}
