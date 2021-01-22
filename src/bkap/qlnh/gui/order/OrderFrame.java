/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bkap.qlnh.gui.order;

import bkap.qlnh.dao.BillDAOImp;
import bkap.qlnh.dao.BillDetailDAOImp;
import bkap.qlnh.dao.DishDAOImp;
import bkap.qlnh.dao.EmployeeDAOImp;
import bkap.qlnh.dao.MenuDAOImp;
import bkap.qlnh.dao.TableDAO;
import bkap.qlnh.dao.TableDAOImp;
import bkap.qlnh.entities.Bill;
import bkap.qlnh.entities.BillDetail;
import bkap.qlnh.entities.Dish;
import bkap.qlnh.entities.Employee;
import bkap.qlnh.entities.Menu;
import bkap.qlnh.entities.Table;
import bkap.qlnh.gui.LabelTable;
import bkap.qlnh.gui.mainframe.IFUpdate;
import bkap.qlnh.util.DConnection;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hai Phuong
 */
public class OrderFrame extends javax.swing.JInternalFrame implements IFUpdate.CallbackUpdate {

    BillDAOImp billDAOImp = new BillDAOImp();
    TableDAOImp tableDAOImp = new TableDAOImp();
    int idBill;
    Bill bill = new Bill();

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    /**
     * Creates new form MainFrame
     */
    public OrderFrame() {

        initComponents();
        loadTable();
        loadDishList(null, 0);
        loadEmployee();
        loadMenu();
        try {
            //Set the required look and feel
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            //Update the component tree - associate the look and feel with the given frame.
            SwingUtilities.updateComponentTreeUI(this);
        }//end try
        catch (Exception ex) {
            ex.printStackTrace();
        }//end catch
    }

    public void loadTable() {
        jPanelTang1.removeAll();
        jPanelTang2.removeAll();
        TableDAOImp tableDAOImp = new TableDAOImp();
        List<Table> lstTable = tableDAOImp.lstTable(null);
        LabelTable labelTable;
        for (Table table : lstTable) {
            labelTable = new LabelTable(table.getId());
            labelTable.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
            labelTable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            labelTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bkap/qlnh/icon/hot-cup.png"))); // NOI18N
            labelTable.setText(table.getName());
            labelTable.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            labelTable.setMaximumSize(new java.awt.Dimension(50, 53));
            labelTable.setMinimumSize(new java.awt.Dimension(50, 53));
            labelTable.setPreferredSize(new java.awt.Dimension(100, 120));
            labelTable.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            labelTable.setOpaque(true);
            Color before = labelTable.getBackground();
            if (table.isStatus()) {
                labelTable.setOpaque(true);
                labelTable.setForeground(Color.red);
            }
            if (table.getIdPosition() == 1) {
                jPanelTang1.add(labelTable);
            } else {
                jPanelTang2.add(labelTable);
            }
            labelTable.addMouseListener(new MouseAdapter() {

                public void mouseClicked(MouseEvent e) {
                    Component com = e.getComponent();
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        addBill.setEnabled(true);
                        ((LabelTable) com).add(popupMenu);
                        popupMenu.show(com, e.getX(), e.getY());
                    }
                    OrderFrame.this.idBill = 1;
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    e.getComponent().setBackground(new Color(51, 255, 153, 255));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    e.getComponent().setBackground(before);
                }

            });
        }

    }

    private void loadDishList(String name, int idMenu) {
        DefaultTableModel model = (DefaultTableModel) tblDish.getModel();
//        tblDish.removeColumn(tblDish.getColumnModel().getColumn(0));
        model.setRowCount(0);
        if (idMenu == 0) {
            DishDAOImp dishDAO = new DishDAOImp();
            List<Dish> lstDish = new ArrayList<>(dishDAO.lstDish(name));
            for (Dish d : lstDish) {
                String status = "";
                if (d.isStatus()) {
                    status = "Còn";
                } else {
                    status = "Hết";
                }
                model.addRow(new Object[]{d.getId(), d.getName(), d.getMenuName(), d.getPrice()});
            }
            tblDish.setModel(model);
        } else if (idMenu > 0) {
            ResultSet rs = DConnection.executeQuery("SELECT \n"
                    + "		   tblDish.id,\n"
                    + "		   tblDish.name,\n"
                    + "		   tblDish.price,\n"
                    + "		   tblDish.status,\n"
                    + "		   tblMenu.name as 'menuName',\n"
                    + "		   tblMenu.id as 'menuId'\n"
                    + "	FROM tblDish JOIN tblMenu ON tblDish.menuId = tblMenu.id "
                    + "WHERE menuId = " + idMenu);
            try {
                while (rs.next()) {
                    model.addRow(new Object[]{rs.getInt("id"),
                        rs.getString("name"), rs.getString("menuName"),
                        rs.getFloat("price")});
                }
            } catch (SQLException ex) {
                Logger.getLogger(OrderFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void loadEmployee() {
        EmployeeDAOImp employeeDAOImp = new EmployeeDAOImp();
        for (Employee employee : employeeDAOImp.lstEmployee(null)) {
            cbxEmployee.addItem(employee);
        }
    }

    public void loadBill() {
        EmployeeDAOImp employeeDAOImp = new EmployeeDAOImp();
        if (this.bill != null) {
            ResultSet rs = DConnection.executeQuery("SELECT \n"
                    + "	t.name as tableName,\n"
                    + "	position.name as positionName\n"
                    + "FROM\n"
                    + "	tblTable t JOIN tblPosition position \n"
                    + "	ON  t.idPosition = position.id\n"
                    + "WHERE t.id = " + this.bill.getIdTable());
            try {
                rs.next();
                labelPosition.setText(rs.getString("positionName") + " - " + rs.getString("tableName"));
            } catch (SQLException ex) {
                Logger.getLogger(OrderFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            startDate.setText(formatter.format(bill.getStareDate()));
            numberCustomer.setValue(bill.getNumberCustomer());
            customerName.setText(bill.getNameCustomer());
            Employee e = employeeDAOImp.find(bill.getIdEmployee());
            cbxEmployee.getModel().setSelectedItem(e);
        }
    }

    private void loadMenu() {
        MenuDAOImp menuDAOImp = new MenuDAOImp();
        for (Menu menu : menuDAOImp.lstMenu()) {
            cbxMenu.addItem(menu);
        }

    }

    private void loadDetailBill() {
        DefaultTableModel model = (DefaultTableModel) tblDetailBill.getModel();
        double s = 0;
        model.setRowCount(0);
        BillDetailDAOImp billDetailDAOImp = new BillDetailDAOImp();
        ResultSet rs = DConnection.executeQuery("SELECT \n"
                + "	dish.id,\n"
                + "	dish.name,\n"
                + "	detail.dishQuantity,\n"
                + "	dish.price\n"
                + "FROM tblDish dish JOIN tblBillDetail detail\n"
                + "ON dish.id = detail.dishId\n"
                + "WHERE detail.billId = " + this.bill.getId());
        try {
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id"), rs.getString("name"), rs.getInt("dishQuantity"), rs.getFloat("price")});
                s += rs.getFloat("price") * rs.getInt("dishQuantity");
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtProvisional.setText(String.valueOf(s));
        double provisional = Double.valueOf(txtProvisional.getText());
        if (txtDiscount.getText().length() > 0) {
            double discount = Double.valueOf(txtDiscount.getText());
            txtTotal.setText(String.valueOf(provisional - discount));
        } else {
            txtTotal.setText(String.valueOf(txtProvisional.getText()));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupMenu = new javax.swing.JPopupMenu();
        addBill = new javax.swing.JMenuItem();
        editBill = new javax.swing.JMenuItem();
        orderDish = new javax.swing.JPopupMenu();
        addFood = new javax.swing.JMenuItem();
        jPanel2 = new javax.swing.JPanel();
        cbxMenu = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDish = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        labelPosition = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        startDate = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        numberCustomer = new javax.swing.JSpinner();
        jLabel26 = new javax.swing.JLabel();
        customerName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        note = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        cbxEmployee = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        txtTotal = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtProvisional = new javax.swing.JTextField();
        txtDiscount = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDetailBill = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanelTang1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jPanelTang2 = new javax.swing.JPanel();

        addBill.setText("Thêm");
        addBill.setToolTipText("");
        addBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBillActionPerformed(evt);
            }
        });
        popupMenu.add(addBill);

        editBill.setText("Sửa");
        editBill.setToolTipText("");
        editBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBillActionPerformed(evt);
            }
        });
        popupMenu.add(editBill);

        addFood.setText("Thêm");
        addFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFoodActionPerformed(evt);
            }
        });
        orderDish.add(addFood);

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cbxMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbxMenuMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cbxMenuMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cbxMenuMouseReleased(evt);
            }
        });
        cbxMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxMenuActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Thực đơn");

        tblDish.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Mã", "Tên món ăn", "Danh mục", "Giá bán"
            }
        ));
        tblDish.setSelectionBackground(new java.awt.Color(227, 162, 97));
        tblDish.setShowHorizontalLines(false);
        tblDish.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblDishMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblDish);

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bkap/qlnh/icon/search_24px.png"))); // NOI18N
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cbxMenu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel20))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbxMenu)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(447, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.LINE_START);

        labelPosition.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        labelPosition.setForeground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 0, 0));
        jLabel3.setText("Hóa đơn bán hàng");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 393, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 622, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
        );

        jLabel4.setText("Giờ vào:");

        startDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startDateActionPerformed(evt);
            }
        });

        jLabel5.setText("Khách:");

        numberCustomer.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel26.setText("Khách hàng:");

        customerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerNameActionPerformed(evt);
            }
        });

        jLabel6.setText("Ghi chú:");

        note.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noteActionPerformed(evt);
            }
        });

        jLabel21.setText("Nhân viên:");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(49, 49, 49)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(startDate, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(note))
                .addGap(65, 65, 65)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(numberCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(cbxEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(customerName, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(numberCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerName, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(note, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(3, 3, 3)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(241, 241, 241)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 58, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        jLabel22.setBackground(new java.awt.Color(255, 153, 51));
        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bkap/qlnh/icon/checkmark_30px.png"))); // NOI18N
        jLabel22.setText("THANH TOÁN HÓA ĐƠN");
        jLabel22.setOpaque(true);
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });

        jLabel15.setBackground(new java.awt.Color(102, 255, 0));
        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bkap/qlnh/icon/change_24px.png"))); // NOI18N
        jLabel15.setText("Thay đổi số lượng");
        jLabel15.setOpaque(true);
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });

        jLabel17.setBackground(new java.awt.Color(255, 51, 51));
        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bkap/qlnh/icon/delete_24px.png"))); // NOI18N
        jLabel17.setText("Xóa thực đơn");
        jLabel17.setOpaque(true);
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        txtTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTotal.setForeground(new java.awt.Color(102, 0, 0));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel16.setText("Tổng tiền: ");

        jLabel12.setBackground(new java.awt.Color(128, 51, 0));
        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bkap/qlnh/icon/print_24px.png"))); // NOI18N
        jLabel12.setText("IN TẠM TÍNH");
        jLabel12.setOpaque(true);

        jLabel10.setBackground(new java.awt.Color(0, 204, 255));
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bkap/qlnh/icon/print_24px.png"))); // NOI18N
        jLabel10.setText("IN CHẾ BIẾN");
        jLabel10.setOpaque(true);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bkap/qlnh/icon/save_30px.png"))); // NOI18N
        jButton1.setText("LƯU HÓA ĐƠN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel18.setText("THANH TOÁN: ");

        txtProvisional.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtProvisional.setForeground(new java.awt.Color(102, 0, 0));
        txtProvisional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProvisionalActionPerformed(evt);
            }
        });

        txtDiscount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDiscount.setForeground(new java.awt.Color(102, 0, 0));
        txtDiscount.setMinimumSize(new java.awt.Dimension(131, 23));
        txtDiscount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDiscountFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiscountFocusLost(evt);
            }
        });
        txtDiscount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiscountActionPerformed(evt);
            }
        });
        txtDiscount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDiscountKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDiscountKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDiscountKeyTyped(evt);
            }
        });

        jLabel19.setBackground(new java.awt.Color(0, 0, 0));
        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bkap/qlnh/icon/discount_24px.png"))); // NOI18N
        jLabel19.setText("Giảm giá:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(txtProvisional, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProvisional, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bkap/qlnh/icon/cancel_subscription_30px.png"))); // NOI18N
        jButton2.setText("HỦY ĐƠN HÀNG");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(47, 47, 47)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(34, 34, 34))
        );

        tblDetailBill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Tên thực đơn", "Số lượng", "Giá bán"
            }
        ));
        jScrollPane3.setViewportView(tblDetailBill);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanelTang1.setBorder(javax.swing.BorderFactory.createTitledBorder("Tầng 1"));
        jPanelTang1.setMaximumSize(new java.awt.Dimension(400, 400));
        jPanelTang1.setMinimumSize(new java.awt.Dimension(400, 400));
        jPanelTang1.setPreferredSize(new java.awt.Dimension(390, 410));
        jScrollPane4.setViewportView(jPanelTang1);

        jPanelTang2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tầng 2"));
        jPanelTang2.setMaximumSize(new java.awt.Dimension(450, 550));
        jPanelTang2.setMinimumSize(new java.awt.Dimension(450, 400));
        jPanelTang2.setPreferredSize(new java.awt.Dimension(450, 410));
        jScrollPane5.setViewportView(jPanelTang2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBillActionPerformed
        // TODO add your handling code here:
        BillDAOImp billDAOImp = new BillDAOImp();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        TableDAOImp tableDAO = new TableDAOImp();
        Component component = (Component) evt.getSource();
        Component comTable = ((JPopupMenu) component.getParent()).getInvoker();
        int idBill = ((LabelTable) comTable).getIdBill();
        int idTable = ((LabelTable) comTable).getIdTable();
        Table table = tableDAO.find(idTable);
        if (idBill != 0) {

        } else if (!table.isStatus()) {
            Timestamp timeStap = new Timestamp(new java.util.Date().getTime());
            Bill bill;
            bill = new Bill(false, 0, timeStap, timeStap, 1, idTable, 1, "", 1, "");
            billDAOImp.add(bill);
            ResultSet rs = DConnection.executeQuery("SELECT IDENT_CURRENT('tblBill') AS 'idBill'");
            try {
                rs.next();
                idBill = rs.getInt("idBill");
                ((LabelTable) comTable).setIdBill(idBill);
                bill.setId(idBill);
            } catch (SQLException ex) {
                Logger.getLogger(OrderFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.bill = bill;
            loadDetailBill();
            table.setStatus(true);
            tableDAOImp.edit(table);
            loadBill();
            loadTable();
        }
    }//GEN-LAST:event_addBillActionPerformed

    private void editBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBillActionPerformed
        // TODO add your handling code here:
        Component component = (Component) evt.getSource();
        Component comTable = ((JPopupMenu) component.getParent()).getInvoker();
        int idTable = ((LabelTable) comTable).getIdTable();
        ((LabelTable) comTable).setIdBill(billDAOImp.findBillByTable(idTable).getId());
        int idBill = ((LabelTable) comTable).getIdBill();
        this.bill = billDAOImp.find(idBill);
        loadBill();
        loadDetailBill();
    }//GEN-LAST:event_editBillActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        BillDAOImp billDAOImp = new BillDAOImp();
        Bill bill = this.bill;
        bill.setNumberCustomer((int) numberCustomer.getValue());
        int idEmployee = ((Employee) cbxEmployee.getSelectedItem()).getId();
        bill.setIdEmployee(idEmployee);
        bill.setNameCustomer(customerName.getText());
        bill.setNote(note.getText());
        billDAOImp.edit(bill);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblDishMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDishMouseReleased
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON3 && tblDish.getSelectedRowCount() != 0) {
            orderDish.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tblDishMouseReleased

    private void addFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFoodActionPerformed
        // TODO add your handling code here:
        if (this.bill.id > 0) {
            BillDetail billDetail;
            BillDetailDAOImp billDetailDAOImp = new BillDetailDAOImp();
            int idDish = (int) tblDish.getModel().getValueAt(tblDish.getSelectedRow(), 0);
            float price = (float) tblDish.getModel().getValueAt(tblDish.getSelectedRow(), 3);
            ResultSet rs = DConnection.executeQuery("SELECT * FROM tblBillDetail WHERE dishId = " + idDish + "AND billId = " + this.bill.getId());
            try {
                if (rs.next()) {
                    billDetail = billDetailDAOImp.find(rs.getInt("id"));
                    billDetail.setDishQuantity(billDetail.getDishQuantity() + 1);
                    billDetail.setTotalPrice(billDetail.getDishQuantity() * price);
                    billDetailDAOImp.edit(billDetail);
                } else {
                    billDetail = new BillDetail();
                    billDetail.setDishId(idDish);
                    billDetail.setDishQuantity(1);
                    billDetail.setBillId(bill.getId());
                    billDetail.setTotalPrice(price);
                    billDetailDAOImp.add(billDetail);
                }
            } catch (SQLException ex) {
                Logger.getLogger(OrderFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            loadDetailBill();
        } else {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn bàn !!!");
        }
    }//GEN-LAST:event_addFoodActionPerformed

    private void noteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noteActionPerformed

    private void customerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_customerNameActionPerformed

    private void startDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_startDateActionPerformed

    private void cbxMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxMenuActionPerformed
        // TODO add your handling code here:
//        System.out.println("đã chọn");
    }//GEN-LAST:event_cbxMenuActionPerformed

    private void cbxMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbxMenuMouseClicked
    }//GEN-LAST:event_cbxMenuMouseClicked

    private void cbxMenuMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbxMenuMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxMenuMouseReleased

    private void cbxMenuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbxMenuMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxMenuMouseExited

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        // TODO add your handling code here:
        int idMenu = ((Menu) cbxMenu.getSelectedItem()).getId();
        loadDishList(null, idMenu);
    }//GEN-LAST:event_jLabel20MouseClicked

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        // TODO add your handling code here:
        System.out.println("vào đây");
        int line = tblDetailBill.getRowCount();
        try {
            SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
            Date now = new Date();
            Writer bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Bill" + this.bill.getId(), true), StandardCharsets.UTF_8));
            bw.write("\t\t\tTân Nguyễn Restaurant\r\n\r\n");
            bw.write("\t\t\tSĐT: 01212692802\r\n\r\n");
            bw.write("\t\t\tHÓA ĐƠN BÁN HÀNG\r\n\r\n");
            bw.write("Mã hóa đơn: " + bill.getId() + "\r\n");
            bw.write("Thời gian: " + ft.format(now) + "\r\n");
            bw.write("NHÂN VIÊN: " + ((Employee) cbxEmployee.getSelectedItem()).toString() + "\r\n");
            bw.write("------------------------------------------------------------\r\n");
            bw.write(String.format("%-20s %-10s %-15s %-10s \n", "Tên", "Số lượng", "Đơn giá", "Thành tiền"));
            bw.write("-----------------------------------------------------------\r\n");
            //Ghi sản phẩm
            for (int i = 0; i < line; i++) {
                int id = bill.getId();
                String name = customerName.getText();
                String nameDish = (tblDetailBill.getValueAt(i, 1).toString());
                Double price = Double.valueOf(tblDetailBill.getValueAt(i, 3).toString());
                Double quantity = Double.valueOf(tblDetailBill.getValueAt(i, 2).toString());
                Double intomoney = price * quantity;
//                bw.write((i + 1) + ". " + name + "\r\n");
                bw.write(String.format("%-20s %-10s %-15s %-10s \n", nameDish, quantity, price, intomoney));
            }
            bw.write("------------------------------------------------------------\r\n");
            bw.write("Tổng cộng:\t" + txtProvisional.getText() + " VNĐ\r\n");
            if (txtDiscount.getText().length() > 0) {
                bw.write("Giảm giá:\t" + txtDiscount.getText() + " VNĐ\r\n");
            } else {
                bw.write("Giảm giá:\t" + 0 + " VNĐ\r\n");
            }
            bw.write("\t\t--------------------------------------------\r\n");
            bw.write("\t\tThành tiền:\t\t\t" + txtTotal.getText() + " VNĐ\r\n");
            bw.write("\t\t--------------------------------------------\r\n");
            bw.write("------------------------------------------------------------\r\n");
            bw.write("------------------------------------------------------------\r\n");
            bw.write("Mật khẩu Wifi: motdentam\r\n");
            bw.write("---------------------CÁM ƠN QUÝ KHÁCH!----------------------");
            bw.close();
            Table table = tableDAOImp.find(bill.getIdTable());
            table.setStatus(false);
            tableDAOImp.edit(table);
            loadTable();
            startDate.setText("");
            numberCustomer.setValue(1);
            txtProvisional.setText("");
            txtDiscount.setText("");
            note.setText("");
            customerName.setText("");
            this.bill.setStatus(true);
            billDAOImp.edit(bill);
            this.bill = new Bill();
            labelPosition.setText("");
            loadDetailBill();
        } catch (Exception e) {

        }
    }//GEN-LAST:event_jLabel22MouseClicked

    private void txtProvisionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProvisionalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProvisionalActionPerformed

    private void txtDiscountFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscountFocusGained
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtDiscountFocusGained

    private void txtDiscountFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscountFocusLost
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtDiscountFocusLost

    private void txtDiscountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiscountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiscountActionPerformed

    private void txtDiscountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountKeyPressed
        // TODO add your handling code here:
        double total = 0;
        if (txtDiscount.getText().length() > 0) {
            total = Double.valueOf(txtProvisional.getText()) - Double.valueOf(txtDiscount.getText());
        }else{
            total = Double.valueOf(Double.valueOf(txtProvisional.getText()) - 0);
        }
        txtTotal.setText(String.valueOf(total));
    }//GEN-LAST:event_txtDiscountKeyPressed

    private void txtDiscountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountKeyReleased
        double total = 0;
        if (txtDiscount.getText().length() > 0) {
            total = Double.valueOf(txtProvisional.getText()) - Double.valueOf(txtDiscount.getText());
        }else{
            total = Double.valueOf(Double.valueOf(txtProvisional.getText()) - 0);
        }
        txtTotal.setText(String.valueOf(total));
    }//GEN-LAST:event_txtDiscountKeyReleased

    private void txtDiscountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiscountKeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        // TODO add your handling code here:
        IFUpdate iFUpdate = new IFUpdate(true, this);
        iFUpdate.setVisible(true);
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        // TODO add your handling code here:
        int idDish = (int) tblDetailBill.getValueAt(tblDetailBill.getSelectedRow(), 0);
        DConnection.executeUpdate("DELETE FROM tblBillDetail WHERE billId = "  + this.bill.id + " AND " + "dishId = " + idDish );
        loadDetailBill();
    }//GEN-LAST:event_jLabel17MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OrderFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrderFrame().setVisible(true);
            }

        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addBill;
    private javax.swing.JMenuItem addFood;
    private javax.swing.JComboBox<Employee> cbxEmployee;
    private javax.swing.JComboBox<Menu> cbxMenu;
    private javax.swing.JTextField customerName;
    private javax.swing.JMenuItem editBill;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelTang1;
    private javax.swing.JPanel jPanelTang2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel labelPosition;
    private javax.swing.JTextField note;
    private javax.swing.JSpinner numberCustomer;
    private javax.swing.JPopupMenu orderDish;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JTextField startDate;
    private javax.swing.JTable tblDetailBill;
    private javax.swing.JTable tblDish;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtProvisional;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

    @Override
    public void doUpdate(int quantity) {
        BillDetailDAOImp billDetailDAOImp = new BillDetailDAOImp();
        DishDAOImp dishDAOImp = new DishDAOImp();
        int idDish = (int) tblDetailBill.getValueAt(tblDetailBill.getSelectedRow(), 0);
        Dish dish = dishDAOImp.find(idDish);
        int n = DConnection.executeUpdate("UPDATE tblBillDetail\n"
                + "SET dishQuantity = \n" + quantity
                + ",totalPrice = " + dish.getPrice()*quantity
                + "WHERE dishId = " + idDish + "AND billId = "
                + this.bill.id);
        loadDetailBill();
    }
}
