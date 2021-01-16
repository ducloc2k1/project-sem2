/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.ResourceDetail;
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
public class ResourceDetailsDAOImp implements ResourceDetailDAO{

    @Override
    public List<ResourceDetail> lstResourceDetail() {
        List<ResourceDetail> lstResourceDetail = new ArrayList<>();
        ResultSet rs = DConnection.executeQuery("{call getAllResourceDetail()}");
        try {
            while(rs.next()){
                ResourceDetail rd = new ResourceDetail();
                rd.setId(rs.getInt("id"));
                rd.setDishId(rs.getInt("dishId"));
                rd.setResourceId(rs.getInt("resourceId"));
                lstResourceDetail.add(rd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstResourceDetail;
    }

    @Override
    public void add(ResourceDetail rd) {
        DConnection.executeUpdate("{call addResourceDetail(?,?)}", rd.getDishId(),rd.getResourceId());
    }

    @Override
    public void edit(ResourceDetail rd) {
        DConnection.executeUpdate("{call editResourceDetail(?,?,?)}", rd.getId(),rd.getDishId(),rd.getResourceId());
    }

    @Override
    public void remove(int id) {
        DConnection.executeUpdate("{call removeResourceDetail(?)}", id);
    }

    @Override
    public ResourceDetail find(int id) {
         ResultSet rs = DConnection.executeQuery("{call findTable(?)}", id);
        try {
            while(rs.next()){
                ResourceDetail rd = new ResourceDetail();
                rd.setId(rs.getInt("id"));
                rd.setDishId(rs.getInt("dishId"));
                rd.setResourceId(rs.getInt("resourceId"));
                return rd;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
