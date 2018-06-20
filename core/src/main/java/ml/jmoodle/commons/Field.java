package ml.jmoodle.commons;

import java.io.Serializable;

public enum Field implements Serializable {
    ID("id"), IDNUMBER("idnumber"), USERNAME("username"), EMAIL("email");

    private String fieldName;

    private Field(String fieldName) {
        this.fieldName = fieldName;
    }
    
    public String getValue() {
        return fieldName;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}