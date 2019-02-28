package com.frezrik.router;


public interface Unbinder {
    void unbind();

    Unbinder EMPTY = new Unbinder() {
        @Override
        public void unbind() {
        }
    };
}

