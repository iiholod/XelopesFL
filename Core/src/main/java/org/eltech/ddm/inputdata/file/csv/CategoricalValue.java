package org.eltech.ddm.inputdata.file.csv;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Kolpaschikov
 */
public class CategoricalValue {

    private final String value;
    private final List<String> values = new ArrayList<>();

    public CategoricalValue(String attributeName) {
        this.value = attributeName;
    }

    public CategoricalValue(String attributeName, String value) {
        this.value = attributeName;
        values.add(value);
    }

    public void add(String value) {
        values.add(value);
    }

    public boolean contains(String value) {
        return values.contains(value);
    }

    public int indexOf(String value) {
        return values.indexOf(value);
    }

    public int size() {
        return values.size();
    }

    public String getValue() {
        return value;
    }
}
