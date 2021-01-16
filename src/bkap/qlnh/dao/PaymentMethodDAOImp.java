/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.PaymentMethod;
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
public class PaymentMethodDAOImp implements PaymentMethodDAO{

    @Override
    public List<PaymentMethod> lstPaymentMethod() {
        List<PaymentMethod> lstPaymentMethod = new ArrayList<>();
        ResultSet rs = DConnection.executeQuery("{call getAllPaymentMethod()}");
        try {
            while(rs.next()){
                PaymentMethod pm = new PaymentMethod();
                pm.setId(rs.getInt("id"));
                pm.setName(rs.getString("name"));
                pm.setStatus(rs.getBoolean("status"));
                lstPaymentMethod.add(pm);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstPaymentMethod;
    }

    @Override
    public void add(PaymentMethod pm) {
        DConnection.executeUpdate("{call addPaymentMethod(?,?)}", pm.getName(),pm.isStatus());
    }

    @Override
    public void edit(PaymentMethod pm) {
        DConnection.executeUpdate("{call editPaymentMethod(?,?,?)}", pm.getId(),pm.getName(),pm.isStatus());
    }

    @Override
    public void remove(int id) {
        DConnection.executeUpdate("{call removePaymentMethod(?)}", id);
    }

    @Override
    public PaymentMethod find(int id) {
        ResultSet rs = DConnection.executeQuery("{call findPaymentMethod(?)}",id);
        try {
            while(rs.next()){
                PaymentMethod pm = new PaymentMethod();
                pm.setId(rs.getInt("id"));
                pm.setName(rs.getString("name"));
                pm.setStatus(rs.getBoolean("status"));
                return pm;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
