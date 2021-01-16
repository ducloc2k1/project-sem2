/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author DELL
 */
public class LabelTable extends javax.swing.JLabel{
    private int idTable;
    private int idBill;

    public LabelTable() {
    }

    public LabelTable(int idTable, int idBill) {
        this.idTable = idTable;
        this.idBill = idBill;
    }
    
    public LabelTable(int idTable) {
        this.idTable = idTable;
    }

    public int getIdTable() {
        return idTable;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    public int getIdBill() {
        return idBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }

    
   
}
