package me.silmoon.shopmanagement;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class ShopItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String title;
    private String description;
    private Integer price;
    private String item_image_url;
    private Date created_at;
    private Date updated_at;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setItem_image_url(String item_image_url) {
        this.item_image_url = item_image_url;
    }

    public String getItem_image_url() {
        return item_image_url;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
