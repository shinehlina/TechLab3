package com.company.model;

import com.company.exceptions.NoProperty;
import lombok.ToString;

import java.util.*;

@ToString
public class Properties {
    private Map<String, Set<Property>> properties = new HashMap<>();

    public void addProperty(String sectionName, Property property) {
        if (properties.get(sectionName) == null)
            properties.put(sectionName, new HashSet<>(Collections.singletonList(property)));
        else {
            properties.get(sectionName).add(property);
        }
    }

    public boolean isEmpty() {
        return properties.isEmpty();
    }

    public <T> T getValue(String sectionName, String paramName, Class<T> clazz) throws NoProperty {
        if (properties.get(sectionName) == null)
            throw new NoProperty();
        Property property = properties.get(sectionName).stream()
                .filter(p -> p.getName().equals(paramName) && clazz.isInstance(p.getValue()))
                .findFirst()
                .orElseThrow(NoProperty::new);
        return clazz.cast(property.getValue());
    }
}
