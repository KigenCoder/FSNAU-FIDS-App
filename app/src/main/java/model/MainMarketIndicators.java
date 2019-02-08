package model;

import java.util.ArrayList;

public class MainMarketIndicators {

    public static ArrayList<Object> listOfIndicators(){
        ArrayList<Object> indicators = new ArrayList<>();
        //Cereals
        indicators.add("Cereals");
        indicators.add(new Indicator("1", "White Sorghum 1kg", "cereals", ""));
        indicators.add(new Indicator("2", "White Sorghum Bag/50kg", "cereals", ""));
        indicators.add(new Indicator("3", "Red Sorghum 1kg", "cereals", ""));
        indicators.add(new Indicator("4", "Red Sorghum 50kg", "cereals", ""));
        indicators.add(new Indicator("5", "Yellow maize 1kg", "cereals", ""));
        indicators.add(new Indicator("6", "Yellow maize 50kg", "cereals", ""));
        indicators.add(new Indicator("7", "White maize 1kg", "cereals", ""));
        indicators.add(new Indicator("8", "White maize 50kg", "cereals", ""));
        indicators.add(new Indicator("9", "Imported Red Rice 1kg", "cereals", ""));
        indicators.add(new Indicator("10", "Wheat Grain 1kg", "cereals", ""));
        indicators.add(new Indicator("11", "Wheat Flour 1kg", "cereals", ""));

        //Other Food Items
        indicators.add("Food Others");
        indicators.add(new Indicator("12", "Cow Peas 1kg", "Food Others", ""));
        indicators.add(new Indicator("13", "Sugar 1kg", "Food Others", ""));
        indicators.add(new Indicator("14", "Tea Leaves 1kg", "Food Others", ""));
        indicators.add(new Indicator("15", "Salt 1kg", "Food Others", ""));
        indicators.add(new Indicator("16", "Grinding Costs 1kg", "Food Others", ""));
        indicators.add(new Indicator("17", "Local Sesame Oil Liter", "Food Others", ""));
        indicators.add(new Indicator("18", "Vegetable Oil Liter", "Food Others", ""));

        //Livestock
        indicators.add("Livestock");
        indicators.add(new Indicator("19", "Goat Export Quality", "Livestock", ""));
        indicators.add(new Indicator("20", "Goat Local Quality", "Livestock", ""));
        indicators.add(new Indicator("21", "Sheep Export Quality", "Livestock", ""));
        indicators.add(new Indicator("22", "Cattle Export Quality", "Livestock", ""));
        indicators.add(new Indicator("23", "Cattle Local Quality", "Livestock", ""));
        indicators.add(new Indicator("24", "Camel Local Quality", "Livestock", ""));
        indicators.add(new Indicator("25", "Fresh Camel Milk 1 litre", "Livestock", ""));
        indicators.add(new Indicator("26", "Fresh Cattle Milk 1 litre", "Livestock", ""));

        //Energy
        indicators.add("Energy");
        indicators.add(new Indicator("27", "Charcoal 50kg", "Energy", ""));
        indicators.add(new Indicator("28", "Firewood Bundle", "Energy", ""));
        indicators.add(new Indicator("29", "Diesel 1 litre", "Energy", ""));
        indicators.add(new Indicator("30", "Petrol 1 litre", "Energy", ""));
        indicators.add(new Indicator("31", "Kerosene 1 litre", "Energy", ""));

        //Water & Others
        indicators.add("Water & Others");
        indicators.add(new Indicator("32", "Soap 1 Bar", "Water & Others", ""));
        indicators.add(new Indicator("33", "Water Drum", "Water & Others", ""));

        //Unskilled Labor
        indicators.add("Unskilled Labor");
        indicators.add(new Indicator("34", "Daily Labor Rate", "Unskilled Labor", ""));

        //Exchange Rates
        indicators.add("Exchange Rates");
        indicators.add(new Indicator("35", "Somali Shilling To USD", "Exchange Rate", ""));
        indicators.add(new Indicator("36", "Somaliland Sh To USD", "Exchange Rate", ""));

        //Boarder Town Exchange rates
        indicators.add("Border Town Exchange Rates");
        indicators.add(new Indicator("37", "Somaliland Sh To Djibouti Franc", "Border Town Exchange Rates", ""));
        indicators.add(new Indicator("38", "Somaliland Sh To Ethiopian Bir", "Border Town Exchange Rates", ""));
        indicators.add(new Indicator("39", "Somali Shilling To Ksh", "Border Town Exchange Rates", ""));

        //Building Materials
        indicators.add("Building Materials");
        indicators.add(new Indicator("68", "Cement 50kg", "Building Materials", ""));
        indicators.add(new Indicator("69", "Roofing Nails 15Kg", "Building Materials", ""));
        indicators.add(new Indicator("70", "Galvanised Iron Sheet Ga 26", "Building Materials", ""));
        indicators.add(new Indicator("71", "Timber 2in x 4in x 20ft", "Building Materials", ""));
        indicators.add(new Indicator("72", "Hollow Concrete Block 10cm x 20cm x 40cm", "Building Materials", ""));
        indicators.add(new Indicator("73", "Plastic Tarpaulin 14 x 5 metres", "Building Materials", ""));
        indicators.add(new Indicator("74", "Non Collapsable Jerrycan 10l", "Building Materials", ""));
        indicators.add(new Indicator("75", "Cooking Pot Aluminium 7l", "Building Materials", ""));
        indicators.add(new Indicator("76", "Woven Dry Raised Blanket 150cm x 200cm", "Building Materials", ""));


        return indicators;
    }





}
