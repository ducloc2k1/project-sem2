/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.entities;

import java.sql.Timestamp;

/**
 *
 * @author ntan2
 */
public class Bill {
    private int id;
    private boolean status;
    private float totalCost;
    private Timestamp stareDate;
    private Timestamp endDate;
    private int paymentMethodId;
    private int idTable;
    private int numberCustomer;
    private String nameCustomer;
    private int idEmployee;
    private String note;
    
    public Bill() {
    }

    public Bill(int id, boolean status, float totalCost, Timestamp stareDate, Timestamp endDate, int paymentMethodId, int idTable, int numberCustomer, String nameCustomer, int idEmployee, String note) {
        this.id = id;
        this.status = status;
        this.totalCost = totalCost;
        this.stareDate = stareDate;
        this.endDate = endDate;
        this.paymentMethodId = paymentMethodId;
        this.idTable = idTable;
        this.numberCustomer = numberCustomer;
        this.nameCustomer = nameCustomer;
        this.idEmployee = idEmployee;
        this.note = note;
    }

    public Bill(boolean status, float totalCost, Timestamp stareDate, Timestamp endDate, int paymentMethodId, int idTable, int numberCustomer, String nameCustomer, int idEmployee, String note) {
        this.status = status;
        this.totalCost = totalCost;
        this.stareDate = stareDate;
        this.endDate = endDate;
        this.paymentMethodId = paymentMethodId;
        this.idTable = idTable;
        this.numberCustomer = numberCustomer;
        this.nameCustomer = nameCustomer;
        this.idEmployee = idEmployee;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public Timestamp getStareDate() {
        return stareDate;
    }

    public void setStareDate(Timestamp stareDate) {
        this.stareDate = stareDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public int getIdTable() {
        return idTable;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    public int getNumberCustomer() {
        return numberCustomer;
    }

    public void setNumberCustomer(int numberCustomer) {
        this.numberCustomer = numberCustomer;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    
    
    

}