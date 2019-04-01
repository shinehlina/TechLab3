package com.company;

import com.company.exceptions.InvalidLineException;
import com.company.exceptions.NoSectionIntroduced;
import com.company.model.Properties;
import com.company.model.Property;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class PropertyFileParser {
    @Setter
    private List<String> content;
    @Getter
    private Properties properties = new Properties();

    private final Pattern headerPattern = Pattern.compile("\\[[A-Z].*]");
    private final Pattern propertyPattern = Pattern.compile("[A-Za-z0-9_]+ ?= ?[0-9.A-Za-z/]+");

    void parse() throws NoSectionIntroduced, InvalidLineException {
        if (!properties.isEmpty()) {
            return;
        }
        eraseComments();
        String currentSection = null;

        for (String str : content) {
            Matcher headerMatcher = headerPattern.matcher(str);
            Matcher propertyMatcher = propertyPattern.matcher(str);

            if (headerMatcher.matches()) {
                currentSection = str.substring(headerMatcher.start() + 1, headerMatcher.end() - 1);
            } else if (propertyMatcher.matches()) {
                if (currentSection == null) {
                    throw new NoSectionIntroduced(str);
                }

                String sss = str.substring(propertyMatcher.start(), propertyMatcher.end());
                String[] split = sss.split(" ?= ?");
                properties.addProperty(currentSection, convertToTypedProperty(split[0], split[1]));
            } else {
                throw new InvalidLineException(str);
            }
        }
    }

    private void eraseComments() {
        content = content.stream()
                .map(str -> str.replaceAll(";.*", "").trim())
                .filter(str -> !str.isEmpty())
                .collect(Collectors.toList());
    }

    private Property convertToTypedProperty(String name, String stringValue) {
        Integer intValue = tryParseInt(stringValue);
        Float floatValue = tryParseFloat(stringValue);
        if (intValue != null)
            return new Property<>(name, intValue);
        if (floatValue != null)
            return new Property<>(name, floatValue);
        return new Property<>(name, stringValue);
    }

    private Integer tryParseInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Float tryParseFloat(String string) {
        try {
            return Float.parseFloat(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
