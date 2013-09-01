package com.alexcaranha.jquerybyhumming.screen.configuration.table;

import java.util.List;

public class ConfigurationTableItem<T> {

    private String label;
    private T[] options;
    private T value;
    private String description;

    public ConfigurationTableItem(String label, T[] options, T value, String hint) {
        this.label = label;
        this.options = options;
        this.value = value;
        this.description = (hint == null) ? "" : hint;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public T[] getOptions() {
        return options;
    }

    public void setOptions(T[] options) {
        this.options = options;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        String result;

        result = String.format("Label: %s\nValue: %s\nDescription: %s", label, value.toString(), description);
        return result;
    }

    public static void printConfigurationInConsole(List<ConfigurationTableItem> items) {
        if (items == null) return;

        for (ConfigurationTableItem item : items) {
            System.out.println(String.format("\n%s", item.toString()));
        }
    }
}
