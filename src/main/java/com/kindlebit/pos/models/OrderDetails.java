package com.kindlebit.pos.models;


import javax.persistence.*;

@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name ="order_id")
    private Long orderId;

    @Column(name ="recipe_name")
    private String recipeName;


    @Column(name ="half_quantity")
    private Integer halfQuantity;

    @Column(name ="full_quantity")
    private Integer fullQuantity;

    @Column(name ="total_amount")
    private Integer totalAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public Integer getHalfQuantity() {
        return halfQuantity;
    }

    public void setHalfQuantity(Integer halfQuantity) {
        this.halfQuantity = halfQuantity;
    }

    public Integer getFullQuantity() {
        return fullQuantity;
    }

    public void setFullQuantity(Integer fullQuantity) {
        this.fullQuantity = fullQuantity;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }
}
