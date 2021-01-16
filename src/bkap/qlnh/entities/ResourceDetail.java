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
public class ResourceDetail {
    private int id;
    private int dishId;
    private int resourceId;

    public ResourceDetail() {
    }

    public ResourceDetail(int id, int dishId, int resourceId) {
        this.id = id;
        this.dishId = dishId;
        this.resourceId = resourceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
    
}
