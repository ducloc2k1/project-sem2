/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.Resource;
import java.util.List;

/**
 *
 * @author ntan2
 */
public interface ResourceDAO {
    List<Resource> lstResource(String name);
    void add(Resource r);
    void edit(Resource r);
    void remove(int id);
    Resource find(int id);
}
