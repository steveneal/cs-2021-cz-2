package com.cs.rfq.decorator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rfq implements Serializable {
    private String id;
    @SerializedName(value = "instrumentId")
    private String isin;
    private Long traderId;
    private Long entityId;
    @SerializedName(value = "qty")
    private Long quantity;
    private Double price;
    private String side;

    private static Gson gson = new GsonBuilder().create();

    public static Rfq fromJson(String json) {
        return gson.fromJson(json, Rfq.class);
    }

    @Override
    public String toString() {
        return "Rfq{" +
                "id='" + id + '\'' +
                ", isin='" + isin + '\'' +
                ", traderId=" + traderId +
                ", entityId=" + entityId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", side=" + side +
                '}';
    }

    public boolean isBuySide() {
        return "B".equals(side);
    }

    public boolean isSellSide() {
        return "S".equals(side);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Long getTraderId() {
        return traderId;
    }

    public void setTraderId(Long traderId) {
        this.traderId = traderId;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
