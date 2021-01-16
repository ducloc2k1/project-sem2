/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.Role;
import java.util.List;

/**
 *
 * @author ntan2
 */
public interface RoleDAO {
    List<Role> lstRole();
    void add(Role r);
    void edit(Role r);
    void remove(int id);
    Role find(int id);
}
