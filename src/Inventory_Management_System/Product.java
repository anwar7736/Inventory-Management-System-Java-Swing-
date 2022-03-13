
package Inventory_Management_System;

import java.sql.*;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Product extends javax.swing.JFrame {

    /** Creates new form Product */
    public Product() {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getCategory();
        this.viewAllData();
        sku_code.setEditable(false);
    }
    
    public void viewAllData()
    {
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_management_system", "root", "");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT products.*, categories.category_name FROM products INNER JOIN categories ON products.category_code = categories.category_code");
                while(rs.next())
                {
                    String code = rs.getString("product_code");                    
                    String name = rs.getString("product_name");                       
                    String cat_code = rs.getString("category_code");                      
                    String cat_name = rs.getString("category_name");                     
                   
                    String price = rs.getString("unit_price");                    
                    String tableData[] = {code, name, (cat_code + " " + cat_name ), price};
                    DefaultTableModel tblModel = (DefaultTableModel)dataTable.getModel();
                    tblModel.addRow(tableData);
                }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, e);
                }
        
    }
    
    public void clearForm()
    {
       sku_code.setText("Auto Generated");        
       sku_name.setText("");        
       price.setText("");        
       category.setSelectedItem("");
    }
    
    public void getCategory()
    {
        try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_management_system", "root", "");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM categories");
                while(rs.next())
                {                  
                    String code = rs.getString("category_code");                      
                    String name = rs.getString("category_name");                    
                  
                    category.addItem(code + " " + name);
                }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, e);
                }
    }
    
   public void formValidation(String action)
   {
        String code = sku_code.getText();        
        String name = sku_name.getText();        
        String rate = price.getText();        
        String cat_code = (category.getSelectedItem().toString()).substring(0,6);
        
        if(name.length()==0)
        {
            JOptionPane.showMessageDialog(null, "Product name is required!");
        }
        else if(rate.length()==0)
        {
            JOptionPane.showMessageDialog(null, "Product price is required!");
        }
        else {
            int max = 99999999;
            int min = 11111111;
            long random = Math.round(Math.random()*(max - min + 1) + min);
            String product_code = String.valueOf(random);
            if(action=="insert")
            {
                try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_management_system", "root", "");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM products WHERE product_name='"+name+"'");                
                if(rs.next())
                {
                    JOptionPane.showMessageDialog(null, "Product name already exists!");
                }
                else{
                String query1 = "INSERT INTO products(product_code, category_code,product_name, unit_price) VALUES(?,?,?,?)";
                PreparedStatement stmt = con.prepareStatement(query1);
                stmt.setString(1, product_code);                  
                stmt.setString(2, cat_code);                
                stmt.setString(3, name);                  
                stmt.setString(4, rate);               
                stmt.executeUpdate();
                
                String query2 = "INSERT INTO current_stock(product_code)VALUES('"+product_code+"')";
                Statement stmt2 = con.createStatement();
                stmt2.executeUpdate(query2);           
                DefaultTableModel tblModel = (DefaultTableModel)dataTable.getModel();
                tblModel.setRowCount(0);
                this.viewAllData();
                this.clearForm();
                JOptionPane.showMessageDialog(null, "New Product Saved Successfully");
                
                
                }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
                
            }
            else if(action=="update")
            {
                try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_management_system", "root", "");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM products WHERE product_name='"+name+"' AND product_code!='"+code+"'");                
                if(rs.next())
                {
                    JOptionPane.showMessageDialog(null, "Product name already exists!");
                }
                else {
                    PreparedStatement stmt = con.prepareStatement("UPDATE products SET category_code=?, product_name=?, unit_price=? WHERE product_code=?");             
                    stmt.setString(1, cat_code);                
                    stmt.setString(2, name);                      
                    stmt.setString(3, rate);                      
                    stmt.setString(4, code);                
                    stmt.executeUpdate();                    
                    DefaultTableModel tblModel = (DefaultTableModel)dataTable.getModel();
                    tblModel.setRowCount(0);
                    this.viewAllData();
                    this.clearForm();
                    JOptionPane.showMessageDialog(null, "Product Updated Successfully");
                    

                }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
   }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        sku_name = new javax.swing.JTextField();
        updateBtn = new javax.swing.JButton();
        insertBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        refreshBtn = new javax.swing.JButton();
        sku_code = new javax.swing.JTextField();
        price = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        category = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Product  Name");

        sku_name.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sku_name.setForeground(new java.awt.Color(0, 0, 255));

        updateBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        updateBtn.setText("Update");
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        insertBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        insertBtn.setText("Insert");
        insertBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertBtnActionPerformed(evt);
            }
        });

        clearBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        clearBtn.setText("Clear");
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBtnActionPerformed(evt);
            }
        });

        deleteBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        deleteBtn.setText("Delete");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Product Code");

        refreshBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        refreshBtn.setText("Print Table");
        refreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnActionPerformed(evt);
            }
        });

        sku_code.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sku_code.setForeground(new java.awt.Color(255, 0, 0));
        sku_code.setText("Auto Generated");

        price.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        price.setForeground(new java.awt.Color(0, 0, 255));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Product  Price");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Category");

        category.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        category.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                categoryItemStateChanged(evt);
            }
        });
        category.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryActionPerformed(evt);
            }
        });

        dataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Code", "Product Name", "Category Code", "Unit Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        dataTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dataTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(dataTable);

        jPanel1.setBackground(new java.awt.Color(102, 255, 102));

        jLabel1.setBackground(new java.awt.Color(255, 0, 0));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Inventory Management System");

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Product");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(28, 28, 28))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel5)
                        .addComponent(jLabel4)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(price)
                    .addComponent(updateBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sku_name)
                    .addComponent(sku_code)
                    .addComponent(clearBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(insertBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(130, 130, 130)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(refreshBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(refreshBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sku_code, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sku_name, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(insertBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(135, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void insertBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertBtnActionPerformed
        this.formValidation("insert");
    }//GEN-LAST:event_insertBtnActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
          this.formValidation("update");
    }//GEN-LAST:event_updateBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        int response = JOptionPane.showConfirmDialog(this, "Do you want to delete this product?\nAfter deleted this product will be\n delete all products from current stock.", "Confirm", JOptionPane.YES_NO_OPTION);
        if(response==JOptionPane.YES_OPTION)
        {
            String code = sku_code.getText();
        try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_management_system", "root", "");
                String sql = "DELETE FROM products WHERE product_code=?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(code));
                stmt.executeUpdate();
                String query2 = "DELETE FROM current_stock WHERE product_code IN('"+code+"')";
                Statement stmt2 = con.createStatement();
                stmt2.executeUpdate(query2);   
                DefaultTableModel tblModel = (DefaultTableModel)dataTable.getModel();
                tblModel.setRowCount(0);
                this.viewAllData();
                this.clearForm();
                JOptionPane.showMessageDialog(null, "Product has been Deleted");
                
               }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtnActionPerformed
        this.clearForm();
    }//GEN-LAST:event_clearBtnActionPerformed

    private void categoryItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_categoryItemStateChanged
        
    }//GEN-LAST:event_categoryItemStateChanged

    private void categoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryActionPerformed
      
        
        
    }//GEN-LAST:event_categoryActionPerformed

    private void dataTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dataTableMouseClicked
        int row = dataTable.getSelectedRow();
        DefaultTableModel tblModel = (DefaultTableModel)dataTable.getModel();
        sku_code.setText(tblModel.getValueAt(row, 0).toString());        
        sku_name.setText(tblModel.getValueAt(row, 1).toString());
        price.setText(tblModel.getValueAt(row, 3).toString()); 
        String along =  tblModel.getValueAt(row, 2).toString();
        String cat_name = (along.toString()).substring(7);
        switch(cat_name)
        {
            case "Mobile" :
            category.setSelectedIndex(0);
            break;
            case "Laptop" :
             category.setSelectedIndex(1);
            break;
            case "Electronics" :
             category.setSelectedIndex(2);
            break;
            case "Television" :
             category.setSelectedIndex(3);
            break;
            case "Refrigerator" :
             category.setSelectedIndex(4);
            break;
            case "Baby Food" :
            category.setSelectedIndex(5);
            break;
            default:
             category.setSelectedIndex(0);
        }


    }//GEN-LAST:event_dataTableMouseClicked

    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnActionPerformed
       try{
        dataTable.print();
          }catch(Exception e){
                      
          }
    }//GEN-LAST:event_refreshBtnActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Product().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> category;
    private javax.swing.JButton clearBtn;
    private javax.swing.JTable dataTable;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton insertBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField price;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JTextField sku_code;
    private javax.swing.JTextField sku_name;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables

}
