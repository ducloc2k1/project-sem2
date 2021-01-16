/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.Bill;
import java.util.List;

/**
 *
 * @author ntan2
 */
public interface BillDAO {
    List<Bill> lstBill();
    void add(Bill b);
    void edit(Bill b);
    void remove(int id);
    Bill find(int id);
    Bill findBillByTable(int idTable);
}
