/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.Employee;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import bkap.qlnh.util.DConnection;
import java.sql.Date;

/**
 *
 * @author ntan2
 */
public class EmployeeDAOImp implements EmployeeDAO {

    @Override
    public Employee getSingleEmployee(String gmail, String pass) {
        try {
            ResultSet rs = DConnection.executeQuery("{call getSingleEmployee(?,?)}", gmail, pass);
            if (rs.next()) {
                Employee employee = new Employee(rs.getInt("id"), rs.getString("name"), rs.getInt("age"),
                        rs.getString("email"), rs.getString("phone"),
                        rs.getString("address"), rs.getInt("roleId"),
                        new Date(rs.getDate("startDate").getTime()),
                        rs.getFloat("workTime"), rs.getFloat("rate"),
                        rs.getFloat("salary"), rs.getString("password"),
                        rs.getBoolean("status"));
                return employee;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Employee> lstEmployee(String name) {

        List<Employee> lstEmployee = new ArrayList<>();
        ResultSet rs = DConnection.executeQuery("{call getAllEmployee()}");
        if (name != null) {
            rs = DConnection.executeQuery("{call findEmpByName(?)}", name);
        }
        try {
            while (rs.next()) {
                Employee e = new Employee();
                e.setId(rs.getInt("id"));
                e.setName(rs.getString("name"));
                e.setAge(rs.getInt("age"));
                e.setEmail(rs.getString("email"));
                e.setPhone(rs.getString("phone"));
                e.setAddress(rs.getString("address"));
                e.setRoleId(rs.getInt("roleId"));
                e.setStartDate(rs.getDate("startDate"));
                e.setWorkTime(rs.getFloat("workTime"));
                e.setRate(rs.getFloat("rate"));
                e.setSalary(rs.getFloat("salary"));
                e.setStatus(rs.getBoolean("status"));
                e.setPassword(rs.getString("password"));
                lstEmployee.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstEmployee;
    }

    @Override
    public void add(Employee e) {
        DConnection.executeUpdate("{call addEmployee(?,?,?,?,?,?,?,?,?,?,?,?)}", e.getName(), e.getAge(), e.getEmail(), e.getPhone(), e.getAddress(), e.getRoleId(), e.getStartDate(), e.getWorkTime(), e.getRate(), e.getSalary(), e.isStatus(), e.getPassword());
    }

    @Override
    public void edit(Employee e) {
        DConnection.executeUpdate("{call editEmployee(?,?,?,?,?,?,?,?,?,?,?,?,?)}", e.getId(), e.getName(), e.getAge(),
                e.getEmail(), e.getPhone(),
                e.getAddress(), e.getRoleId(),
                e.getStartDate(), e.getWorkTime(),
                e.getRate(), e.getSalary(), e.isStatus(), e.getPassword());
    }

    @Override
    public void remove(int id) {
        DConnection.executeUpdate("{call removeEmployee(?)}", id);
    }

    @Override
    public Employee find(int id) {
        ResultSet rs = DConnection.executeQuery("{call findEmployee(?)}", id);
        try {
            while (rs.next()) {
                Employee e = new Employee();
                e.setId(rs.getInt("id"));
                e.setName(rs.getString("name"));
                e.setAge(rs.getInt("age"));
                e.setEmail(rs.getString("email"));
                e.setPhone(rs.getString("phone"));
                e.setAddress(rs.getString("address"));
                e.setRoleId(rs.getInt("roleId"));
                e.setStartDate(rs.getDate("startDate"));
                e.setWorkTime(rs.getFloat("workTime"));
                e.setRate(rs.getFloat("rate"));
                e.setSalary(rs.getFloat("salary"));
                e.setStatus(rs.getBoolean("status"));
                e.setPassword(rs.getString("password"));
                return e;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
