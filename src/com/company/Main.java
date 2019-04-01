package com.company;

import com.company.exceptions.NoProperty;
import com.company.exceptions.ParsingConfigException;
import com.company.model.Properties;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        PropertyFileParser parser = new PropertyFileParser();
        try {
            List<String> content = Files.readAllLines(Paths.get("/Users/Shinskey/Downloads/OOP2task3/src/input.txt"));
            parser.setContent(content);
            parser.parse();
            Properties properties = parser.getProperties();
            System.out.println(properties.getValue("COMMON", "StatisterTimeMs", Integer.class));
            System.out.println(properties.getValue("COMMON", "StatisterTimeMs", String.class));
            System.out.println(properties.getValue("ssss", "ddddd", Integer.class));

        } catch (IOException | ParsingConfigException | NoProperty e) { //todo
            e.printStackTrace();
        }
    }
}
