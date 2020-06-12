package org.owly.duckdns.bugs.quarkus.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class TestEntity extends PanacheEntity {
    private String name;

    public TestEntity() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "TestEntity(name=" + this.getName() + ", id=" + super.id + ")";
    }

    public boolean equals(final Object other) {
        return other instanceof TestEntity &&
                super.equals(other) &&
                Objects.equals(this.id, ((TestEntity) other).id) &&
                Objects.equals(this.name, ((TestEntity) other).name);
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final String name = this.getName();
        final Long id = this.id;
        result = result * PRIME + (name == null ? 43 : name.hashCode());
        result = result * PRIME + (id == null ? 43 : id.hashCode());
        return result;
    }
}
