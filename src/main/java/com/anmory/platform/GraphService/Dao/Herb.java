package com.anmory.platform.GraphService.Dao;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node(labels = "Herb")
public class Herb {
    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String name;

    public Herb() {}

    @Override
    public String toString() {
        return "Herb{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Herb(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}