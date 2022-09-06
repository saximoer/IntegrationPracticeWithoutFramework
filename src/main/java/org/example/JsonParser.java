package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public static List<String> parseJsonData(JsonElement jsonElement) {
        List<String> petData = new ArrayList<>();
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        for (int i = 0; i < 10; i++) {
            petData.add(jsonArray.get(i).toString());
        }
        return petData;
    }
}
