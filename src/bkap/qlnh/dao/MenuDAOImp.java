/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.Menu;
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
public class MenuDAOImp implements MenuDAO{

    @Override
    public List<Menu> lstMenu() {
        List<Menu> lstMenu = new ArrayList<>();
        ResultSet rs = DConnection.executeQuery("{call getAllMenu()}");
        try {
            while(rs.next()){
                Menu m = new Menu();
                m.setId(rs.getInt("id"));
                m.setName(rs.getString("name"));
                m.setStatus(rs.getBoolean("status"));
                lstMenu.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstMenu;
    }

    @Override
    public void add(Menu m) {
        if( DConnection.executeUpdate("{call addMenu(?,?)}", m.getName(),m.isStatus())==0){
            JOptionPane.showMessageDialog(null, "Thêm Mới Thất Bại");
        }else{
            JOptionPane.showMessageDialog(null, "Thêm Mới Thành Công");
        }
       
    }

    @Override
    public void edit(Menu m) {
        
        if( DConnection.executeUpdate("{call editMenu(?,?,?)}", m.getId(),m.getName(),m.isStatus())==0){
            JOptionPane.showMessageDialog(null, "Chỉnh Sửa Thất Bại");
        }else{
            JOptionPane.showMessageDialog(null, "Chỉnh Sửa Thành Công");
        }
    }

    @Override
    public void remove(int id) {
        if(DConnection.executeUpdate("{call removeMenu(?)}", id)==0){
            JOptionPane.showMessageDialog(null, "Xóa thất bại");
        }else{
            JOptionPane.showMessageDialog(null, "Xóa thành công");
        }
        
    }

    @Override
    public Menu find(int id) {
        ResultSet rs = DConnection.executeQuery("{call findMenu(?)}",id);
        try {
            while(rs.next()){
                Menu m = new Menu();
                m.setId(rs.getInt("id"));
                m.setName(rs.getString("name"));
                m.setStatus(rs.getBoolean("status"));
                return m;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
