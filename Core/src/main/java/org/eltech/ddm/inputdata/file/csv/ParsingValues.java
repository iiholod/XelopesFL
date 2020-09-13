package org.eltech.ddm.inputdata.file.csv;

import java.util.ArrayList;
import java.util.List;

public class ParsingValues {
    private String attributeName;
    private List<String> values = new ArrayList<>();

    public ParsingValues(String attributeName) {
        this.attributeName = attributeName;
    }

    public ParsingValues(String attributeName, String value) {
        this.attributeName = attributeName;
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

    public String getAttributeName() {
        return attributeName;
    }
}
