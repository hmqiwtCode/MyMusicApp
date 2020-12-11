package com.example.serviceboundmusic;

import java.io.Serializable;

public class Music implements Serializable {
    private int id;
    private String name;

    public Music(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Music() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Music{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
