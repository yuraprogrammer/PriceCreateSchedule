/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pricecreator;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author yura_
 */
public class CreatorSettings extends javax.swing.JFrame {
    DocumentBuilderFactory dbf;
    DocumentBuilder db = null;
    Document doc = null;
    File xmlFile;
    List<ServerNode> servers;
    JDbConnection dbConn;
    boolean edited=false;
    boolean saved=false;
    String driver="";
    String ftpPath="";
    String ftpUser="";
    String ftpUserPassword="";
    String serial="";
    String DatabaseName="";
    String ServerPort="";
    String DatabaseUserName="";
    String DatabaseUserPassword="";
    String serverPath="";
    String urlPrefix="";
    String discount="";    
    
    public String getDiscount() {
        return discount;
    }

    private void setDiscount(String discount) {
            this.discount = discount;            
    }
    
    /**
     * Creates new form CreatorSettings
     */
    public CreatorSettings() {        
        initComponents();        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sizeWidth = this.getWidth();
        int sizeHeight = this.getHeight();
        int locationX = (screenSize.width - sizeWidth) / 2;
        int locationY = (screenSize.height - sizeHeight) / 2;
        this.setLocation(locationX, locationY);
        
        dbf = DocumentBuilderFactory.newInstance();
        
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException err) {
            System.out.println("Unable create document!!!");
        }
        
        xmlFile = new File(System.getProperty("user.dir")+File.separator+"settings.xml");
        try {
            doc = db.parse(xmlFile);            
            NodeList serverList = doc.getElementsByTagName("Server");
            servers = getServers(serverList);
            //Get database connection settings
            this.setServerPath(servers.get(0).getURL());
            this.setDatabaseName(servers.get(0).getSource());
            this.setDriver(servers.get(0).getDriver());
            this.setServerPort(servers.get(0).getPort());
            this.setDatabaseUserName(servers.get(0).getUsername());
            this.setDatabaseUserPassword(servers.get(0).getPassword());
            //Get serial number
            NodeList serialList = doc.getElementsByTagName("SerialNum");
            Node serialNode = serialList.item(0);
            NamedNodeMap map = serialNode.getAttributes();
            Node serialItem = map.getNamedItem("name");
            this.setSerial(serialItem.getNodeValue());            
            //Get ftp connection settings
            NodeList ftpList = doc.getElementsByTagName("FTP");
            Node ftpNode = ftpList.item(0);
            NamedNodeMap ftpMap = ftpNode.getAttributes();
            Node ftpPathItem = ftpMap.getNamedItem("Path");
            Node ftpUserItem = ftpMap.getNamedItem("User");
            Node ftpPasswordItem = ftpMap.getNamedItem("Password");
            this.setFtpPath(ftpPathItem.getNodeValue());
            this.setFtpUser(ftpUserItem.getNodeValue());
            this.setFtpUserPassword(ftpPasswordItem.getNodeValue());
            NodeList discountList = doc.getElementsByTagName("Discount");
            Node discountNode = discountList.item(0);
            NamedNodeMap discountMap = discountNode.getAttributes();
            Node discountItem = discountMap.getNamedItem("name");
            this.setDiscount(discountItem.getNodeValue());
            
            fillFields();
            edited=false;
            btnAccept.setEnabled(edited);
        } catch (SAXException | IOException ex) {
            Logger.getLogger(CreatorSettings.class.getName()).log(Level.SEVERE, null, ex);
        }                            
    }

    private void fillFields(){
        eDbName.setText(this.getDatabaseName());
        eServerAddress.setText(this.getServerPath());
        ePortNum.setText(this.getServerPort());
        eLogin.setText(this.getDatabaseUserName());
        ePassword.setText(this.getDatabaseUserPassword());
        
        eFtpAddress.setText(this.getFtpPath());
        eFtpUserName.setText(this.getFtpUser());
        eFtpUserPassword.setText(this.getFtpUserPassword());
        
        eSerialNumber.setText(this.getSerial());
        eDiscount.setText(this.getDiscount());
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label5 = new java.awt.Label();
        eSerialNumber = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        btnCancel = new javax.swing.JButton();
        btnAccept = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        eDbName = new javax.swing.JTextField();
        label3 = new java.awt.Label();
        label4 = new java.awt.Label();
        eServerAddress = new javax.swing.JTextField();
        ePortNum = new javax.swing.JTextField();
        label1 = new java.awt.Label();
        eLogin = new javax.swing.JTextField();
        label2 = new java.awt.Label();
        ePassword = new javax.swing.JPasswordField();
        btnTest = new javax.swing.JButton();
        showDbPassword = new javax.swing.JCheckBox();
        label9 = new java.awt.Label();
        jPanel2 = new javax.swing.JPanel();
        label6 = new java.awt.Label();
        eFtpAddress = new javax.swing.JTextField();
        label7 = new java.awt.Label();
        eFtpUserName = new javax.swing.JTextField();
        label8 = new java.awt.Label();
        eFtpUserPassword = new javax.swing.JPasswordField();
        showFtpPassword = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        label10 = new java.awt.Label();
        eDiscount = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Настройки");
        setResizable(false);

        label5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        label5.setText("Серийный номер:");

        eSerialNumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                eSerialNumberFocusLost(evt);
            }
        });
        eSerialNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                eSerialNumberKeyPressed(evt);
            }
        });

        btnCancel.setText("Отмена");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnAccept.setText("Применить");
        btnAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptActionPerformed(evt);
            }
        });

        btnOk.setText("ОК");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Настройка связис БД:"));

        eDbName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                eDbNameFocusLost(evt);
            }
        });
        eDbName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                eDbNameKeyPressed(evt);
            }
        });

        label3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        label3.setText("Имя БД:");

        label4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        label4.setText("Порт:");

        eServerAddress.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                eServerAddressFocusLost(evt);
            }
        });
        eServerAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                eServerAddressKeyPressed(evt);
            }
        });

        ePortNum.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ePortNumFocusLost(evt);
            }
        });
        ePortNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ePortNumKeyPressed(evt);
            }
        });

        label1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        label1.setText("Имя пользователя:");

        eLogin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                eLoginFocusLost(evt);
            }
        });
        eLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                eLoginKeyPressed(evt);
            }
        });

        label2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        label2.setText("Пароль:");

        ePassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ePasswordFocusLost(evt);
            }
        });
        ePassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ePasswordKeyPressed(evt);
            }
        });

        btnTest.setText("Тест соединения");
        btnTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestActionPerformed(evt);
            }
        });

        showDbPassword.setText("Показать");
        showDbPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showDbPasswordActionPerformed(evt);
            }
        });

        label9.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        label9.setText("Имя сервера БД:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(eDbName, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(eServerAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 13, Short.MAX_VALUE))
                            .addComponent(ePortNum)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTest)
                            .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(ePassword)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(showDbPassword))
                    .addComponent(eLogin))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(eDbName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ePortNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(eServerAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(eLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ePassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showDbPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTest)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Настройка связи с ftp:"));

        label6.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        label6.setText("Путь к ftp-серверу:");

        eFtpAddress.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                eFtpAddressFocusLost(evt);
            }
        });
        eFtpAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                eFtpAddressKeyPressed(evt);
            }
        });

        label7.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        label7.setText("Имя пользователя:");

        eFtpUserName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                eFtpUserNameFocusLost(evt);
            }
        });
        eFtpUserName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                eFtpUserNameKeyPressed(evt);
            }
        });

        label8.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        label8.setText("Пароль:");

        eFtpUserPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                eFtpUserPasswordFocusLost(evt);
            }
        });
        eFtpUserPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                eFtpUserPasswordKeyPressed(evt);
            }
        });

        showFtpPassword.setText("Показать");
        showFtpPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showFtpPasswordActionPerformed(evt);
            }
        });

        jButton1.setText("Тест соединения");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(eFtpAddress)
                    .addComponent(eFtpUserName)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(eFtpUserPassword)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(showFtpPassword))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(eFtpAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(eFtpUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eFtpUserPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showFtpPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        label10.setText("Размер скидки, %:");

        eDiscount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                eDiscountFocusLost(evt);
            }
        });
        eDiscount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                eDiscountKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(176, 176, 176)
                                .addComponent(btnOk)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAccept)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancel))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(eSerialNumber)
                            .addComponent(jSeparator1)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(eDiscount))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(eSerialNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(eDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btnAccept)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        if (edited){
            if (JOptionPane.showConfirmDialog(this, "Сохранить перед выходом?", "Подтверждение", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
                save();
            }
        }
        System.exit(0);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestActionPerformed
        dbConn = new JDbConnection(this.getDriver(), this.getDatabaseName(), servers.get(0).getUrlPrefix()+this.getServerPath()+":"+this.getServerPort(), this.getDatabaseUserName(), this.getDatabaseUserPassword());
        if (dbConn.isConnected()){
            JOptionPane.showMessageDialog(this, "Соединение установлено!!!", "Тест соединения с БД...", JOptionPane.OK_OPTION);
        }else{
            JOptionPane.showMessageDialog(this, "Соединение не установлено!!! Проверьте настройки. \r\n"+dbConn.e.getMessage()+"\r\n"+dbConn.getUrl(), "Тест соединения с БД...", JOptionPane.OK_OPTION);
        }
    }//GEN-LAST:event_btnTestActionPerformed

    private void btnAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptActionPerformed
        save();
        edited = false;
        btnAccept.setEnabled(false);
    }//GEN-LAST:event_btnAcceptActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        if (edited){
            save();
        }
        System.exit(0);
    }//GEN-LAST:event_btnOkActionPerformed

    private void showDbPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showDbPasswordActionPerformed
        if (showDbPassword.isSelected()){
            ePassword.setEchoChar('\0');
        }else{
            ePassword.setEchoChar('*');
        }
    }//GEN-LAST:event_showDbPasswordActionPerformed

    private void showFtpPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showFtpPasswordActionPerformed
        if (showFtpPassword.isSelected()){
            eFtpUserPassword.setEchoChar('\0');
        }else{
            eFtpUserPassword.setEchoChar('*');
        }
    }//GEN-LAST:event_showFtpPasswordActionPerformed

    private void eSerialNumberFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_eSerialNumberFocusLost
        edited=true;
        this.setSerialNumber(edited);        
    }//GEN-LAST:event_eSerialNumberFocusLost

    private void eSerialNumberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_eSerialNumberKeyPressed
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            edited=true;
            this.setSerialNumber(edited);
        }
    }//GEN-LAST:event_eSerialNumberKeyPressed

    private void eDbNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_eDbNameKeyPressed
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            edited=true;
            this.setDbName(edited);
            eDbName.transferFocus();
        }
    }//GEN-LAST:event_eDbNameKeyPressed

    private void eDbNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_eDbNameFocusLost
        edited=true;
        this.setDbName(edited);
    }//GEN-LAST:event_eDbNameFocusLost

    private void ePortNumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ePortNumKeyPressed
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            edited=true;
            this.setPortNumber(edited);
            ePortNum.transferFocus();
        }
    }//GEN-LAST:event_ePortNumKeyPressed

    private void ePortNumFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ePortNumFocusLost
        edited=true;
        this.setPortNumber(edited);
    }//GEN-LAST:event_ePortNumFocusLost

    private void eLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_eLoginKeyPressed
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            edited=true;
            this.setDbUserName(edited);
            eLogin.transferFocus();
        }
    }//GEN-LAST:event_eLoginKeyPressed

    private void eLoginFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_eLoginFocusLost
        edited=true;
        this.setDbUserName(edited);
    }//GEN-LAST:event_eLoginFocusLost

    private void ePasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ePasswordKeyPressed
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            edited=true;
            this.setDbPassword(edited);
            ePassword.transferFocus();
        }
    }//GEN-LAST:event_ePasswordKeyPressed

    private void ePasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ePasswordFocusLost
        edited=true;
        this.setDbPassword(edited);
    }//GEN-LAST:event_ePasswordFocusLost

    private void eFtpAddressKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_eFtpAddressKeyPressed
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            edited=true;
            this.setFtpPath(edited);
            eFtpAddress.transferFocus();
        }
    }//GEN-LAST:event_eFtpAddressKeyPressed

    private void eFtpAddressFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_eFtpAddressFocusLost
        edited=true;
        this.setFtpPath(edited);
    }//GEN-LAST:event_eFtpAddressFocusLost

    private void eFtpUserNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_eFtpUserNameKeyPressed
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            edited=true;
            this.setFtpUserName(edited);
            eFtpUserName.transferFocus();
        }
    }//GEN-LAST:event_eFtpUserNameKeyPressed

    private void eFtpUserNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_eFtpUserNameFocusLost
        edited=true;
        this.setFtpUserName(edited);
    }//GEN-LAST:event_eFtpUserNameFocusLost

    private void eFtpUserPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_eFtpUserPasswordKeyPressed
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            edited=true;
            this.setFtpPassword(edited);
            eFtpUserPassword.transferFocus();
        }
    }//GEN-LAST:event_eFtpUserPasswordKeyPressed

    private void eFtpUserPasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_eFtpUserPasswordFocusLost
        edited=true;
        this.setFtpPassword(edited);
    }//GEN-LAST:event_eFtpUserPasswordFocusLost

    private void eServerAddressKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_eServerAddressKeyPressed
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            edited=true;
            this.setServerAddress(edited);
            eServerAddress.transferFocus();
        }
    }//GEN-LAST:event_eServerAddressKeyPressed

    private void eServerAddressFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_eServerAddressFocusLost
        edited=true;
        this.setServerAddress(edited);
    }//GEN-LAST:event_eServerAddressFocusLost

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            FTPClient ftp = null;
            ftp = new FTPClient();
            ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
            int reply;
            ftp.connect(this.getFtpPath());
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                JOptionPane.showMessageDialog(this, "Неверный адрес FTP-сервера!!!", "Тест соединения с FTP...", JOptionPane.OK_OPTION);
                ftp.disconnect();			
            }
            String login = this.getFtpUser();
            String password = this.getFtpUserPassword();            
            if (ftp.login(login, password)){
                JOptionPane.showMessageDialog(this, "Соединение с FTP-сервером установлено!!!", "Тест соединения с FTP...", JOptionPane.OK_OPTION);
            }else{
                JOptionPane.showMessageDialog(this, "Неверный логин или пароль!!!", "Тест соединения с FTP...", JOptionPane.OK_OPTION);
            }
            ftp.disconnect();
        } catch (IOException ex) {
            Logger.getLogger(CreatorSettings.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void eDiscountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_eDiscountKeyPressed
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
            try{
                float discountValue = Float.parseFloat(discount);
                edited=true;
                setDiscountValue(edited);
                eDiscount.transferFocus();            
            }catch(java.lang.NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Неверный формат числа скидки!!!", "Ошибка ввода!!!", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }//GEN-LAST:event_eDiscountKeyPressed

    private void eDiscountFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_eDiscountFocusLost
        try{
                float discountValue = Float.parseFloat(discount);
                edited=true;
                setDiscountValue(edited);                
            }catch(java.lang.NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Неверный формат числа скидки!!!", "Ошибка ввода!!!", JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_eDiscountFocusLost

    private void save(){
        try {
            NodeList serverList = doc.getElementsByTagName("Server");
            Node serverNode = serverList.item(0);
            NamedNodeMap serverMap = serverNode.getAttributes();
            Node serverDriver = serverMap.getNamedItem("driver");
            serverDriver.setTextContent(this.getDriver());
            Node dbName = serverMap.getNamedItem("Source");
            dbName.setTextContent(this.getDatabaseName());
            Node dbAddress = serverMap.getNamedItem("URL");
            dbAddress.setTextContent(this.getServerPath());
            Node dbPort = serverMap.getNamedItem("Port");
            dbPort.setTextContent(this.getServerPort());
            Node dbUserName = serverMap.getNamedItem("User");
            dbUserName.setTextContent(this.getDatabaseUserName());
            Node dbUserPassword = serverMap.getNamedItem("Password");
            dbUserPassword.setTextContent(this.getDatabaseUserPassword());
            
            NodeList ftpList = doc.getElementsByTagName("FTP");
            Node ftpNode = ftpList.item(0);
            NamedNodeMap ftpMap = ftpNode.getAttributes();
            Node ftpServer = ftpMap.getNamedItem("Path");
            ftpServer.setTextContent(this.getFtpPath());
            Node ftpServerUser = ftpMap.getNamedItem("User");
            ftpServerUser.setTextContent(this.getFtpUser());
            Node ftpPassword = ftpMap.getNamedItem("Password");
            ftpPassword.setTextContent(this.getFtpUserPassword());
            
            NodeList serialList = doc.getElementsByTagName("SerialNum");
            Node serialNode = serialList.item(0);
            NamedNodeMap serialMap = serialNode.getAttributes();
            Node serialItem = serialMap.getNamedItem("name");
            serialItem.setTextContent(this.getSerial());
            
            NodeList discountList = doc.getElementsByTagName("Discount");
            Node discountNode = discountList.item(0);
            NamedNodeMap discountMap = discountNode.getAttributes();
            Node discountItem = discountMap.getNamedItem("name");
            discountItem.setTextContent(this.getDiscount());
            
            Transformer transformer = TransformerFactory.newInstance()
                    .newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(System.getProperty("user.dir")+File.separator+"settings.xml"));
            transformer.transform(source, result);
            JOptionPane.showMessageDialog(this, "Сохранение выполнено успешно!!!", "Сохранение...", JOptionPane.OK_OPTION);
            edited = false;
        } catch (TransformerException ex) {
            JOptionPane.showMessageDialog(this, "Ошибка!!! "+ex.getLocalizedMessage(), "Сохранение...", JOptionPane.OK_OPTION);                        
        }
    }        
    
    private void setDiscountValue(boolean edited){
        if (edited){
            if (!eDiscount.getText().isEmpty()){
                setDiscount(eDiscount.getText().replace(",", "."));                
            }
            btnAccept.setEnabled(true);
        }
    }
    
    private void setDbName(boolean edited){
        if (edited){
            setDatabaseName(eDbName.getText());
            btnAccept.setEnabled(true);
        }
    }
    
    private void setPortNumber(boolean edited){
        if (edited){
            this.setServerPort(ePortNum.getText());
            btnAccept.setEnabled(true);
        }
    }
    
    private void setDbUserName(boolean edited){
        if (edited){
            this.setDatabaseUserName(eLogin.getText());
            btnAccept.setEnabled(true);
        }
    }
    
    private void setDbPassword(boolean edited){
        if (edited){
            this.setDatabaseUserPassword(String.valueOf(ePassword.getPassword()));
            btnAccept.setEnabled(true);
        }
    }
    
    private void setFtpPath(boolean edited){
        if(edited){
            this.setFtpPath(eFtpAddress.getText());
            btnAccept.setEnabled(true);
        }
    }
    
    private void setFtpUserName(boolean edited){
        if(edited){
            this.setFtpUser(eFtpUserName.getText());
            btnAccept.setEnabled(true);
        }
    }
    
    private void setFtpPassword(boolean edited){
        if (edited){
            this.setFtpUserPassword(String.valueOf(eFtpUserPassword.getPassword()));
            btnAccept.setEnabled(true);
        }
    }
    
    private void setSerialNumber(boolean edited){
        if (edited){
            this.setSerial(eSerialNumber.getText());
            btnAccept.setEnabled(true);            
        }
    }

    private void setServerAddress(boolean edited){
        if (edited){
            this.setServerPath(eServerAddress.getText());
            btnAccept.setEnabled(true);
        }
    }
    
    public String getDriver() {
        return driver;
    }

    public final void setDriver(String driver) {
        this.driver = driver;
    }

    public String getFtpPath() {
        return ftpPath;
    }

    public final void setFtpPath(String ftpPath) {
        this.ftpPath = ftpPath;
    }

    public String getFtpUser() {
        return ftpUser;
    }

    public final void setFtpUser(String ftpUser) {
        this.ftpUser = ftpUser;
    }

    public String getFtpUserPassword() {
        return ftpUserPassword;
    }

    public final void setFtpUserPassword(String ftpUserPassword) {
        this.ftpUserPassword = ftpUserPassword;
    }

    public String getSerial() {
        return serial;
    }

    public final void setSerial(String serial) {
        this.serial = serial;
    }

    public String getDatabaseName() {
        return DatabaseName;
    }

    public final void setDatabaseName(String DatabaseName) {
        this.DatabaseName = DatabaseName;
    }

    public String getServerPort() {
        return ServerPort;
    }

    public final void setServerPort(String ServerPort) {
        this.ServerPort = ServerPort;
    }

    public String getDatabaseUserName() {
        return DatabaseUserName;
    }

    public final void setDatabaseUserName(String DatabaseUserName) {
        this.DatabaseUserName = DatabaseUserName;
    }

    public String getDatabaseUserPassword() {
        return DatabaseUserPassword;
    }

    public final void setDatabaseUserPassword(String DatabaseUserPassword) {
        this.DatabaseUserPassword = DatabaseUserPassword;
    }

    public String getServerPath() {
        return serverPath;
    }

    public final void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {            
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CreatorSettings.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(CreatorSettings.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CreatorSettings.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(CreatorSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreatorSettings().setVisible(true);
            }
        });
    }
    
    private static List<ServerNode> getServers(NodeList list){
        ArrayList<ServerNode> servers = new ArrayList<>();
        for (int i=0; i<list.getLength(); i++){
            ServerNode server = new ServerNode(list.item(i));
            servers.add(server);
        }
        return servers;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccept;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnTest;
    private javax.swing.JTextField eDbName;
    private javax.swing.JFormattedTextField eDiscount;
    private javax.swing.JTextField eFtpAddress;
    private javax.swing.JTextField eFtpUserName;
    private javax.swing.JPasswordField eFtpUserPassword;
    private javax.swing.JTextField eLogin;
    private javax.swing.JPasswordField ePassword;
    private javax.swing.JTextField ePortNum;
    private javax.swing.JTextField eSerialNumber;
    private javax.swing.JTextField eServerAddress;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private java.awt.Label label1;
    private java.awt.Label label10;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private java.awt.Label label5;
    private java.awt.Label label6;
    private java.awt.Label label7;
    private java.awt.Label label8;
    private java.awt.Label label9;
    private javax.swing.JCheckBox showDbPassword;
    private javax.swing.JCheckBox showFtpPassword;
    // End of variables declaration//GEN-END:variables
}
