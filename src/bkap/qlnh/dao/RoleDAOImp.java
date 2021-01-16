/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.Role;
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
public class RoleDAOImp implements RoleDAO{

    @Override
    public List<Role> lstRole() {
        List<Role> lstRole = new ArrayList<>();
        ResultSet rs = DConnection.executeQuery("{call getAllRole()}");
        try {
            while(rs.next()){
                Role r = new Role();
                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                lstRole.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstRole;
    }

    @Override
    public void add(Role r) {
        DConnection.executeUpdate("{call addRole(?)}", r.getName());
    }

    @Override
    public void edit(Role r) {
        DConnection.executeUpdate("{call editRole(?,?)}", r.getId(),r.getName());
    }

    @Override
    public void remove(int id) {
        DConnection.executeUpdate("{call removeRole(?)}", id);
    }

    @Override
    public Role find(int id) {
        ResultSet rs = DConnection.executeQuery("{call findRole(?)}", id);
        try {
            while(rs.next()){
                Role r = new Role();
                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                return r;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
