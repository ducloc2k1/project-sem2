/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.Position;
import java.util.List;

/**
 *
 * @author ntan2
 */
public interface PositionDAO {
    List<Position> lstPos();
    void add(Position p);
    void edit(Position p);
    void remove(int id);
    Position find(int id);
}
