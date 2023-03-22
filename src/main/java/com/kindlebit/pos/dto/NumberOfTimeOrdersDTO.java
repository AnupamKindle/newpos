package com.kindlebit.pos.dto;

import java.util.Comparator;

public class NumberOfTimeOrdersDTO  {
    private Long customerId;
    private Long totalOrder;

    public NumberOfTimeOrdersDTO(Long customerId, Long totalOrder) {
        this.customerId = customerId;
        this.totalOrder = totalOrder;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Long totalOrder) {
        this.totalOrder = totalOrder;
    }


}
