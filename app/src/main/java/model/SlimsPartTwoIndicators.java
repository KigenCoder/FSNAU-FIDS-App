package model;

import java.util.ArrayList;

public class SlimsPartTwoIndicators {
    public static ArrayList<Object> listOfIndicators(){
        ArrayList<Object> indicators = new ArrayList<>();
        //School attendance
        indicators.add("School Attendance");
        indicators.add(new Indicator("45", "Koranic School Boy Pupils", "School Attendance", ""));
        indicators.add(new Indicator("46", "Koranic School Girl Pupils", "School Attendance", ""));
        indicators.add(new Indicator("47", "Primary School Boy Pupils", "School Attendance", ""));
        indicators.add(new Indicator("48", "Primary School Girl Pupils", "School Attendance", ""));

        //Local Credit
        indicators.add("Local Credit");
        indicators.add(new Indicator("49", "Number of People Receiving Credit", "Local Credit", ""));

        //Remittances
        indicators.add("Remittances");
        indicators.add(new Indicator("50", "Number of People Receiving Remittances", "Remittances", ""));

        //Migration
        indicators.add("Migration");
        indicators.add(new Indicator("52", "Number of Households in-migrated", "Migration", ""));
        indicators.add(new Indicator("53", "Number of Households out-migrated", "Migration", ""));

        //Civil Insecurity
        indicators.add("Civil Insecurity");
        indicators.add(new Indicator("54", "Level Of Civil Insecurity", "Civil Insecurity", ""));

        return indicators;
    }



}
