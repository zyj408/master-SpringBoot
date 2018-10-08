package com.huawei.master.core.bean;

import org.springframework.data.annotation.Id;

public abstract class Entity {

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Entity)) {
            return false;
        }
        Entity other = (Entity) obj;
        return this.id.equals(other.id);
    }
}
