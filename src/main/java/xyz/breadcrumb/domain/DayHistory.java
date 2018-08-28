package xyz.breadcrumb.domain;

import java.util.List;

public class DayHistory {
    private String day;
    private int totalAmount;
    private List<Amount> amounts;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<Amount> getAmounts() {
        return amounts;
    }

    public void setAmounts(List<Amount> amounts) {
        this.amounts = amounts;
    }
}
