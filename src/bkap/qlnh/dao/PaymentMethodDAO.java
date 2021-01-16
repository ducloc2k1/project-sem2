/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.dao;

import bkap.qlnh.entities.PaymentMethod;
import java.util.List;

/**
 *
 * @author ntan2
 */
public interface PaymentMethodDAO {
    List<PaymentMethod> lstPaymentMethod();
    void add(PaymentMethod pm);
    void edit(PaymentMethod pm);
    void remove(int id);
    PaymentMethod find(int id);
}
