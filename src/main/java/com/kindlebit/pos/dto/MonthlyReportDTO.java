package com.kindlebit.pos.dto;

public class MonthlyReportDTO {

private  Integer year;

private Integer month;

private Long numberOfOrder;

    public MonthlyReportDTO(Integer year, Integer month, Long numberOfOrder, Double total_sale) {
        this.year = year;
        this.month = month;
        this.numberOfOrder = numberOfOrder;
        this.total_sale = total_sale;
    }

    private Double total_sale;


    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
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
