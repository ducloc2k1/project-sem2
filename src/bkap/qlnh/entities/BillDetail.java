/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.entities;

/**
 *
 * @author ntan2
 */
public class BillDetail {
    private int id;
    private int billId;
    private Integer drinkId;
    private Integer dishId;
    private int drinkQuantity;
    private int dishQuantity;
    private float totalPrice;
    private float discount;
    private boolean status;

    public BillDetail() {
    }

    public BillDetail(int id, int billId, Integer drinkId, Integer dishId, int drinkQuantity, int dishQuantity, float totalPrice, float discount, boolean status) {
        this.id = id;
        this.billId = billId;
        this.drinkId = drinkId;
        this.dishId = dishId;
        this.drinkQuantity = drinkQuantity;
        this.dishQuantity = dishQuantity;
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public Integer getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(Integer drinkId) {
        this.drinkId = drinkId;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public int getDrinkQuantity() {
        return drinkQuantity;
    }

    public void setDrinkQuantity(int drinkQuantity) {
        this.drinkQuantity = drinkQuantity;
    }

    public int getDishQuantity() {
        return dishQuantity;
    }

    public void setDishQuantity(int dishQuantity) {
        this.dishQuantity = dishQuantity;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    
    
}
