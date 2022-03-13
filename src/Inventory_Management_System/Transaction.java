
package Inventory_Management_System;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.*;
import javax.swing.table.DefaultTableModel;
public class Transaction extends javax.swing.JFrame {

    /**
     * Creates new form Transaction
     */
    public String trx = "";
    public Transaction() {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getProducts();        
        this.getAllMemo();
        this.viewAllData("");
        
    }
    public void clearTable()
    {
        DefaultTableModel tblModel = (DefaultTableModel)dataTable.getModel();
        tblModel.setRowCount(0);
    }
    public void getAllMemo()
    {
        try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_management_system", "root", "");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT DISTINCT invoice_no FROM transactions ORDER BY trx_id DESC");
                while(rs.next())
                {                  
                    String invoice = rs.getString("invoice_no");  
                    memo.addItem(invoice);

                }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, e);
                }
    }
    public void getInfo(String code)
    {
        try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_management_system", "root", "");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT products.unit_price, categories.category_name FROM products INNER JOIN categories ON products.category_code = categories.category_code WHERE products.product_code = '"+code+"'");
                while(rs.next())
                {                  
                    String price = rs.getString("unit_price");                      
                    String name = rs.getString("category_name");                    
                  
                    category.setText(name);                    
                    unit_price.setText(price);

                }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, e);
                }
    }
    
    public void getProducts()
    {
        try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_management_system", "root", "");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM products");
                while(rs.next())
                {                  
                    String code = rs.getString("product_code");                      
                    String name = rs.getString("product_name");                    
                  
                    product.addItem(code + " " + name);
                }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, e);
                }
    }
    
    public void viewAllData(String invoice_no)
    {   this.clearTable();
        if(invoice_no=="" || invoice_no == "Choose Memo No")
                {
                    try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_management_system", "root", "");
                    Statement stmt = con.createStatement();

                    ResultSet rs = stmt.executeQuery("SELECT * FROM transactions ORDER BY trx_id DESC");
                    while(rs.next())
                    {
                        String trx_id = rs.getString("trx_id");  
                        this.trx = trx_id;
                        String inv = rs.getString("invoice_no");
                        String date = rs.getString("invoice_date"); 
                        String code = rs.getString("product_code");                    
                        String name = rs.getString("product_name");                       
                        String cat = rs.getString("category");                      
                        String unit = rs.getString("unit_price");                     
                        String qty = rs.getString("sales_qty");                    
                        String amount = rs.getString("total_amount");                                   
                   

                        String tableData[] = {inv, date, code + " " + name, cat , unit, qty, amount};
                        DefaultTableModel tblModel = (DefaultTableModel)dataTable.getModel();
                        tblModel.addRow(tableData);
                    }
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            else {
                try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_management_system", "root", "");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM transactions WHERE invoice_no LIKE '%"+invoice_no+"%' ORDER BY trx_id DESC");
                while(rs.next())
                {
                    String trx_id = rs.getString("trx_id");  
                    this.trx = trx_id;
                    String inv = rs.getString("invoice_no");     
                    String date = rs.getString("invoice_date"); 
                    String code = rs.getString("product_code");                    
                    String name = rs.getString("product_name");                       
                    String cat = rs.getString("category");                      
                    String unit = rs.getString("unit_price");                     
                    String qty = rs.getString("sales_qty");                    
                    String amount = rs.getString("total_amount");                    
                   
                    String tableData[] = {inv, date, code + " " + name, cat , unit, qty, amount};
                    DefaultTableModel tblModel = (DefaultTableModel)dataTable.getModel();
                    tblModel.addRow(tableData);
                }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
           
        
    }
    public void clearForm()
    {
        this.generateInvoiceNo();
        unit_price.setText("");        
        qty.setText("");

        

    }
    
    public void generateInvoiceNo()
    {
        int max = 99999999;
        int min = 11111111;
        long inv = Math.round(Math.random()*(max - min+1)+min);
        String invoice_no = String.valueOf(inv);
        invoiceNo.setText("INV" +invoice_no);
    }
    
    public void formValidation(String action)
    {

            String inv = invoiceNo.getText();
            String sku_name = product.getSelectedItem().toString(); 
            String product_name = sku_name.substring(8);
            String sku_qty = qty.getText();
            String code = sku_name.substring(0, 8);
            String unit = unit_price.getText();            
            String cat_name = category.getText();

            if(inv.length()==0)
            {
                JOptionPane.showMessageDialog(null, "Invoice no is required!");
            }
            else if(sku_name=="Choose Your Product")
            {
                JOptionPane.showMessageDialog(null, "Product is required!");
            }
            else if(sku_qty.length()==0)
            {
                JOptionPane.showMessageDialog(null, "Sales quantity is required!");
            }
            else if(unit.length()==0)
            {
                JOptionPane.showMessageDialog(null, "Product Price is required!");
            }
            else if(sku_qty.equals("0"))
            {
                JOptionPane.showMessageDialog(null, "Sales quantity will be greater than 0!");
            } 
            else {
                if(action=="insert")
                {
                try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_management_system", "root", "");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM current_stock WHERE product_code='"+code+"'"); 
//                ResultSet rs4 = st.executeQuery("SELECT * FROM categories WHERE product_code='"+code+"'");                
                if(rs.next())
                {
                    int current_stock = rs.getInt("stock_qty");
                    int remain_stock    = current_stock - Integer.parseInt(sku_qty);
                    if(remain_stock<0){
                        JOptionPane.showMessageDialog(null, sku_name + " " + current_stock + " pcs available!");
                    }

                    else{
                        double total = Integer.parseInt(sku_qty) * Double.parseDouble(unit);
                        Date d = new Date();
                        DateFormat dt = new SimpleDateFormat("dd-MM-YYYY");
                        String date = dt.format(d);
                        Statement stmt = con.createStatement();
                        rs = stmt.executeQuery("SELECT * FROM transactions WHERE invoice_no = '"+inv+"' AND product_code= '"+code+"' ");
                        if(rs.next())
                        {
                            String query1 = "UPDATE transactions SET sales_qty=(SELECT sales_qty FROM transactions WHERE invoice_no='"+inv+"' AND product_code='"+code+"') + ('"+sku_qty+"'), total_amount=((SELECT sales_qty FROM transactions WHERE invoice_no='"+inv+"' AND product_code='"+code+"') + '"+Integer.parseInt(sku_qty)+"') * ('"+unit+"') WHERE trx_id=?";
                            PreparedStatement stmt2 = con.prepareStatement(query1);
                            stmt2.setString(1, trx);                            
                            stmt2.executeUpdate();    
                            stmt2.close();
                        }
                        else{
                            String query1 = "INSERT INTO transactions (invoice_no,product_code, product_name,category,unit_price,sales_qty,total_amount,invoice_date)VALUES(?,?,?,?,?,?,?,?)";
                            PreparedStatement stmt2 = con.prepareStatement(query1);
                            stmt2.setString(1, inv);                  
                            stmt2.setString(2, code);                
                            stmt2.setString(3, product_name);                  
                            stmt2.setString(4, cat_name);                
                            stmt2.setDouble(5, Double.parseDouble(unit));                  
                            stmt2.setInt(6, Integer.parseInt(sku_qty));                
                            stmt2.setDouble(7, total);                        
                            stmt2.setString(8, date);               
                            stmt2.executeUpdate();    
                            stmt2.close();
                        }
                        
                            String query2 = "UPDATE current_stock SET stock_qty=? WHERE product_code=?";
                            PreparedStatement stmt3 = con.prepareStatement(query2);
                            stmt3.setInt(1, remain_stock);                  
                            stmt3.setString(2, code); 
                            stmt3.executeUpdate();   
                            qty.setText("");
                            DefaultTableModel tblModel = (DefaultTableModel)dataTable.getModel();
                            tblModel.setRowCount(0);
                            this.viewAllData("");
                           JOptionPane.showMessageDialog(null, "Sale Confirm successfully");
                  
                    }
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
                        ResultSet rs;
                        rs = st.executeQuery("SELECT * FROM transactions WHERE trx_id='"+trx+"'"); 
                        if(rs.next())
                        {
                            int old_sale_qty = rs.getInt("sales_qty");         
                            String product_code = rs.getString("product_code");
                            String sku = rs.getString("product_name");
                            int diff = Integer.parseInt(sku_qty) - old_sale_qty;
                            if(diff>=0)
                            {
                                rs = st.executeQuery("SELECT * FROM current_stock WHERE product_code='"+product_code+"'");
                                if(rs.next())
                                {
                                    int old_stock_qty = rs.getInt("stock_qty");
                                    int diff_stock = old_stock_qty - diff;
                                    if(diff_stock < 0)
                                    {
                                        JOptionPane.showMessageDialog(null, sku + " " + old_stock_qty + " pcs available!");
                                    }
                                    else {
                                        String sql = "UPDATE current_stock SET stock_qty=(SELECT stock_qty FROM current_stock WHERE product_code='"+product_code+"') - '"+diff+"' WHERE product_code=?";
                                        PreparedStatement stmt2 = con.prepareStatement(sql);                
                                        stmt2.setString(1, product_code);
                                        stmt2.executeUpdate();
                                        stmt2.close();
                                        String sql2 = "UPDATE transactions SET sales_qty=? WHERE trx_id=?";
                                        stmt2 = con.prepareStatement(sql2);                
                                        stmt2.setInt(1, Integer.parseInt(sku_qty));  
                                        stmt2.setInt(2, Integer.parseInt(trx));
                                        stmt2.executeUpdate();
                                        stmt2.close();
                                        JOptionPane.showMessageDialog(null, "Sales Updated successfully");
                                    }
                                }
                            }
                            else {
                                        String sql3 = "UPDATE current_stock SET stock_qty=(SELECT stock_qty FROM current_stock WHERE product_code='"+product_code+"') + '"+Math.abs(diff)+"' WHERE product_code=?";
                                        PreparedStatement stmt2 = con.prepareStatement(sql3);                
                                        stmt2.setString(1, product_code);
                                        stmt2.executeUpdate();
                                        stmt2.close();
                                        String sql4 = "UPDATE transactions SET sales_qty=? WHERE trx_id=?";
                                        stmt2 = con.prepareStatement(sql4);                
                                        stmt2.setInt(1, Integer.parseInt(sku_qty));  
                                        stmt2.setInt(2, Integer.parseInt(trx));
                                        stmt2.executeUpdate();
                                        stmt2.close();
                                        JOptionPane.showMessageDialog(null, "Sales Updated successfully");
                            }

                            
                        }
                        rs.close();
                        DefaultTableModel tblModel = (DefaultTableModel)dataTable.getModel();
                        tblModel.setRowCount(0);
                        this.viewAllData("");
                       }
                    
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
               }
            }
            
        
   }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        deleteBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        viewBtn = new javax.swing.JButton();
        printTable = new javax.swing.JButton();
        invoiceNo = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        updateBtn = new javax.swing.JButton();
        insertBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        qty = new javax.swing.JTextField();
        product = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        unit_price = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        category = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();
        memo = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        deleteBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        deleteBtn.setText("Delete Sale");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Invoice No. :");

        viewBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        viewBtn.setText("Generate Invoice No");
        viewBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewBtnActionPerformed(evt);
            }
        });

        printTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        printTable.setText("Print Memo");
        printTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printTableActionPerformed(evt);
            }
        });

        invoiceNo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        invoiceNo.setForeground(new java.awt.Color(255, 0, 0));

        jPanel1.setBackground(new java.awt.Color(153, 255, 255));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Inventory Management System");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Transaction");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        updateBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        updateBtn.setText("Edit Sale");
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        insertBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        insertBtn.setText("Confrim Sale");
        insertBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertBtnActionPerformed(evt);
            }
        });

        clearBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        clearBtn.setText("Clear Form");
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBtnActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Choose Product :");

        qty.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        qty.setForeground(new java.awt.Color(51, 51, 255));

        product.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        product.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Unit Price :");

        unit_price.setEditable(false);
        unit_price.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        unit_price.setForeground(new java.awt.Color(51, 51, 255));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Sales Quantity :");

        category.setEditable(false);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Category :");

        dataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Invoice No.", "Invoice Date", "Product", "Category", "Unit Price", "Sales Qty", "Total Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
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
        jScrollPane1.setViewportView(dataTable);

        memo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Memo No" }));
        memo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                memoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(invoiceNo))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(product, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(updateBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(deleteBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                            .addComponent(insertBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(clearBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(qty)
                            .addComponent(unit_price)
                            .addComponent(category))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(viewBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(printTable)
                        .addGap(34, 34, 34)
                        .addComponent(memo, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 745, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(viewBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(printTable, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(invoiceNo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(memo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(product, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(unit_price, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(qty, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addComponent(insertBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(110, 110, 110))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void insertBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertBtnActionPerformed
        this.formValidation("insert");
    }//GEN-LAST:event_insertBtnActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
         this.formValidation("update");
    }//GEN-LAST:event_updateBtnActionPerformed

    private void printTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printTableActionPerformed
       try{
           dataTable.print();
       }
       catch(Exception e)
       {
           JOptionPane.showMessageDialog(null, e.getMessage());
       }
    }//GEN-LAST:event_printTableActionPerformed

    private void viewBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewBtnActionPerformed
        this.generateInvoiceNo();
    }//GEN-LAST:event_viewBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        int response = JOptionPane.showConfirmDialog(this, "Do you want to remove this sales?", "Confirm", JOptionPane.YES_NO_OPTION);
        int sales_qty = Integer.parseInt(qty.getText());
        if(response==JOptionPane.YES_OPTION)
        {       
            String trx = this.trx;

        try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_management_system", "root", "");
                Statement st = con.createStatement();
                ResultSet rs; 
                rs = st.executeQuery("SELECT * FROM transactions WHERE trx_id ='"+trx+"'");
                if(rs.next())
                {
                    String code = rs.getString("product_code"); 
                    String sql = "UPDATE current_stock SET stock_qty=(SELECT stock_qty FROM current_stock WHERE product_code='"+code+"') + '"+sales_qty+"' WHERE product_code=?";
                    PreparedStatement stmt2 = con.prepareStatement(sql);                
                    stmt2.setString(1, code);
                    stmt2.executeUpdate();
                }
                rs.close();
                String sql2 = "DELETE FROM transactions WHERE trx_id=?";
                PreparedStatement stmt = con.prepareStatement(sql2);
                stmt.setString(1, trx);
                stmt.executeUpdate();
                DefaultTableModel tblModel = (DefaultTableModel)dataTable.getModel();
                tblModel.setRowCount(0);
                this.viewAllData("");
                this.clearForm();
                this.generateInvoiceNo();
                JOptionPane.showMessageDialog(null, "Memo has been Deleted");
                
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

    private void productActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productActionPerformed
        String prd = product.getSelectedItem().toString();
        this.getInfo(prd);
    }//GEN-LAST:event_productActionPerformed

    private void dataTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dataTableMouseClicked
       int row = dataTable.getSelectedRow();
        DefaultTableModel tblModel = (DefaultTableModel)dataTable.getModel();
        invoiceNo.setText(tblModel.getValueAt(row, 0).toString());        
        category.setText(tblModel.getValueAt(row, 3).toString());
        unit_price.setText(tblModel.getValueAt(row, 4).toString());  
        qty.setText(tblModel.getValueAt(row, 5).toString()); 
        String along =  tblModel.getValueAt(row, 2).toString();
        String sku_name = (along.toString()).substring(9);  
        switch(sku_name)
        {
            case "Lactogen 2 180gm" :
            product.setSelectedIndex(0);
            break;
            case "Walton Refrigerator" :
            product.setSelectedIndex(1);
            break;
            case "Lactogen 3 180 gm" :
            product.setSelectedIndex(2);
            break;
            default:
             product.setSelectedIndex(0);
        }
    }//GEN-LAST:event_dataTableMouseClicked

    private void memoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_memoActionPerformed
        String invoice_no = memo.getSelectedItem().toString();
        this.viewAllData(invoice_no);
       
    }//GEN-LAST:event_memoActionPerformed

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
            java.util.logging.Logger.getLogger(Transaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Transaction().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField category;
    private javax.swing.JButton clearBtn;
    private javax.swing.JTable dataTable;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton insertBtn;
    private javax.swing.JTextField invoiceNo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> memo;
    private javax.swing.JButton printTable;
    private javax.swing.JComboBox<String> product;
    private javax.swing.JTextField qty;
    private javax.swing.JTextField unit_price;
    private javax.swing.JButton updateBtn;
    private javax.swing.JButton viewBtn;
    // End of variables declaration//GEN-END:variables
}
