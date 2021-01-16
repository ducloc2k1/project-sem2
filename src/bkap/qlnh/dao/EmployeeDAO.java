/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.Employee;
import java.util.List;

/**
 *
 * @author ntan2
 */
public interface EmployeeDAO {
    List<Employee> lstEmployee(String name);
    void add(Employee e);
    void edit(Employee e);
    void remove(int id);
    Employee find(int id);
    public Employee getSingleEmployee(String gmail, String pass);
}
