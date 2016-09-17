package com.ofalvai.aircard;

public class CustomField {

    private String label;

    private String value;

    public CustomField(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }
}
