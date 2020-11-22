package com.rin.model;

public enum Keys {
    LEFT("Left"),
    RIGHT("Right"),
    UP("Up"),
    DOWN("Down");

    public final String key;

    private Keys(String key) {
        this.key = key;
    }
}
