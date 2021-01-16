/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.Order;
import java.util.List;

/**
 *
 * @author ntan2
 */
public interface OrderDAO {
    List<Order> lstOrder();
    void add(Order o);
    void edit(Order o);
    void remove(int id);
    Order find(int id);
}
