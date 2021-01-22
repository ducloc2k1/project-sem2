/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.Table;
import java.util.List;

/**
 *
 * @author ntan2
 */
public interface TableDAO {
    List<Table>lstTable(String name);
    void add(Table t);
    void edit(Table t);
    void remove(int id);
    Table find(int id);
}
