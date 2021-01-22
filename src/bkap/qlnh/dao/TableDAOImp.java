/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.Table;
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
public class TableDAOImp implements TableDAO{

    @Override
    public List<Table> lstTable(String name) {
        List<Table> lstTable = new ArrayList<>();
        ResultSet rs = DConnection.executeQuery("{call getAllTable()}");
        if(name!=null){
            rs = DConnection.executeQuery("{call findTableByName(?)}", name);
        }
        try {
            while(rs.next()){
                Table t = new Table();
                t.setId(rs.getInt("id"));
                t.setName(rs.getString("name"));
                t.setIdPosition(rs.getInt("idPosition"));
                t.setNamePosition(rs.getString("pName"));
                t.setStatus(rs.getBoolean("status"));
                lstTable.add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstTable;
    }

    @Override
    public void add(Table t) {
        if(DConnection.executeUpdate("{call addTable(?,?,?)}", t.getName(),t.getIdPosition(),t.isStatus())==0){
            JOptionPane.showMessageDialog(null, "Thêm mới thất bại");
        }else{
            JOptionPane.showMessageDialog(null, "Thêm mới thành công");
        }
    }

    @Override
    public void edit(Table t) {
        DConnection.executeUpdate("{call editTable(?,?,?,?)}", t.getId(),t.getName(),t.getIdPosition(),t.isStatus());
    }

    @Override
    public void remove(int id) {
        if(DConnection.executeUpdate("{call removeTable(?)}", id)==0){
            JOptionPane.showMessageDialog(null, "Xóa thất bại");
        }else{
            JOptionPane.showMessageDialog(null, "Xóa thành công");
        }
    }

    @Override
    public Table find(int id) {
        ResultSet rs = DConnection.executeQuery("{call findTable(?)}", id);
        try {
            while(rs.next()){
                Table t = new Table();
                t.setId(rs.getInt("id"));
                t.setName(rs.getString("name"));
                t.setIdPosition(rs.getInt("idPosition"));
                
                t.setStatus(rs.getBoolean("status"));
                return t;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
