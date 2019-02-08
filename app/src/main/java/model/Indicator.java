package model;

public class Indicator {

    private String indicatorId = null;
    private String indicatorBusinessName = null;
    private String categoryName = null;
    private String indicatorPrice = null;

    public Indicator(String indicator_id, String indicator_business_name, String category_name, String price) {
        this.indicatorId = indicator_id;
        this.indicatorBusinessName = indicator_business_name;
        this.categoryName = category_name;
        this.indicatorPrice = price;
    }

    public String getIndicatorId() {
        return indicatorId;
    }

    public String getIndicatorBusinessName() {
        return indicatorBusinessName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getIndicatorPrice() {
        return indicatorPrice;
    }

    public void setIndicatorPrice(String indicatorPrice) {
        this.indicatorPrice = indicatorPrice;
    }
}
