package com.kindlebit.pos.dto;

public class MonthlyReportDTOResponse {

    private Integer year;
    private String monthName;
    private Long numberOfOrder;
    private Double total_sale;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public Long getNumberOfOrder() {
        return numberOfOrder;
    }

    public void setNumberOfOrder(Long numberOfOrder) {
        this.numberOfOrder = numberOfOrder;
    }

    public Double getTotal_sale() {
        return total_sale;
    }

    public void setTotal_sale(Double total_sale) {
        this.total_sale = total_sale;
    }
}
