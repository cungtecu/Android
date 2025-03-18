package com.example.learnvocabulary.model;

import java.io.Serializable;

public class Topic implements Serializable {
    private int id;
    private String name;
    private String description;

    // Constructor
    public Topic( String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getter & Setter
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Override toString() để dễ dàng hiển thị trong ListView/RecyclerView
    @Override
    public String toString() {
        return name;
    }
}
