/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.BillDetail;
import java.util.List;

/**
 *
 * @author ntan2
 */
public interface BillDetailDAO {
    List<BillDetail> lstBillDetail();
    void add(BillDetail bd);
    void edit(BillDetail bd);
    void remove(int id);
    BillDetail find(int id);
}
