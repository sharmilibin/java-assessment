package com.assessment;

import java.util.Map;

public class CellPhoneUsageWrapper {
    private Map<Integer, CellPhoneUsage> map;
    private Integer totalMinutes;
    private Double totaldata;

    public Map<Integer, CellPhoneUsage> getMap() {
        return map;
    }

    public void setMap(Map<Integer, CellPhoneUsage> map) {
        this.map = map;
    }

    public Integer getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(Integer totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public Double getTotaldata() {
        return totaldata;
    }

    public void setTotaldata(Double totaldata) {
        this.totaldata = totaldata;
    }
}
