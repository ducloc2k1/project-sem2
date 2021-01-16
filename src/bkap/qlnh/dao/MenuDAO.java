/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.Menu;
import java.util.List;

/**
 *
 * @author ntan2
 */
public interface MenuDAO {
    List<Menu> lstMenu();
    void add(Menu m);
    void edit(Menu m);
    void remove(int id);
    Menu find(int id);
}
