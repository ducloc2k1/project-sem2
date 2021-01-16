/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.Order;
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
public class OrderDAOImp implements OrderDAO{

    @Override
    public List<Order> lstOrder() {
        List<Order> lstOrder = new ArrayList<>();
        ResultSet rs = DConnection.executeQuery("{call getAllOrder()}");
        try {
            while(rs.next()){
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setName(rs.getString("name"));
                o.setEmail(rs.getString("email"));
                o.setPhone(rs.getString("phone"));
                o.setQuantity(rs.getInt("quantity"));
                o.setDate(rs.getString("date"));
                o.setTime(rs.getString("time"));
                o.setGender(rs.getBoolean("gender"));
                o.setType(rs.getBoolean("type"));
                o.setRequirement(rs.getString("requirement"));
                o.setTableId(rs.getInt("tableId"));
                lstOrder.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstOrder;
    }

    @Override
    public void add(Order o) {
       DConnection.executeUpdate("{call addOrder(?,?,?,?,?,?,?,?,?,?)}", o.getName(),o.getEmail(),o.getPhone(),o.getQuantity(),o.getDate(),o.getTime(),o.isGender(),o.isType(),o.getRequirement(),o.getTableId());
    }

    @Override
    public void edit(Order o) {
        DConnection.executeUpdate("{call editOrder(?,?,?,?,?,?,?,?,?,?,?)}", o.getId(),o.getName(),o.getEmail(),o.getPhone(),o.getQuantity(),o.getDate(),o.getTime(),o.isGender(),o.isType(),o.getRequirement(),o.getTableId());
    }

    @Override
    public void remove(int id) {
        DConnection.executeUpdate("{call removeOrder(?)}", id);
    }

    @Override
    public Order find(int id) {
        ResultSet rs = DConnection.executeQuery("{call findOrder(?)}",id);
        try {
            while(rs.next()){
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setName(rs.getString("name"));
                o.setEmail(rs.getString("email"));
                o.setPhone(rs.getString("phone"));
                o.setQuantity(rs.getInt("quantity"));
                o.setDate(rs.getString("date"));
                o.setTime(rs.getString("time"));
                o.setGender(rs.getBoolean("gender"));
                o.setType(rs.getBoolean("type"));
                o.setRequirement(rs.getString("requirement"));
                o.setTableId(rs.getInt("tableId"));
                return o;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
