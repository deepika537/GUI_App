
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author deepika
 */
public class FirstStatMain extends javax.swing.JFrame {

    /**
     * Creates new form FirstStatMain
     */
    private Connection connection = null;
    private PreparedStatement pst = null;
    private ResultSet rst = null;
    private byte[] person_img = null;
    private String file_path = null;

    public FirstStatMain() {
        initComponents();
        init();
        connection = JavaDbConnect.dbConnect();
        updateStudentInfotable();
        updateStudentShtInfotable();
        updateDocumentTable();
        currentDateandTime();

    }

    public void init() {
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    public void close() {
        WindowEvent winclosingevent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winclosingevent);
    }

    private void currentDateandTime() {
        Calendar cal = new GregorianCalendar();
        int day = cal.get(Calendar.MONTH);
        int month = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        int second = cal.get(Calendar.SECOND);
        int minute = cal.get(Calendar.MINUTE);
        int hour = cal.get(Calendar.HOUR);
        menuDate.setText("Today:" + month + "/" + day + "/" + year);
        menuDate.setForeground(Color.blue);
        menuTIme.setText("Time:" + hour + "/" + minute + "/" + second);
        menuTIme.setForeground(Color.red);
    }

    private void Statisticspart() {
        try {
            String sql = "select sum(Total)/3 as avg1 from Marks_info where Studentid=?";
            pst = connection.prepareStatement(sql);
            pst.setString(1, txtStudentId.getText());
            rst = pst.executeQuery();
            while (rst.next()) {
                txtMean.setText(rst.getString("avg1"));
                if (txtMean.getText() != null) {
                    if (Integer.parseInt(rst.getString("avg1")) >= 300) {
                        txtGPA.setText("4");
                    } else if (Integer.parseInt(rst.getString("avg1")) < 300 && Integer.parseInt(rst.getString("avg1")) >= 200) {
                        txtGPA.setText("3");
                    } else if (Integer.parseInt(rst.getString("avg1")) < 200 && Integer.parseInt(rst.getString("avg1")) >= 100) {
                        txtGPA.setText("2");
                    } else {
                        txtGPA.setText("D");
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
        try {
            String sql = "select sum(Total) as tot from Marks_info where Studentid=?";
            pst = connection.prepareStatement(sql);
            pst.setString(1, txtStudentId.getText());
            rst = pst.executeQuery();
            while (rst.next()) {
                txtTotal.setText(rst.getString("tot"));

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
        try {
            String selected = (String) combosubject.getSelectedItem();
            String sql = "select sum(" + selected + ") as stot from Marks_info where Studentid=?";
            pst = connection.prepareStatement(sql);
            pst.setString(1, txtStudentId.getText());
            rst = pst.executeQuery();
            while (rst.next()) {
                txtsTotal.setText(rst.getString("stot"));

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
        try {
            String selected = (String) combosubject.getSelectedItem();
            String sql = "select sum(" + selected + ") as stot from Marks_info where Studentid=?";
            pst = connection.prepareStatement(sql);
            pst.setString(1, txtStudentId.getText());
            rst = pst.executeQuery();
            while (rst.next()) {
                txtsTotal.setText(rst.getString("stot"));
                if (txtsTotal.getText() != null) {
                    if (Integer.parseInt(rst.getString("stot")) >= 250) {
                        txtsGrade.setText("A");
                    } else if (Integer.parseInt(rst.getString("stot")) < 250 && Integer.parseInt(rst.getString("stot")) >= 200) {
                        txtsGrade.setText("B");
                    } else if (Integer.parseInt(rst.getString("stot")) < 200 && Integer.parseInt(rst.getString("stot")) >= 100) {
                        txtsGrade.setText("C");
                    } else {
                        txtsGrade.setText("D");
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
        
    }

    private void updateStudentInfotable() {
        try {
            String sql = "select studentid,firstname,lastname,department,series,age,height,weight,gender,blood from Student_info";
            pst = connection.prepareStatement(sql);
            rst = pst.executeQuery();
            stTableInfo.setModel(DbUtils.resultSetToTableModel(rst));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }

    }

    private void updateStudentShtInfotable() {
        try {
            String sql = "select studentid,firstname from Student_info";
            pst = connection.prepareStatement(sql);
            rst = pst.executeQuery();
            stShortInfo.setModel(DbUtils.resultSetToTableModel(rst));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }

    }

    private void updateDocumentTable() {
        try {
            String sql = "select * from Document_info";
            pst = connection.prepareStatement(sql);
            rst = pst.executeQuery();
            Document_t.setModel(DbUtils.resultSetToTableModel(rst));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        btnHelp = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        stTableInfo = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        ageindicator = new javax.swing.JSlider();
        jLabel30 = new javax.swing.JLabel();
        heightindicator = new javax.swing.JSlider();
        jLabel31 = new javax.swing.JLabel();
        weightindicator = new javax.swing.JSlider();
        jPanel20 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tacomment = new javax.swing.JTextArea();
        jLabel33 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        stpiechart = new javax.swing.JButton();
        stlinechart = new javax.swing.JButton();
        stbarchart = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        allstpiechart = new javax.swing.JButton();
        allstLinechart = new javax.swing.JButton();
        allstbarchart = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        Document_t = new javax.swing.JTable();
        txtDocattach = new javax.swing.JTextField();
        btnDocAttach = new javax.swing.JButton();
        txtDocId = new javax.swing.JTextField();
        btnDocAdd = new javax.swing.JButton();
        txtDocsid = new javax.swing.JTextField();
        btnDocDelete = new javax.swing.JButton();
        txtDocname = new javax.swing.JTextField();
        btnDocClear = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtEmailfrom = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtEmailto = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtemailsbj = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tABody = new javax.swing.JTextArea();
        attachmentpath = new javax.swing.JTextField();
        btnattachment = new javax.swing.JButton();
        txtattachmentname = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btnsendemail = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();
        jPanel4 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        txtMean = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtGPA = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        txtsGrade = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtsTotal = new javax.swing.JTextField();
        combosubject = new javax.swing.JComboBox<>();
        jPanel18 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        txtstudents = new javax.swing.JTextField();
        combosubject1 = new javax.swing.JComboBox<>();
        txtavgmarks = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtlowmarks = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txthighmarks = new javax.swing.JTextField();
        comboexams = new javax.swing.JComboBox<>();
        showbutton = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        panelStudentInfo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtStudentId = new javax.swing.JTextField();
        txtStudentFname = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtStudentLname = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtStudentdept = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtStudentseries = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtStudentage = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtStudentheight = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtStudentweight = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtBlood = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        comboGender = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        stShortInfo = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        photolabel = new javax.swing.JLabel();
        btnUpload = new javax.swing.JButton();
        txtimagename = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mitClose = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mItScreenshot = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        mItOfflinehelp = new javax.swing.JMenuItem();
        mItWebhelp = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        menuDate = new javax.swing.JMenu();
        menuTIme = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1483575348_system-log-out.png"))); // NOI18N
        jButton1.setText("SignOut");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        btnHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1483655827_Help_Circle_Blue.png"))); // NOI18N
        btnHelp.setText("Help");
        btnHelp.setFocusable(false);
        btnHelp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHelp.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHelpActionPerformed(evt);
            }
        });
        jToolBar1.add(btnHelp);

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Action Panel", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 14))); // NOI18N

        jTabbedPane1.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N

        stTableInfo.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        stTableInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        stTableInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stTableInfoMouseClicked(evt);
            }
        });
        stTableInfo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                stTableInfoKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(stTableInfo);

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel29.setText("Age");

        ageindicator.setBackground(new java.awt.Color(204, 204, 255));

        jLabel30.setText("Height");

        heightindicator.setBackground(new java.awt.Color(204, 204, 255));

        jLabel31.setText("Weight");

        weightindicator.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel30)
                        .addComponent(jLabel29))
                    .addComponent(jLabel31))
                .addGap(27, 27, 27)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(weightindicator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(heightindicator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ageindicator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addComponent(ageindicator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(heightindicator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(weightindicator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addGap(27, 27, 27))
        );

        jPanel20.setBackground(new java.awt.Color(204, 204, 255));
        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Report", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 12))); // NOI18N

        jLabel32.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel32.setText("Performance");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setForeground(new java.awt.Color(51, 153, 0));
        jRadioButton1.setText("Good");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setForeground(new java.awt.Color(204, 0, 51));
        jRadioButton2.setText("Bad");

        jButton2.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jButton2.setText("Generate report");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        tacomment.setColumns(20);
        tacomment.setRows(5);
        jScrollPane5.setViewportView(tacomment);

        jLabel33.setText("Comment");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton1))
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton2)
                .addGap(18, 18, 18)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jRadioButton1)
                                .addComponent(jRadioButton2)
                                .addComponent(jLabel33))
                            .addComponent(jLabel32))
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 721, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(109, 109, 109)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Data Table", jPanel2);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Student", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 14))); // NOI18N

        stpiechart.setText("Pie Chart");
        stpiechart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stpiechartActionPerformed(evt);
            }
        });

        stlinechart.setText("Line Chart");
        stlinechart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stlinechartActionPerformed(evt);
            }
        });

        stbarchart.setText("Bar Chart");
        stbarchart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stbarchartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(stbarchart)
                    .addComponent(stlinechart)
                    .addComponent(stpiechart))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(stpiechart)
                .addGap(13, 13, 13)
                .addComponent(stbarchart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(stlinechart)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "All Students", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 14))); // NOI18N

        allstpiechart.setText("Pie Chart");
        allstpiechart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allstpiechartActionPerformed(evt);
            }
        });

        allstLinechart.setText("Line Chart");
        allstLinechart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allstLinechartActionPerformed(evt);
            }
        });

        allstbarchart.setText("Bar Chart");
        allstbarchart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allstbarchartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(allstbarchart)
                    .addComponent(allstLinechart)
                    .addComponent(allstpiechart))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(allstpiechart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(allstbarchart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(allstLinechart)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(322, 322, 322)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(470, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );

        jTabbedPane1.addTab("Charts", jPanel3);

        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Document_t.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        Document_t.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Document_tMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(Document_t);

        jPanel13.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(451, 31, 744, 184));

        txtDocattach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocattachActionPerformed(evt);
            }
        });
        jPanel13.add(txtDocattach, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 50, 246, -1));

        btnDocAttach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1483749576_Upload.png"))); // NOI18N
        btnDocAttach.setText("Attach");
        btnDocAttach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDocAttachActionPerformed(evt);
            }
        });
        jPanel13.add(btnDocAttach, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, -1, -1));

        txtDocId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocIdActionPerformed(evt);
            }
        });
        jPanel13.add(txtDocId, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 96, 125, -1));

        btnDocAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1483671399_sign-add.png"))); // NOI18N
        btnDocAdd.setText("Add");
        btnDocAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDocAddActionPerformed(evt);
            }
        });
        jPanel13.add(btnDocAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 96, 69, -1));

        txtDocsid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocsidActionPerformed(evt);
            }
        });
        jPanel13.add(txtDocsid, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 142, 125, -1));

        btnDocDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1483671498_Line_ui_icons_Svg-03.png"))); // NOI18N
        btnDocDelete.setText("Delete");
        btnDocDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDocDeleteActionPerformed(evt);
            }
        });
        jPanel13.add(btnDocDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 142, -1, -1));

        txtDocname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocnameActionPerformed(evt);
            }
        });
        jPanel13.add(txtDocname, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 176, 125, -1));

        btnDocClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1483671532_edit-clear.png"))); // NOI18N
        btnDocClear.setText("Clear");
        btnDocClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDocClearActionPerformed(evt);
            }
        });
        jPanel13.add(btnDocClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 176, 69, -1));

        jLabel17.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel17.setText("Document Id");
        jPanel13.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 96, -1, -1));

        jLabel18.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel18.setText("StudentId");
        jPanel13.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 137, -1, -1));

        jLabel19.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel19.setText("Document Name");
        jPanel13.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 182, -1, -1));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Documents", jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel12.setText("From");
        jPanel12.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 23, -1, -1));

        txtEmailfrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailfromActionPerformed(evt);
            }
        });
        jPanel12.add(txtEmailfrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 17, 123, -1));

        jLabel13.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel13.setText("Password");
        jPanel12.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 55, 85, -1));

        jLabel14.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel14.setText("To");
        jPanel12.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 93, -1, -1));
        jPanel12.add(txtEmailto, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 87, 123, -1));

        jLabel15.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel15.setText("Subject");
        jPanel12.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 132, -1, -1));
        jPanel12.add(txtemailsbj, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 126, 123, -1));

        tABody.setColumns(20);
        tABody.setRows(5);
        jScrollPane3.setViewportView(tABody);

        jPanel12.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(272, 17, 422, 96));
        jPanel12.add(attachmentpath, new org.netbeans.lib.awtextra.AbsoluteConstraints(278, 126, 192, -1));

        btnattachment.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        btnattachment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1483755675_email_attach.png"))); // NOI18N
        btnattachment.setText("Attach");
        btnattachment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnattachmentActionPerformed(evt);
            }
        });
        jPanel12.add(btnattachment, new org.netbeans.lib.awtextra.AbsoluteConstraints(488, 125, 109, 29));

        txtattachmentname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtattachmentnameActionPerformed(evt);
            }
        });
        jPanel12.add(txtattachmentname, new org.netbeans.lib.awtextra.AbsoluteConstraints(278, 160, 192, -1));

        jLabel16.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 51, 102));
        jLabel16.setText("Attachment name");
        jPanel12.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(502, 166, -1, -1));

        btnsendemail.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnsendemail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1483755578_aiga_mail.png"))); // NOI18N
        btnsendemail.setText("Send Email");
        btnsendemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsendemailActionPerformed(evt);
            }
        });
        jPanel12.add(btnsendemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(373, 198, 165, 39));
        jPanel12.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 49, 123, -1));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(331, Short.MAX_VALUE)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 725, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(221, 221, 221))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Email", jPanel6);

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "All Exams", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 14))); // NOI18N

        jLabel20.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel20.setText("Mean");

        jLabel21.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel21.setText("Total");

        jLabel22.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel22.setText("GPA");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                        .addComponent(txtGPA, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtMean, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMean, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addGap(33, 33, 33)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtGPA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "All Subjects", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 14))); // NOI18N

        jLabel23.setText("Total");

        txtsGrade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsGradeActionPerformed(evt);
            }
        });

        jLabel24.setText("Grade");

        txtsTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsTotalActionPerformed(evt);
            }
        });

        combosubject.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "English", "Science", "Social", "Math", " " }));
        combosubject.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combosubjectItemStateChanged(evt);
            }
        });
        combosubject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combosubjectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(combosubject, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                                .addComponent(txtsGrade, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtsTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24))))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(combosubject, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtsTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(26, 26, 26)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtsGrade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel25.setText("Number of Students");

        combosubject1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "English", "Science", "Social", "Math", " " }));
        combosubject1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combosubject1ItemStateChanged(evt);
            }
        });
        combosubject1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combosubject1ActionPerformed(evt);
            }
        });

        jLabel26.setText("Average Marks");

        jLabel27.setText("Lowest Marks");

        jLabel28.setText("Highest Marks");

        comboexams.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "exam1", "exam2", "exam3", " " }));

        showbutton.setText("Show");
        showbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showbuttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel28)
                            .addComponent(jLabel27)))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel25)))
                .addGap(32, 32, 32)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtavgmarks, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                            .addComponent(txtlowmarks)
                            .addComponent(txthighmarks))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(txtstudents, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(combosubject1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboexams, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(showbutton)
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combosubject1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboexams, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showbutton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txtstudents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtavgmarks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(txtlowmarks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txthighmarks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 129, Short.MAX_VALUE)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(117, 117, 117))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28))
        );

        jTabbedPane1.addTab("Statistics", jPanel4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel11.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 102));
        jLabel11.setText("Welcome to Statistics Software");

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1483670827_documents-07.png"))); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        txtSearch.setText("Search..");
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Commands", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 14), new java.awt.Color(0, 51, 102))); // NOI18N

        btnAdd.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1483671399_sign-add.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1483671465_edit.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1483671498_Line_ui_icons_Svg-03.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1483671532_edit-clear.png"))); // NOI18N
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                    .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEdit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClear)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtSearch)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearch)
                        .addGap(16, 16, 16))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSearch)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelStudentInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Student Info", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 14), new java.awt.Color(0, 51, 102))); // NOI18N

        jLabel1.setText("StudentId");

        txtStudentId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStudentIdActionPerformed(evt);
            }
        });

        txtStudentFname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStudentFnameActionPerformed(evt);
            }
        });

        jLabel2.setText("FirstName");

        txtStudentLname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStudentLnameActionPerformed(evt);
            }
        });

        jLabel3.setText("LastName");

        txtStudentdept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStudentdeptActionPerformed(evt);
            }
        });

        jLabel4.setText("Department");

        txtStudentseries.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStudentseriesActionPerformed(evt);
            }
        });

        jLabel5.setText("Series");

        txtStudentage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStudentageActionPerformed(evt);
            }
        });

        jLabel6.setText("Age");

        txtStudentheight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStudentheightActionPerformed(evt);
            }
        });

        jLabel7.setText("Height");

        txtStudentweight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStudentweightActionPerformed(evt);
            }
        });

        jLabel8.setText("Weight");

        jLabel9.setText("Gender");

        txtBlood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBloodActionPerformed(evt);
            }
        });

        jLabel10.setText("Blood");

        comboGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));

        javax.swing.GroupLayout panelStudentInfoLayout = new javax.swing.GroupLayout(panelStudentInfo);
        panelStudentInfo.setLayout(panelStudentInfoLayout);
        panelStudentInfoLayout.setHorizontalGroup(
            panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStudentInfoLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelStudentInfoLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtStudentseries, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelStudentInfoLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtStudentdept, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelStudentInfoLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtStudentLname, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelStudentInfoLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtStudentFname, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelStudentInfoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtStudentId, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46)
                .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panelStudentInfoLayout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addGap(18, 18, 18)
                            .addComponent(txtStudentage, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelStudentInfoLayout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addGap(18, 18, 18)
                            .addComponent(txtStudentheight, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelStudentInfoLayout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addGap(18, 18, 18)
                            .addComponent(txtStudentweight, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(panelStudentInfoLayout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtBlood, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelStudentInfoLayout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addGap(18, 18, 18)
                            .addComponent(comboGender, 0, 111, Short.MAX_VALUE))))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        panelStudentInfoLayout.setVerticalGroup(
            panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStudentInfoLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtStudentId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtStudentage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelStudentInfoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtStudentFname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelStudentInfoLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtStudentheight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelStudentInfoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtStudentLname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtStudentdept, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelStudentInfoLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtStudentweight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(comboGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel10)
                        .addComponent(txtBlood, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtStudentseries, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(0, 204, 204));
        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        stShortInfo.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        stShortInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        stShortInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stShortInfoMouseClicked(evt);
            }
        });
        stShortInfo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                stShortInfoKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(stShortInfo);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(photolabel, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(photolabel, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        btnUpload.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        btnUpload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1483749576_Upload.png"))); // NOI18N
        btnUpload.setText("Upload");
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });

        txtimagename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtimagenameActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1483749602_floppy_disk_save.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(txtimagename, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpload)
                .addGap(0, 12, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpload)
                    .addComponent(txtimagename, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        jMenu1.setText("File");

        mitClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1483572775_Close_Icon_Dark.png"))); // NOI18N
        mitClose.setText("Close");
        mitClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitCloseActionPerformed(evt);
            }
        });
        jMenu1.add(mitClose);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1483575348_system-log-out.png"))); // NOI18N
        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        mItScreenshot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1483677160_camera_digital_snapshot_screenshot_memory_life.png"))); // NOI18N
        mItScreenshot.setText("Screenshot");
        mItScreenshot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItScreenshotActionPerformed(evt);
            }
        });
        jMenu2.add(mItScreenshot);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Help");

        mItOfflinehelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1483655827_Help_Circle_Blue.png"))); // NOI18N
        mItOfflinehelp.setText("Offline Help");
        mItOfflinehelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItOfflinehelpActionPerformed(evt);
            }
        });
        jMenu3.add(mItOfflinehelp);

        mItWebhelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1483668646_Internet_Line-02.png"))); // NOI18N
        mItWebhelp.setText("Web Help");
        mItWebhelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItWebhelpActionPerformed(evt);
            }
        });
        jMenu3.add(mItWebhelp);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("About");
        jMenuBar1.add(jMenu4);

        menuDate.setText("Date");
        menuDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDateActionPerformed(evt);
            }
        });
        jMenuBar1.add(menuDate);

        menuTIme.setText("Time");
        jMenuBar1.add(menuTIme);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelStudentInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelStudentInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mitCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitCloseActionPerformed
        try {
            close();
            LogIn obj = new LogIn();
            obj.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        } finally {
            try {
                rst.close();
                pst.close();
                connection.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, e);
            }
        }
    }//GEN-LAST:event_mitCloseActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            close();
            LogIn obj = new LogIn();
            obj.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        } finally {
            try {
                rst.close();
                pst.close();
                connection.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, e);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtStudentIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStudentIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStudentIdActionPerformed

    private void txtStudentFnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStudentFnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStudentFnameActionPerformed

    private void txtStudentLnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStudentLnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStudentLnameActionPerformed

    private void txtStudentdeptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStudentdeptActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStudentdeptActionPerformed

    private void txtStudentseriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStudentseriesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStudentseriesActionPerformed

    private void txtStudentageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStudentageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStudentageActionPerformed

    private void txtStudentheightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStudentheightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStudentheightActionPerformed

    private void txtStudentweightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStudentweightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStudentweightActionPerformed

    private void txtBloodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBloodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBloodActionPerformed
    private void getValue() {
        try {
            txtStudentId.setText(rst.getString("studentid"));
            txtStudentFname.setText(rst.getString("firstname"));
            txtStudentLname.setText(rst.getString("lastname"));
            txtStudentdept.setText(rst.getString("department"));
            txtStudentseries.setText(rst.getString("series"));
            txtStudentage.setText(rst.getString("age"));
            txtStudentheight.setText(rst.getString("height"));
            txtStudentweight.setText(rst.getString("weight"));
            comboGender.setSelectedItem(rst.getString("gender"));
            txtBlood.setText(rst.getString("blood"));
            ageindicator.setValue((int)Double.parseDouble(rst.getString("age")));
            heightindicator.setValue((int)Double.parseDouble(rst.getString("height")));
            weightindicator.setValue((int)Double.parseDouble(rst.getString("weight")));
            byte[] imagedata = rst.getBytes("photo");
            ImageIcon frmt = new ImageIcon(scaledImage(imagedata, photolabel.getWidth(), photolabel.getHeight()));
            photolabel.setIcon(frmt);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
    }

    private Image scaledImage(byte[] img, int w, int h) {
        BufferedImage sizechange = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        try {
            Graphics2D g2d = sizechange.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            ByteArrayInputStream input = new ByteArrayInputStream(img);
            BufferedImage bfg = ImageIO.read(input);
            g2d.drawImage(bfg, 0, 0, w, h, null);
            g2d.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);

        }
        return sizechange;
    }
    private void stShortInfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stShortInfoMouseClicked
        try {
            int row = stShortInfo.getSelectedRow();
            String rowselected = (stShortInfo.getModel().getValueAt(row, 0).toString());
            String sql = "select * from Student_info where studentid='" + rowselected + "'";
            pst = connection.prepareStatement(sql);
            rst = pst.executeQuery();
            if (rst.next()) {
                getValue();

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
        Statisticspart();
    }//GEN-LAST:event_stShortInfoMouseClicked

    private void stShortInfoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stShortInfoKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            try {
                int row = stShortInfo.getSelectedRow();
                String tableclick = (stShortInfo.getModel().getValueAt(row, 0).toString());
                String sql = "select * from Student_info where studentid='" + tableclick + "'";
                pst = connection.prepareStatement(sql);
                rst = pst.executeQuery();
                if (rst.next()) {
                    getValue();

                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, ex);
            }
        }
    }//GEN-LAST:event_stShortInfoKeyReleased

    private void stTableInfoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stTableInfoKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            try {
                int row = stTableInfo.getSelectedRow();
                String tableclick = (stTableInfo.getModel().getValueAt(row, 0).toString());
                String sql = "select * from Student_info where studentid='" + tableclick + "'";
                pst = connection.prepareStatement(sql);
                rst = pst.executeQuery();
                if (rst.next()) {
                    getValue();

                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, ex);
            }
        }
    }//GEN-LAST:event_stTableInfoKeyReleased

    private void stTableInfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stTableInfoMouseClicked
        try {
            int row = stTableInfo.getSelectedRow();
            String tableclick = (stTableInfo.getModel().getValueAt(row, 0).toString());
            String sql = "select * from Student_info where studentid='" + tableclick + "'";
            pst = connection.prepareStatement(sql);
            rst = pst.executeQuery();
            if (rst.next()) {
                getValue();

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }

    }//GEN-LAST:event_stTableInfoMouseClicked

    private void mItOfflinehelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItOfflinehelpActionPerformed
        try {
            File file = new File("/Files/Help.pdf");
            Runtime.getRuntime().exec(new String[]{"/usr/bin/open", file.getAbsolutePath()});
            //Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+"/Files/Help.pdf");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_mItOfflinehelpActionPerformed
    //to get the screenshot of panels

    private void mItWebhelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItWebhelpActionPerformed
        try {
            String url = "https://www.google.com";
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (IOException ex) {
            //Logger.getLogger(FirstStatMain.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, ex);
        }
    }//GEN-LAST:event_mItWebhelpActionPerformed

    private void btnHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHelpActionPerformed
        try {
            File file = new File("/Files/Help.pdf");
            Runtime.getRuntime().exec(new String[]{"/usr/bin/open", file.getAbsolutePath()});
            //Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+"/Files/Help.pdf");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_btnHelpActionPerformed

    private void menuDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuDateActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        try {
            File file = new File("/Files/Help.pdf");
            Runtime.getRuntime().exec(new String[]{"/usr/bin/open", file.getAbsolutePath()});
            //Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+"/Files/Help.pdf");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed

        txtStudentId.setText(null);
        txtStudentFname.setText(null);
        txtStudentLname.setText(null);
        txtStudentdept.setText(null);
        txtStudentseries.setText(null);
        txtStudentage.setText(null);
        txtStudentheight.setText(null);
        txtStudentweight.setText(null);
        comboGender.setSelectedItem("Male");
        txtBlood.setText(null);

    }//GEN-LAST:event_btnClearActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        String sql = "select * from Student_info where firstname=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, txtSearch.getText());
            rst = pst.executeQuery();
            if (rst.next()) {
                getValue();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
        String sql1 = "select * from Student_info where studentid=?";
        try {
            pst = connection.prepareStatement(sql1);
            pst.setString(1, txtSearch.getText());
            rst = pst.executeQuery();
            if (rst.next()) {
                getValue();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        try {
            String sql = "insert into Student_info(studentid,firstname,lastname,department,series,age,height,weight,gender,blood) values(?,?,?,?,?,?,?,?,?,?)";
            pst = connection.prepareStatement(sql);
            pst.setString(1, txtStudentId.getText());
            pst.setString(2, txtStudentFname.getText());
            pst.setString(3, txtStudentLname.getText());
            pst.setString(4, txtStudentdept.getText());
            pst.setString(5, txtStudentseries.getText());
            pst.setString(6, txtStudentage.getText());
            pst.setString(7, txtStudentheight.getText());
            pst.setString(8, txtStudentweight.getText());
            pst.setString(9, (String) comboGender.getSelectedItem());
            pst.setString(10, txtBlood.getText());
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, "saved");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
        updateStudentInfotable();
        updateStudentShtInfotable();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        try {
            String sql = "Update Student_info set firstname=?,lastname=?,department=?,series=?,age=?,height=?,weight=?,gender=?,blood=? where studentid=?";
            pst = connection.prepareStatement(sql);
            pst.setString(1, txtStudentFname.getText());
            pst.setString(2, txtStudentLname.getText());
            pst.setString(3, txtStudentdept.getText());
            pst.setString(4, txtStudentseries.getText());
            pst.setString(5, txtStudentage.getText());
            pst.setString(6, txtStudentheight.getText());
            pst.setString(7, txtStudentweight.getText());
            pst.setString(8, (String) comboGender.getSelectedItem());
            pst.setString(9, txtBlood.getText());
            pst.setString(10, txtStudentId.getText());
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, "Updated");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
        updateStudentInfotable();
        updateStudentShtInfotable();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int s = JOptionPane.showConfirmDialog(rootPane, "Do you really want to Delete?", "Delete", JOptionPane.YES_NO_OPTION);
        if (s == 0) {
            String sql = "delete from Student_info where studentid=?";
            try {
                pst = connection.prepareStatement(sql);
                pst.setString(1, txtStudentId.getText());
                pst.execute();
                JOptionPane.showMessageDialog(rootPane, "deleted");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, e);
            }
            updateStudentInfotable();
            updateStudentShtInfotable();
        }

    }//GEN-LAST:event_btnDeleteActionPerformed
    private static BufferedImage getScreenShot(Component com) {
        BufferedImage image = new BufferedImage(com.getWidth(), com.getHeight(), BufferedImage.TYPE_INT_RGB);
        com.paint(image.getGraphics());
        return image;
    }

    //to save the image
    private static void saveScreenShot(Component com, String fileName) throws Exception {
        BufferedImage img = getScreenShot(com);
        ImageIO.write(img, "png", new File(fileName));
    }
    private void mItScreenshotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItScreenshotActionPerformed
        try {
            saveScreenShot(panelStudentInfo, "PanelImg.png");
            JOptionPane.showMessageDialog(rootPane, "Image is saved");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_mItScreenshotActionPerformed

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed
        JFileChooser jch = new JFileChooser();
        jch.showOpenDialog(null);

        File imgfile = jch.getSelectedFile();
        String filename = imgfile.getAbsolutePath();
        txtimagename.setText(filename);
        try {
            FileInputStream fis = new FileInputStream(imgfile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);

            }
            person_img = bos.toByteArray();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_btnUploadActionPerformed

    private void txtimagenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtimagenameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtimagenameActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        try {
            String sql = "update Student_info set photo=? where studentid=?";
            pst = connection.prepareStatement(sql);
            pst.setBytes(1, person_img);
            pst.setString(2, txtStudentId.getText());
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, "Image saved");
        } catch (SQLException ex) {
            Logger.getLogger(FirstStatMain.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtEmailfromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailfromActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailfromActionPerformed

    private void txtattachmentnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtattachmentnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtattachmentnameActionPerformed

    private void btnsendemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsendemailActionPerformed
        final String senderemail = txtEmailfrom.getText();
        final String sendPass = txtPassword.getText();
        String send_to = txtEmailto.getText();
        String email_sub = txtemailsbj.getText();
        String email_body = tABody.getText();

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(prop,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderemail, sendPass);
            }
        }
        );
        try {
            /* Message Header*/
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderemail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(send_to));
            message.setSubject(email_sub);
            /*setting text message*/
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(email_body);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            /*attaching file*/
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(file_path);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(txtattachmentname.getText());
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            Transport.send(message);
            JOptionPane.showMessageDialog(rootPane, "Message sent");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_btnsendemailActionPerformed

    private void btnattachmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnattachmentActionPerformed
        JFileChooser jch = new JFileChooser();
        jch.showOpenDialog(null);

        File imgfile = jch.getSelectedFile();
        file_path = imgfile.getAbsolutePath();
        attachmentpath.setText(file_path);
        txtattachmentname.setText(file_path);
    }//GEN-LAST:event_btnattachmentActionPerformed

    private void txtDocattachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocattachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDocattachActionPerformed

    private void txtDocIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDocIdActionPerformed

    private void txtDocsidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocsidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDocsidActionPerformed

    private void txtDocnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDocnameActionPerformed

    private void btnDocAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDocAddActionPerformed
        try {
            String sql = "insert into Document_info(documentid,studentid,documentname,path) values(?,?,?,?)";
            pst = connection.prepareStatement(sql);
            pst.setString(1, txtDocId.getText());
            pst.setString(2, txtDocsid.getText());
            pst.setString(3, txtDocname.getText());
            pst.setString(4, txtDocattach.getText());
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, "saved");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
        updateDocumentTable();

    }//GEN-LAST:event_btnDocAddActionPerformed

    private void btnDocAttachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDocAttachActionPerformed
        JFileChooser jch = new JFileChooser();
        jch.showOpenDialog(null);

        File imgfile = jch.getSelectedFile();
        String Docfile_path = imgfile.getAbsolutePath();
        txtDocattach.setText(Docfile_path);

    }//GEN-LAST:event_btnDocAttachActionPerformed

    private void Document_tMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Document_tMouseClicked
        try {
            int row = Document_t.getSelectedRow();
            String rowselected = (Document_t.getModel().getValueAt(row, 0).toString());
            String sql = "select * from Document_info where documentid='" + rowselected + "'";
            pst = connection.prepareStatement(sql);
            rst = pst.executeQuery();
            if (rst.next()) {
                txtDocId.setText(rst.getString("documentid"));
                txtDocsid.setText(rst.getString("studentid"));
                txtDocname.setText(rst.getString("documentname"));
                txtDocattach.setText(rst.getString("path"));

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
        try {
            int row = Document_t.getSelectedRow();
            String rowselected = (Document_t.getModel().getValueAt(row, 3).toString());
            Runtime.getRuntime().exec("rundll32 url.dll.FileProtocolHandler " + rowselected);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);

        }

    }//GEN-LAST:event_Document_tMouseClicked

    private void btnDocDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDocDeleteActionPerformed
        int s = JOptionPane.showConfirmDialog(rootPane, "Do you really want to Delete?", "Delete", JOptionPane.YES_NO_OPTION);
        if (s == 0) {
            String sql = "delete from Document_info where documentid=?";
            try {
                pst = connection.prepareStatement(sql);
                pst.setString(1, txtDocId.getText());
                pst.execute();
                JOptionPane.showMessageDialog(rootPane, "deleted");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, e);
            }
            updateDocumentTable();
        }


    }//GEN-LAST:event_btnDocDeleteActionPerformed

    private void btnDocClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDocClearActionPerformed
        txtDocId.setText(null);
        txtDocsid.setText(null);
        txtDocname.setText(null);
        txtDocattach.setText(null);
    }//GEN-LAST:event_btnDocClearActionPerformed

    private void stpiechartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stpiechartActionPerformed
        String sql = "select English,Science,Social,Math from Marks_info where Studentid=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, txtStudentId.getText());
            rst = pst.executeQuery();
            DefaultPieDataset dataset = new DefaultPieDataset();
            while (rst.next()) {
                dataset.setValue("English", Integer.parseInt(rst.getString("English")));
                dataset.setValue("Science", Integer.parseInt(rst.getString("Science")));
                dataset.setValue("Social", Integer.parseInt(rst.getString("Social")));
                dataset.setValue("Math", Integer.parseInt(rst.getString("Math")));

            }
            JFreeChart pchart = ChartFactory.createPieChart("Pie Chart", dataset, true, true, true);
            PiePlot P = (PiePlot) pchart.getPlot();
            ChartFrame cf = new ChartFrame("Pie Chart", pchart);
            cf.setSize(450, 450);
            cf.setVisible(true);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
    }//GEN-LAST:event_stpiechartActionPerformed

    private void allstpiechartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allstpiechartActionPerformed
        String sql = "select Studentid,Total from Marks_info";
        try {
            pst = connection.prepareStatement(sql);
            //pst.setString(1, txtStudentId.getText());
            rst = pst.executeQuery();
            DefaultPieDataset dataset = new DefaultPieDataset();
            while (rst.next()) {
                dataset.setValue(rst.getString("Studentid"), Integer.parseInt(rst.getString("Total")));
                //dataset.setValue(rst.getString("Studentid"),Integer.parseInt(rst.getString("Science")));
                //dataset.setValue(rst.getString("Studentid"),Integer.parseInt(rst.getString("Social")));
                //dataset.setValue(rst.getString("Studentid"),Integer.parseInt(rst.getString("Math")));

            }
            JFreeChart pchart = ChartFactory.createPieChart("Pie Chart", dataset, true, true, true);
            PiePlot P = (PiePlot) pchart.getPlot();
            ChartFrame cf = new ChartFrame("Pie Chart", pchart);
            cf.setSize(450, 450);
            cf.setVisible(true);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }

    }//GEN-LAST:event_allstpiechartActionPerformed

    private void stbarchartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stbarchartActionPerformed
        String sql = "select Studentid,English,Science,Social,Math from Marks_info where Studentid=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, txtStudentId.getText());
            rst = pst.executeQuery();
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            while (rst.next()) {
                dataset.setValue(Integer.parseInt(rst.getString("English")), rst.getString("Studentid"), "English");
                dataset.setValue(Integer.parseInt(rst.getString("Science")), rst.getString("Studentid"), "Science");
                dataset.setValue(Integer.parseInt(rst.getString("Social")), rst.getString("Studentid"), "Social");
                dataset.setValue(Integer.parseInt(rst.getString("Math")), rst.getString("Studentid"), "Math");

            }
            JFreeChart pchart = ChartFactory.createBarChart("Bar Chart", "Subject", "Marks", dataset, PlotOrientation.VERTICAL,
                    true, true, false);
            CategoryPlot P = (CategoryPlot) pchart.getCategoryPlot();
            P.setRangeGridlinePaint(Color.BLACK);
            ChartFrame cf = new ChartFrame("Bar Chart", pchart);
            cf.setSize(450, 450);
            cf.setVisible(true);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }

    }//GEN-LAST:event_stbarchartActionPerformed

    private void stlinechartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stlinechartActionPerformed
        String sql = "select Studentid,English,Science,Social,Math from Marks_info where Studentid=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, txtStudentId.getText());
            rst = pst.executeQuery();
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            while (rst.next()) {
                dataset.setValue(Integer.parseInt(rst.getString("English")), rst.getString("Studentid"), "English");
                dataset.setValue(Integer.parseInt(rst.getString("Science")), rst.getString("Studentid"), "Science");
                dataset.setValue(Integer.parseInt(rst.getString("Social")), rst.getString("Studentid"), "Social");
                dataset.setValue(Integer.parseInt(rst.getString("Math")), rst.getString("Studentid"), "Math");

            }
            JFreeChart pchart = ChartFactory.createLineChart("Line Chart", "Subject", "Marks", dataset, PlotOrientation.VERTICAL,
                    true, true, false);
            CategoryPlot P = (CategoryPlot) pchart.getCategoryPlot();
            P.setRangeGridlinePaint(Color.BLACK);
            ChartFrame cf = new ChartFrame("Line Chart", pchart);
            cf.setSize(450, 450);
            cf.setVisible(true);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }

    }//GEN-LAST:event_stlinechartActionPerformed

    private void allstbarchartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allstbarchartActionPerformed
        String sql = "select Studentid,English,Science,Social,Math from Marks_info";
        try {
            pst = connection.prepareStatement(sql);
            //pst.setString(1, txtStudentId.getText());
            rst = pst.executeQuery();
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            while (rst.next()) {
                dataset.setValue(Integer.parseInt(rst.getString("English")), rst.getString("Studentid"), "English");
                dataset.setValue(Integer.parseInt(rst.getString("Science")), rst.getString("Studentid"), "Science");
                dataset.setValue(Integer.parseInt(rst.getString("Social")), rst.getString("Studentid"), "Social");
                dataset.setValue(Integer.parseInt(rst.getString("Math")), rst.getString("Studentid"), "Math");

            }
            JFreeChart pchart = ChartFactory.createBarChart("Bar Chart", "Subject", "Marks", dataset, PlotOrientation.VERTICAL,
                    true, true, false);
            CategoryPlot P = (CategoryPlot) pchart.getCategoryPlot();
            P.setRangeGridlinePaint(Color.BLACK);
            ChartFrame cf = new ChartFrame("Bar Chart", pchart);
            cf.setSize(450, 450);
            cf.setVisible(true);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }

    }//GEN-LAST:event_allstbarchartActionPerformed

    private void allstLinechartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allstLinechartActionPerformed
        String sql = "select Studentid,English,Science,Social,Math from Marks_info";
        try {
            pst = connection.prepareStatement(sql);
            //pst.setString(1, txtStudentId.getText());
            rst = pst.executeQuery();
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            while (rst.next()) {
                dataset.setValue(Integer.parseInt(rst.getString("English")), rst.getString("Studentid"), "English");
                dataset.setValue(Integer.parseInt(rst.getString("Science")), rst.getString("Studentid"), "Science");
                dataset.setValue(Integer.parseInt(rst.getString("Social")), rst.getString("Studentid"), "Social");
                dataset.setValue(Integer.parseInt(rst.getString("Math")), rst.getString("Studentid"), "Math");

            }
            JFreeChart pchart = ChartFactory.createLineChart("Line Chart", "Subject", "Marks", dataset, PlotOrientation.VERTICAL,
                    true, true, false);
            CategoryPlot P = (CategoryPlot) pchart.getCategoryPlot();
            P.setRangeGridlinePaint(Color.BLACK);
            ChartFrame cf = new ChartFrame("Line Chart", pchart);
            cf.setSize(450, 450);
            cf.setVisible(true);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }

    }//GEN-LAST:event_allstLinechartActionPerformed

    private void txtsGradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsGradeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsGradeActionPerformed

    private void txtsTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsTotalActionPerformed

    private void combosubjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combosubjectActionPerformed

    }//GEN-LAST:event_combosubjectActionPerformed

    private void combosubjectItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combosubjectItemStateChanged
        try {
            String selected = (String) combosubject.getSelectedItem();
            String sql = "select sum(" + selected + ") as stot from Marks_info where Studentid=?";
            pst = connection.prepareStatement(sql);
            pst.setString(1, txtStudentId.getText());
            rst = pst.executeQuery();
            while (rst.next()) {
                txtsTotal.setText(rst.getString("stot"));

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
        try {
            String selected = (String) combosubject.getSelectedItem();
            String sql = "select sum(" + selected + ") as stot from Marks_info where Studentid=?";
            pst = connection.prepareStatement(sql);
            pst.setString(1, txtStudentId.getText());
            rst = pst.executeQuery();
            while (rst.next()) {
                txtsTotal.setText(rst.getString("stot"));
                if (txtsTotal.getText() != null) {
                    if (Integer.parseInt(rst.getString("stot")) >= 250) {
                        txtsGrade.setText("A");
                    } else if (Integer.parseInt(rst.getString("stot")) < 250 && Integer.parseInt(rst.getString("stot")) >= 200) {
                        txtsGrade.setText("B");
                    } else if (Integer.parseInt(rst.getString("stot")) < 200 && Integer.parseInt(rst.getString("stot")) >= 100) {
                        txtsGrade.setText("C");
                    } else {
                        txtsGrade.setText("D");
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
    }//GEN-LAST:event_combosubjectItemStateChanged

    private void combosubject1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combosubject1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_combosubject1ItemStateChanged

    private void combosubject1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combosubject1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combosubject1ActionPerformed

    private void showbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showbuttonActionPerformed
    try {
            String examselected = (String) comboexams.getSelectedItem();
            String subjectselected = (String) combosubject1.getSelectedItem();
            String sql = "select count(*) as numst,sum("+subjectselected+")/count(*) as avgsubmarks from Marks_info where Exams=?";
            pst = connection.prepareStatement(sql);
            pst.setString(1, examselected);
            rst = pst.executeQuery();
            while (rst.next()) {
                txtstudents.setText(rst.getString("numst"));
                txtavgmarks.setText(rst.getString("avgsubmarks"));

            }
            String sql1 = "select "+subjectselected+" as submarks from Marks_info where Exams=? order by submarks limit 1";
            pst = connection.prepareStatement(sql1);
            pst.setString(1, examselected);
            rst = pst.executeQuery();
            while (rst.next()) {
                txtlowmarks.setText(rst.getString("submarks"));
            }
            String sql2 = "select "+subjectselected+" as hsubmarks from Marks_info where Exams=? order by hsubmarks desc limit 1";
            pst = connection.prepareStatement(sql2);
            pst.setString(1, examselected);
            rst = pst.executeQuery();
            while (rst.next()) {
                txthighmarks.setText(rst.getString("hsubmarks"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
    }//GEN-LAST:event_showbuttonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
 Document document = new Document();
      try
      {
         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("HelloWorld.pdf"));
         document.open();
         document.add(new Paragraph("A Hello World PDF document."));
         document.add(new Paragraph(tacomment.getText()));
         if(jRadioButton1.isSelected())
          document.add(new Paragraph("Performance Status:Good"));
         else
          document.add(new Paragraph("Performance Status:Bad"));   
         document.close();
         writer.close();
      } catch (DocumentException e)
      {
         e.printStackTrace();
      } catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }
    }//GEN-LAST:event_jButton2ActionPerformed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FirstStatMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FirstStatMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FirstStatMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FirstStatMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FirstStatMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Document_t;
    private javax.swing.JSlider ageindicator;
    private javax.swing.JButton allstLinechart;
    private javax.swing.JButton allstbarchart;
    private javax.swing.JButton allstpiechart;
    private javax.swing.JTextField attachmentpath;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDocAdd;
    private javax.swing.JButton btnDocAttach;
    private javax.swing.JButton btnDocClear;
    private javax.swing.JButton btnDocDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHelp;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpload;
    private javax.swing.JButton btnattachment;
    private javax.swing.JButton btnsendemail;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> comboGender;
    private javax.swing.JComboBox<String> comboexams;
    private javax.swing.JComboBox<String> combosubject;
    private javax.swing.JComboBox<String> combosubject1;
    private javax.swing.JSlider heightindicator;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem mItOfflinehelp;
    private javax.swing.JMenuItem mItScreenshot;
    private javax.swing.JMenuItem mItWebhelp;
    private javax.swing.JMenu menuDate;
    private javax.swing.JMenu menuTIme;
    private javax.swing.JMenuItem mitClose;
    private javax.swing.JPanel panelStudentInfo;
    private javax.swing.JLabel photolabel;
    private javax.swing.JButton showbutton;
    private javax.swing.JTable stShortInfo;
    private javax.swing.JTable stTableInfo;
    private javax.swing.JButton stbarchart;
    private javax.swing.JButton stlinechart;
    private javax.swing.JButton stpiechart;
    private javax.swing.JTextArea tABody;
    private javax.swing.JTextArea tacomment;
    private javax.swing.JTextField txtBlood;
    private javax.swing.JTextField txtDocId;
    private javax.swing.JTextField txtDocattach;
    private javax.swing.JTextField txtDocname;
    private javax.swing.JTextField txtDocsid;
    private javax.swing.JTextField txtEmailfrom;
    private javax.swing.JTextField txtEmailto;
    private javax.swing.JTextField txtGPA;
    private javax.swing.JTextField txtMean;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtStudentFname;
    private javax.swing.JTextField txtStudentId;
    private javax.swing.JTextField txtStudentLname;
    private javax.swing.JTextField txtStudentage;
    private javax.swing.JTextField txtStudentdept;
    private javax.swing.JTextField txtStudentheight;
    private javax.swing.JTextField txtStudentseries;
    private javax.swing.JTextField txtStudentweight;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtattachmentname;
    private javax.swing.JTextField txtavgmarks;
    private javax.swing.JTextField txtemailsbj;
    private javax.swing.JTextField txthighmarks;
    private javax.swing.JTextField txtimagename;
    private javax.swing.JTextField txtlowmarks;
    private javax.swing.JTextField txtsGrade;
    private javax.swing.JTextField txtsTotal;
    private javax.swing.JTextField txtstudents;
    private javax.swing.JSlider weightindicator;
    // End of variables declaration//GEN-END:variables
}
