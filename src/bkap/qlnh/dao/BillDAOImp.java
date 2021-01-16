/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.Bill;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import bkap.qlnh.util.DConnection;

/**
 *
 * @author ntan2
 */
public class BillDAOImp implements BillDAO{

    @Override
    public List<Bill> lstBill() {
        List<Bill> lstBill = new ArrayList<>();
        ResultSet rs = DConnection.executeQuery("{call getAllBill()}");
        try {
            while(rs.next()){
                Bill b = new Bill();
                b.setId(rs.getInt("id"));
                b.setStatus(rs.getBoolean("status"));
                b.setTotalCost(rs.getFloat("totalCost"));
                b.setStareDate(rs.getTimestamp("startDate"));
                b.setPaymentMethodId(rs.getInt("paymentMethodId"));
                lstBill.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstBill;
    }

    @Override
    public void add(Bill b) {
        DConnection.executeUpdate("{call addBill(?,?,?,?,?,?,?,?,?,?)}", b.isStatus(), b.getTotalCost(), 
                b.getStareDate(), b.getEndDate(), b.getNumberCustomer(),
                b.getNameCustomer(),b.getIdEmployee(),b.getIdTable(),
                b.getPaymentMethodId(),b.getNote());
    }

    @Override
    public void edit(Bill b) {
            DConnection.executeUpdate("{call editBill(?,?,?,?,?,?,?,?,?,?,?)}",b.getId(), b.isStatus(), b.getTotalCost(), 
            b.getStareDate(), b.getEndDate(), b.getNumberCustomer(),
            b.getNameCustomer(),b.getIdEmployee(),b.getIdTable(),
            b.getPaymentMethodId(),b.getNote());
    }

    @Override
    public void remove(int id) {
        DConnection.executeUpdate("{call removeBill(?)}", id);
    }

    @Override
    public Bill find(int id) {
        ResultSet rs = DConnection.executeQuery("{call findBill(?)}",id);
        try {
            while(rs.next()){
                Bill b = new Bill();
                b.setId(rs.getInt("id"));
                b.setStatus(rs.getBoolean("status"));
                b.setTotalCost(rs.getFloat("totalCost"));
                b.setStareDate(rs.getTimestamp("startDate"));
                b.setPaymentMethodId(rs.getInt("paymentMethodId"));
                b.setIdEmployee(rs.getInt("idEmployee"));
                b.setEndDate(rs.getTimestamp("endDate"));
                b.setNameCustomer(rs.getString("nameCustomer"));
                b.setNumberCustomer(rs.getInt("numberCustomer"));
                b.setNote(rs.getString("note"));
                b.setIdTable(rs.getInt("idTable"));
                return b;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Bill findBillByTable(int idTable) {
        ResultSet rs = DConnection.executeQuery("{call findBillByTable(?)}",idTable);
        try {
            while(rs.next()){
                Bill b = new Bill();
                b.setId(rs.getInt("id"));
                b.setStatus(rs.getBoolean("status"));
                b.setTotalCost(rs.getFloat("totalCost"));
                b.setStareDate(rs.getTimestamp("startDate"));
                b.setPaymentMethodId(rs.getInt("paymentMethodId"));
                b.setIdEmployee(rs.getInt("idEmployee"));
                b.setEndDate(rs.getTimestamp("endDate"));
                b.setNameCustomer("nameCustomer");
                b.setNumberCustomer(rs.getInt("numberCustomer"));
                b.setNote(rs.getString("note"));
                b.setIdTable(rs.getInt("idTable"));
                return b;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
}
