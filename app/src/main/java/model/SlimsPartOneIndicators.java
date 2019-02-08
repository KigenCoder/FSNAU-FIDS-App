package model;

import java.util.ArrayList;

public class SlimsPartOneIndicators {
    public static ArrayList<Object> listOfIndicators(){
        ArrayList<Object> indicators = new ArrayList<>();

        //Unskilled Labor
        indicators.add("Unskilled Labor");
        indicators.add(new Indicator("43", "Average Daily Rate (Agricultural)", "Unskilled Labor", ""));
        indicators.add(new Indicator("44", "Average Daily Rate (Non-Agricultural)", "Unskilled Labor", ""));

        //SLIMS cereals
        indicators.add("Cereals");
        indicators.add(new Indicator("3", "Red Sorghum 1kg", "cereals", ""));
        indicators.add(new Indicator("1", "White Sorghum 1kg", "cereals", ""));
        indicators.add(new Indicator("7", "White maize 1kg", "cereals", ""));
        indicators.add(new Indicator("40", "Rice Export Quality 1kg", "cereals", ""));
        indicators.add(new Indicator("11", "Wheat Flour 1kg", "cereals", ""));

        //Food - Others
        indicators.add("Food Others");
        indicators.add(new Indicator("13", "Sugar 1kg", "Food Others", ""));
        indicators.add(new Indicator("18", "Vegetable Oil Liter", "Food Others", ""));
        indicators.add(new Indicator("14", "Tea Leaves 1kg", "Food Others", ""));
        indicators.add(new Indicator("15", "Salt 1kg", "Food Others", ""));
        indicators.add(new Indicator("16", "Grinding Costs 1kg", "Food Others", ""));

        //Livestock
        indicators.add("Livestock");
        indicators.add(new Indicator("20", "Goat Local Quality", "Livestock", ""));
        indicators.add(new Indicator("25", "Fresh Camel Milk 1 litre", "Livestock", ""));
        indicators.add(new Indicator("26", "Fresh Cattle Milk 1 litre", "Livestock", ""));

        //Energy
        indicators.add("");
        indicators.add(new Indicator("28", "Firewood Bundle", "Energy", ""));
        indicators.add(new Indicator("31", "Kerosene 1 litre", "Energy", ""));

        //Water & Others
        indicators.add("Water & Others");
        indicators.add(new Indicator("41", "Water One Jerican 20 litre ", "Water & Others", ""));
        indicators.add(new Indicator("32", "Soap 1 Bar", "Water & Others", ""));

        //Transport
        indicators.add("Transport");
        indicators.add(new Indicator("42", "Transport Cost", "Transport Costs", ""));

       return indicators;
    }

}
