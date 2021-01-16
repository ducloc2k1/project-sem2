/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.Dish;
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
public class DishDAOImp implements DishDAO{

    @Override
    public List<Dish> lstDish(String name) {
        List<Dish> lstDish = new ArrayList<>();
        ResultSet rs = DConnection.executeQuery("{call getAllDishJoin()}");
        if(name!=null){
            rs = DConnection.executeQuery("{call findDishByName(?)}", name);
        }
        try {
            while(rs.next()){
                Dish d = new Dish();
                d.setId(rs.getInt("id"));
                d.setName(rs.getString("name"));
                d.setPrice(rs.getFloat("price"));
                d.setStatus(rs.getBoolean("status"));
                d.setMenuId(rs.getInt("menuId"));
                d.setMenuName(rs.getString("menuName"));
                lstDish.add(d);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstDish;
    }

    @Override
    public void add(Dish d) {
        if(DConnection.executeUpdate("{call addDish(?,?,?,?)}", d.getName(),d.getPrice(),d.isStatus(),d.getMenuId())==0){
            JOptionPane.showMessageDialog(null, "Thêm mới thất bại, tên món ăn đã tồn tại");
        }else{
            JOptionPane.showMessageDialog(null, "Thêm Mới Thành Công");
        }
    }

    @Override
    public void edit(Dish d) {
        if(DConnection.executeUpdate("{call editDish(?,?,?,?,?)}", d.getId(),d.getName(),d.getPrice(),d.isStatus(),d.getMenuId())==0){
            JOptionPane.showMessageDialog(null, "Chỉnh sửa thất bại");
        }else{
            JOptionPane.showMessageDialog(null, "Chỉnh Sửa Thành Công");
        }
    }

    @Override
    public void remove(int id) {
        if(DConnection.executeUpdate("{call removeDish(?)}", id)==0){
            JOptionPane.showMessageDialog(null, "Xóa Thất Bại");
        }else{
            JOptionPane.showMessageDialog(null, "Xóa Thành Công");
        }
    }

    @Override
    public Dish find(int id) {
        ResultSet rs = DConnection.executeQuery("{call findDish(?)}",id);
        try {
            while(rs.next()){
                Dish d = new Dish();
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
