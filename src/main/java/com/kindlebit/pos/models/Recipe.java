package com.kindlebit.pos.models;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
public class Recipe {

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    private Menu menu;

    @Column(name = "name")
    private String name;

    @Column(name = "veg")
    private Boolean veg;

    @Column(name = "half_price")
    private Integer halfPrice;

    @Column(name = "full_price")
    private Integer fullPrice;


    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "image_data")
    private byte[] ImageData;


    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;


    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
