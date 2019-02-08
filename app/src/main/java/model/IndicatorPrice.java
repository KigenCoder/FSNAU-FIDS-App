package model;

public class IndicatorPrice {
    private String indicatorId;
    private String indicatorName;
    private String indicatorPrice;

    public IndicatorPrice(String indicatorId, String indicatorName, String indicatorPrice) {
        this.indicatorId = indicatorId;
        this.indicatorName = indicatorName;
        this.indicatorPrice = indicatorPrice;
    }

    public String getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(String indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getIndicatorPrice() {
        return indicatorPrice;
    }

    public void setIndicatorPrice(String indicatorPrice) {
        this.indicatorPrice = indicatorPrice;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }
}
