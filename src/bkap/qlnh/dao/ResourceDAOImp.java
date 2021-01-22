/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import bkap.qlnh.util.DConnection;
import javax.swing.JOptionPane;

/**
 *
 * @author ntan2
 */
public class ResourceDAOImp implements ResourceDAO {

    @Override
    public List<Resource> lstResource(String name) {
        List<Resource> lstResource = new ArrayList<>();
        ResultSet rs = DConnection.executeQuery("{call getAllResource()}");
        if (name != null) {
            rs = DConnection.executeQuery("{call findResouceByName(?)}", name);
        }
        try {
            while (rs.next()) {
                Resource r = new Resource();
                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                r.setQuantity(rs.getInt("quantity"));
                r.setSupplier(rs.getString("supplier"));
                r.setDate(rs.getDate("date"));
                r.setStatus(rs.getBoolean("status"));
                lstResource.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstResource;
    }

    @Override
    public void add(Resource r) {
        if (DConnection.executeUpdate("{call addResource(?,?,?,?,?)}", r.getName(), r.getQuantity(), r.getSupplier(), r.getDate(), r.isStatus())==0){
            JOptionPane.showMessageDialog(null, "Thêm mới thất bại");
        }else{
            JOptionPane.showMessageDialog(null, "Thêm mới thành công");
        }
    }

    @Override
    public void edit(Resource r) {
        if(DConnection.executeUpdate("{call editResource(?,?,?,?,?,?)}", r.getId(), r.getName(), r.getQuantity(), r.getSupplier(), r.getDate(), r.isStatus())==0){
            JOptionPane.showMessageDialog(null, "Chỉnh sửa thất bại");
        }else{
            JOptionPane.showMessageDialog(null, "Chỉnh sửa thành công");
        }
    }

    @Override
    public void remove(int id) {
        if(DConnection.executeUpdate("{call removeResource(?)}", id)==0){
            JOptionPane.showMessageDialog(null, "Xóa thất bại");
        }else{
            JOptionPane.showMessageDialog(null, "Xóa thành công");
        }
    }

    @Override
    public Resource find(int id) {
        ResultSet rs = DConnection.executeQuery("{call findResource(?)}", id);
        try {
            while (rs.next()) {
                Resource r = new Resource();
                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                r.setQuantity(rs.getInt("quantity"));
                r.setSupplier(rs.getString("supplier"));
                r.setDate(rs.getDate("date"));
                r.setStatus(rs.getBoolean("status"));
                return r;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
