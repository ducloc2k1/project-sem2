/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.entities;

import java.sql.Date;

/**
 *
 * @author ntan2
 */
public class Resource {
    private int id;
    private String name;
    private int quantity;
    private String supplier;
    private Date date;
    private boolean status;

    public Resource() {
    }

    public Resource(int id, String name, int quantity, String supplier, Date date, boolean status) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.supplier = supplier;
        this.date = date;
        this.status = status;
    }

    public Resource(String name, int quantity, String supplier, Date date, boolean status) {
        this.name = name;
        this.quantity = quantity;
        this.supplier = supplier;
        this.date = date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
