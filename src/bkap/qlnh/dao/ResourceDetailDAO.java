/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.ResourceDetail;
import java.util.List;

/**
 *
 * @author ntan2
 */
public interface ResourceDetailDAO {
    List<ResourceDetail> lstResourceDetail();
    void add(ResourceDetail rd);
    void edit(ResourceDetail rd);
    void remove(int id);
    ResourceDetail find(int id);
}
