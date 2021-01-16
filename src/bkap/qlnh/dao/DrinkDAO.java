/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.Drink;
import java.util.List;

/**
 *
 * @author ntan2
 */
public interface DrinkDAO {
    List<Drink> lstDrink(String name);
    void add(Drink d);
    void edit(Drink d);
    void remove(int id);
    Drink find(int id);
}
