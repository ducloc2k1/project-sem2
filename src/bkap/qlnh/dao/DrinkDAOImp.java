/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.Drink;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import bkap.qlnh.util.DConnection;

/**
 *
 * @author ntan2
 */
public class DrinkDAOImp implements DrinkDAO{

    @Override
    public List<Drink> lstDrink(String name) {
        List<Drink> lstDrink = new ArrayList<>();
        ResultSet rs = DConnection.executeQuery("{call getAllDrink()}");
        if(name!=null){
            rs = DConnection.executeQuery("{call findDrinkByName(?)}", name);
        }
        try {
            while(rs.next()){
                Drink d = new Drink();
                d.setId(rs.getInt("id"));
                d.setName(rs.getString("name"));
                d.setPrice(rs.getFloat("price"));
                d.setStatus(rs.getBoolean("status"));
                d.setMenuId(rs.getInt("menuId"));
                d.setMenuName(rs.getString("menuName"));
                lstDrink.add(d);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstDrink;
    }

    @Override
    public void add(Drink d) {
        if(DConnection.executeUpdate("{call addDrink(?,?,?,?)}", d.getName(),d.getPrice(),d.isStatus(),d.getMenuId())==0){
            JOptionPane.showMessageDialog(null, "Thêm Mới Thất bại,Đồ uống đã tồn tại");
        }else{
            JOptionPane.showMessageDialog(null, "Thêm Mới Thành Công");
        }
    }

    @Override
    public void edit(Drink d) {
        
        if(DConnection.executeUpdate("{call editDrink(?,?,?,?,?)}", d.getId(),d.getName(),d.getPrice(),d.isStatus(),d.getMenuId())==0){
            JOptionPane.showMessageDialog(null, "Chỉnh Sửa Thất Bại");
        }else{
            JOptionPane.showMessageDialog(null, "Chỉnh Sửa Thành Công");
        }
    }

    @Override
    public void remove(int id) {
        if(DConnection.executeUpdate("{call removeDrink(?)}", id)==0){
            JOptionPane.showMessageDialog(null, "Xóa thất bại");
        }else{
            JOptionPane.showMessageDialog(null, "Xóa Thành Công");
        }
    }

    @Override
    public Drink find(int id) {
        ResultSet rs = DConnection.executeQuery("{call findDrink(?)}",id);
        try {
            while(rs.next()){
                Drink d = new Drink();
                d.setId(rs.getInt("id"));
                d.setName(rs.getString("name"));
                d.setPrice(rs.getFloat("price"));
                d.setStatus(rs.getBoolean("status"));
                d.setMenuId(rs.getInt("menuId"));
                return d;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
