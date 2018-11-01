/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pricecreator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.csv.CSVFormat;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.apache.commons.csv.CSVPrinter;
import java.util.zip.*;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author yura_
 */
public class Scheduler {
    DocumentBuilderFactory dbf;
    DocumentBuilder db = null;
    Document doc = null;
    File xmlFile;
    List<ServerNode> servers;
    JDbConnection dbConn;
    String driver="";
    String source="";
    String URL="";
    String userName="";
    String userPassword="";
    String serial="";
    String port="";
    String urlPrefix="";
    String ftpAddress="";
    String ftpUser="";
    String ftpPassword="";
    static final Logger logger = Logger.getLogger(Scheduler.class.getName());
    FileHandler fh;   
    

    public Scheduler(){
        try {   
            fh = new FileHandler(System.getProperty("user.dir")+File.separator+"errors.log");
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter); 
        } catch (IOException | SecurityException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        logger.addHandler(fh);
        openSettings();
        doConnect();
        if (dbConn.isConnected()){
            logger.log(Level.INFO, "Database connected successfull");
            doTask();
        }else{
            logger.log(Level.SEVERE, "Database not connected. System will be terminated. "+dbConn.e.getMessage());            
        }
        //doTask();
    }
    
    private void openSettings(){
        dbf = DocumentBuilderFactory.newInstance();
        
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException err) {
            logger.log(Level.SEVERE, "Unable create document!!!");
        }
        
        xmlFile = new File(System.getProperty("user.dir")+File.separator+"settings.xml");
        try {
            doc = db.parse(xmlFile);            
            NodeList serverList = doc.getElementsByTagName("Server");
            servers = getServers(serverList);
            driver = servers.get(0).getDriver();
            source = servers.get(0).getSource();
            URL = servers.get(0).getURL();
            userName = servers.get(0).getUsername();
            userPassword = servers.get(0).getPassword();
            port=servers.get(0).getPort();
            urlPrefix = servers.get(0).getUrlPrefix();
            NodeList serialList = doc.getElementsByTagName("SerialNum");
            Node serialNode = serialList.item(0);
            NamedNodeMap map = serialNode.getAttributes();
            Node serialItem = map.getNamedItem("name");
            serial = serialItem.getNodeValue();
            NodeList ftpList = doc.getElementsByTagName("FTP");
            Node ftpNode = ftpList.item(0);
            NamedNodeMap ftpMap = ftpNode.getAttributes();
            Node ftpPathItem = ftpMap.getNamedItem("Path");
            Node ftpUserItem = ftpMap.getNamedItem("User");
            Node ftpPasswordItem = ftpMap.getNamedItem("Password");
            ftpAddress = ftpPathItem.getNodeValue();
            ftpUser = ftpUserItem.getNodeValue();
            ftpPassword = ftpPasswordItem.getNodeValue();
            logger.log(Level.INFO, "Settings opened and accepted!!!");
        } catch (SAXException | IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    private void doConnect(){
        
        dbConn = new JDbConnection(driver, source, urlPrefix+URL+":"+port, userName, userPassword);
    }
    
    private void doTask(){
        Statement stmt;
        try {
            stmt = dbConn.db.createStatement();
            String query = "select distinct \n" +
"                                d.ID as Code, q.GOODSNAME_UC as Name, \n" +
"                                p.Name as Producer, \n" +
"                                RTRIM(t.ABBR, '%') as Tax, \n" +
"                                d.PRICE6 as Price,\n" +
"                                q.QTYREST as Quantity,\n" +
"                                d.PRICE6 as PriceReserve,\n" +
"                                d.PRICE6 as PriceRreserveOrder,                               \n" +
"                                g.MORIONID as Code1,\n" +
"                                (select NVL(ID, 0) from FIRMS where OKPO=21642228) as Code2,\n" +
"                                (select NVL(ID, 0) from FIRMS where OKPO=21643699) as Code3,\n" +
"                                (select NVL(ID, 0) from FIRMS where OKPO=22946976) as Code4,\n" +
"                                (select NVL(ID, 0) from FIRMS where OKPO=25184975) as Code6,\n" +
"                                (select NVL(ID, 0) from FIRMS where OKPO=31816235) as Code7,\n" +
"                                (select NVL(ID, 0) from FIRMS where OKPO=21194014) as Code8,\n" +
"                                (select NVL(ID, 0) from FIRMS where OKPO=21947206) as Code9,\n" +
"                                (select NVL(ID, 0) from FIRMS where OKPO=13808034) as Code10,\n" +
"                                (select NVL(ID, 0) from FIRMS where OKPO=35431349) as Code11\n" +
"                             from QUANTITY q                                 \n" +
"                                INNER JOIN DOCCONTENTS d ON d.DOCID=q.DOCID AND d.GOODSID=q.GOODSID AND q.QTYREST=d.QTYREST\n" +
"                                INNER JOIN GOODS g ON q.GOODSID=g.ID\n" +
"                                INNER JOIN producers p ON p.ID=g.PRODUCERID                                \n" +
"                                INNER JOIN taxs t ON g.TAXID=t.ID                                                             \n" +
"                                order by q.GOODSNAME_UC ";
            //String query="select * from counters_daq where daq_dt = '2018-10-22'";
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();           
            Date date = new Date();            
            DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
            
            String Rest_DIR = System.getProperty("user.dir")+File.separator+"Rest";
            String SAMPLE_CSV_FILE = "Rest_"+serial+"_"+df.format(date);
            BufferedWriter writer;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Rest_DIR+File.separator+SAMPLE_CSV_FILE+".csv"), "Cp1251"));                
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                .withHeader("Code","Name","Producer","Tax","Price","Quantity","PriceReserve","PriceReserveOrder","Code1","Code2","Code3","Code4","Code6","Code7","Code8","Code9","Code10","Code11")                
                );
                
                while (rs.next()){
                    ArrayList values = new ArrayList();
                    for (int i=1; i<rsmd.getColumnCount(); i++){
                        String fieldValue;
                        fieldValue = rs.getString(i);
                        if ((i==2) || (i==3)){                            
                            String vc = fieldValue.replace(',', '.');                            
                            values.add(vc);
                            //csvPrinter.print(vc);
                        }else{
                            //csvPrinter.print(fieldValue);
                            values.add(fieldValue);
                        }
                    }                    
                    
                    values.add("");
                    csvPrinter.printRecord(values.toArray());
                }
                csvPrinter.flush();
                
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
            try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(Rest_DIR+File.separator+SAMPLE_CSV_FILE+".zip"));
                FileInputStream fis= new FileInputStream(Rest_DIR+File.separator+SAMPLE_CSV_FILE+".csv");)
                {
                    ZipEntry entry1=new ZipEntry(SAMPLE_CSV_FILE+".csv");
                    zout.putNextEntry(entry1);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                }catch(Exception ex){            
                    logger.log(Level.SEVERE, ex.getMessage());
                }            
            FTPClient ftp = null;
            ftp = new FTPClient();
            ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
            int reply;
            ftp.connect(ftpAddress);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
		logger.log(Level.SEVERE, "No reply from FTP-server. Connection should be terminated.");
                ftp.disconnect();			
            }
            if (ftp.login(ftpUser, ftpPassword)){
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                ftp.enterLocalPassiveMode();
                InputStream input = new FileInputStream(new File(Rest_DIR+File.separator+SAMPLE_CSV_FILE+".zip"));
                ftp.storeFile("" + SAMPLE_CSV_FILE+".zip", input);	
                if (ftp.isConnected()) {		
                    ftp.logout();
                    ftp.disconnect();		
                }
            }else{
                logger.log(Level.SEVERE, "FTP-server has not logged in. File has not transfered.");
            }
            logger.log(Level.INFO, "Schedule complete");            
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex.getLocalizedMessage());
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        
    }               
    
    private static List<ServerNode> getServers(NodeList list){
        ArrayList<ServerNode> servers = new ArrayList<>();
        for (int i=0; i<list.getLength(); i++){
            ServerNode server = new ServerNode(list.item(i));
            servers.add(server);
        }
        return servers;
    }
}
