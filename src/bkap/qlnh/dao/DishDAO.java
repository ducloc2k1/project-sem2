/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.Dish;
import java.util.List;

/**
 *
 * @author ntan2
 */
public interface DishDAO {
    List<Dish> lstDish(String name);
    void add(Dish d);
    void edit(Dish d);
    void remove(int id);
    Dish find(int id);
}
