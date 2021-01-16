/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.entities;

import com.toedter.calendar.JDateChooser;
import java.sql.Date;

/**
 *
 * @author ntan2
 */
public class Employee {

    private int id;
    private String name;
    private int age;
    private String email;
    private String phone;
    private String address;
    private int roleId;
    private Date startDate;
    private float workTime;
    private float rate;
    private float salary;
    private String password;
    private boolean status;
    private String roleName;

    public Employee() {
    }

    public Employee(int id, String name, int age, String email, String phone, String address, int roleId, Date startDate, float workTime, float rate, float salary, String password, boolean status) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.roleId = roleId;
        this.startDate = startDate;
        this.workTime = workTime;
        this.rate = rate;
        this.salary = salary;
        this.password = password;
        this.status = status;
    }

    public Employee(int id, String name, int age, String email, String phone, String address, int roleId, Date startDate, float workTime, float rate, float salary, String password, boolean status, String roleName) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.roleId = roleId;
        this.startDate = startDate;
        this.workTime = workTime;
        this.rate = rate;
        this.salary = salary;
        this.password = password;
        this.status = status;
        this.roleName = roleName;
    }
    

    public Employee(String name, int age, String email, String phone, String address, int roleId, Date startDate, float workTime, float rate, float salary, String password, boolean status) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.roleId = roleId;
        this.startDate = startDate;
        this.workTime = workTime;
        this.rate = rate;
        this.salary = salary;
        this.password = password;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public float getWorkTime() {
        return workTime;
    }

    public void setWorkTime(float workTime) {
        this.workTime = workTime;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
}
