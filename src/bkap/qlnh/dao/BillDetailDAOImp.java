/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.BillDetail;
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
public class BillDetailDAOImp implements BillDetailDAO{

    @Override
    public List<BillDetail> lstBillDetail() {
        List<BillDetail> lstBillDetail = new ArrayList<>();
        ResultSet rs = DConnection.executeQuery("{call getAllBillDetail()}");
        try {
            while(rs.next()){
                BillDetail bd = new BillDetail();
                bd.setId(rs.getInt("id"));
                bd.setBillId(rs.getInt("billId"));
                bd.setDrinkId(rs.getInt("drinkId"));
                bd.setDishId(rs.getInt("dishId"));
                bd.setDrinkQuantity(rs.getInt("drinkQuantity"));
                bd.setDishQuantity(rs.getInt("dishQuantity"));
                bd.setTotalPrice(rs.getFloat("totalPrice"));
                bd.setDiscount(rs.getFloat("discount"));
                bd.setStatus(rs.getBoolean("status"));
                lstBillDetail.add(bd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstBillDetail;
    }

    @Override
    public void add(BillDetail bd) {
        DConnection.executeUpdate("{call addBillDetail(?,?,?,?,?,?,?,?)}", bd.getBillId(), bd.getDrinkId(),bd.getDishId(),bd.getDrinkQuantity(),bd.getDishQuantity(),bd.getTotalPrice(),bd.getDiscount(),bd.isStatus());
    }

    @Override
    public void edit(BillDetail bd) {
        DConnection.executeUpdate("{call editBillDetail(?,?,?,?,?,?,?,?,?)}",bd.getId(), bd.getBillId(), bd.getDrinkId(),bd.getDishId(),bd.getDrinkQuantity(),bd.getDishQuantity(),bd.getTotalPrice(),bd.getDiscount(),bd.isStatus());
    }

    @Override
    public void remove(int id) {
        DConnection.executeUpdate("{call removeBillDetail(?)}", id);
    }

    @Override
    public BillDetail find(int id) {
        ResultSet rs = DConnection.executeQuery("{call findBillDetail(?)}",id);
        try {
            while(rs.next()){
                BillDetail bd = new BillDetail();
                bd.setId(rs.getInt("id"));
                bd.setBillId(rs.getInt("billId"));
                bd.setDrinkId(rs.getInt("drinkId"));
                bd.setDishId(rs.getInt("dishId"));
                bd.setDrinkQuantity(rs.getInt("drinkQuantity"));
                bd.setDishQuantity(rs.getInt("dishQuantity"));
                bd.setTotalPrice(rs.getFloat("totalPrice"));
                bd.setDiscount(rs.getFloat("discount"));
                bd.setStatus(rs.getBoolean("status"));
                return bd;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
