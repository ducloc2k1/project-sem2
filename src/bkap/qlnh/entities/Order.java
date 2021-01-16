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
public class Order {
    private int id;
    private String name;
    private String email;
    private String phone;
    private int quantity;
    private String date;
    private String time;
    private boolean gender;
    private boolean type;
    private String requirement;
    private int tableId;

    public Order() {
    }

    public Order(int id, String name, String email, String phone, int quantity, String date, String time, boolean gender, boolean type, String requirement, int tableId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.quantity = quantity;
        this.date = date;
        this.time = time;
        this.gender = gender;
        this.type = type;
        this.requirement = requirement;
        this.tableId = tableId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }
    
}
