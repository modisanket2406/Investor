package main.java.investor;

import java.util.Map;

class Holding extends Node {

    Double marketValue;
    Map<String, Integer> fundNameToQtyMap;

    public void setMarketValue(Double marketValue) {
        this.marketValue = marketValue;
    }

    public Double getMarketValue() {
        return marketValue;
    }

    public void setFundNameToQtyMap(Map<String, Integer> fundNameToQtyMap) {
        this.fundNameToQtyMap = fundNameToQtyMap;
    }

    public Map<String, Integer> getFundNameToQtyMap() {
        return fundNameToQtyMap;
    }

    @Override
    public String toString() {
        return "Level-3:" + this.name;
    }
}

