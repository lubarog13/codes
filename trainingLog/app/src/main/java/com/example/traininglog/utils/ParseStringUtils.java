package com.example.traininglog.utils;

import com.example.traininglog.data.model.Building;

public class ParseStringUtils {
    public static String buildingAddress(Building building) {
        return building.getLiter()!=null?
    String.format("%s, %s д.%s л.%s", building.getCity(), building.getAddress(), building.getNumber(), building.getLiter()):
    String.format("%s, %s д.%s", building.getCity(), building.getAddress(), building.getNumber());
    }

    public static String buildingSmallAddress(Building building) {
        return building.getLiter()!=null?
                String.format("%s д.%s л.%s", building.getAddress(), building.getNumber(), building.getLiter()):
                String.format("%s д.%s", building.getAddress(), building.getNumber());
    }
}
