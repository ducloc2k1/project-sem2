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
public class Drink {
    private int id;
    private String name;
    private float price;
    private int menuId;
    private String menuName;
    private boolean status;

    public Drink() {
    }

    public Drink(int id, String name, float price, int menuId, String menuName, boolean status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuId = menuId;
        this.menuName = menuName;
        this.status = status;
    }
    
    public Drink(int id, String name, float price, int menuId, boolean status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuId = menuId;
        this.status = status;
    }

    public Drink(String name, float price, int menuId, boolean status) {
        this.name = name;
        this.price = price;
        this.menuId = menuId;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    
}
