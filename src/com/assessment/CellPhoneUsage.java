package com.assessment;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CellPhoneUsage {

    private Integer employeeId;
    private Map<String, Integer> minutesMap;
    private Map<String, Double> dataMap;

    public CellPhoneUsage(Comparator<String> comparator) {
        this.minutesMap = new TreeMap<>(comparator);
        this.dataMap = new TreeMap<>(comparator);
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Map<String, Integer> getMinutesMap() {
        return minutesMap;
    }

    public void setMinutesMap(Map<String, Integer> minutesMap) {
        this.minutesMap = minutesMap;
    }

    public Map<String, Double> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Double> dataMap) {
        this.dataMap = dataMap;
    }
}
