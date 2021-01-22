/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.Position;
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
public class PositionDAOImp implements PositionDAO{

    @Override
    public List<Position> lstPos() {
        List<Position> lstPos = new ArrayList<>();
        ResultSet rs = DConnection.executeQuery("{call getAllPosition}");
        try {
            while(rs.next()){ 
                Position p = new Position();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                lstPos.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PositionDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstPos;
    }

    @Override
    public void add(Position p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void edit(Position p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Position find(int id) {
        ResultSet rs = DConnection.executeQuery("{call findPosition(?)}",id);
        try {
            while(rs.next()){ 
                Position p = new Position();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                return p;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PositionDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
