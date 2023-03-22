package com.kindlebit.pos.dto;

import com.kindlebit.pos.models.Menu;

import java.util.Date;

public class RecipeDTO {

    private Long id;

    private Menu menu;

    private String name;

    private Boolean veg;

    public Integer getQuaterPrice() {
        return quaterPrice;
    }

    public void setQuaterPrice(Integer quaterPrice) {
        this.quaterPrice = quaterPrice;
    }

    private Integer halfPrice;

    private Integer fullPrice;

    private Integer quaterPrice;

    private String description;

    public Integer getFullQuantity() {
        return fullQuantity;
    }

    public void setFullQuantity(Integer fullQuantity) {
        this.fullQuantity = fullQuantity;
    }

    public Integer getHalfQuantity() {
        return halfQuantity;
    }

    public void setHalfQuantity(Integer halfQuantity) {
        this.halfQuantity = halfQuantity;
    }

    private byte[] ImageData;

    private Date createdAt;

    private Date updatedAt;

    private  Integer fullQuantity;

    private  Integer halfQuantity;

    private  Integer quaterQuantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getVeg() {
        return veg;
    }

    public void setVeg(Boolean veg) {
        this.veg = veg;
    }

    public Integer getHalfPrice() {
        return halfPrice;
    }

    public void setHalfPrice(Integer halfPrice) {
        this.halfPrice = halfPrice;
    }

    public Integer getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(Integer fullPrice) {
        this.fullPrice = fullPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImageData() {
        return ImageData;
    }

    public void setImageData(byte[] imageData) {
        ImageData = imageData;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


    public Integer getQuaterQuantity() {
        return quaterQuantity;
    }

    public void setQuaterQuantity(Integer quaterQuantity) {
        this.quaterQuantity = quaterQuantity;
    }
}
