package GUI;

import BE.BEIncident;
import BE.BEIncidentDetails;
import BE.BEUsage;
import BLL.BLLRead;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import javax.swing.DefaultListModel;

public class GUIAdmin extends javax.swing.JFrame {

    private static GUIAdmin m_instance;
    private DefaultListModel<BEIncident> incidentModel;
    private DefaultListModel<BEUsage> usageModel;

    /**
     * Creates new form GUIAdmin
     */
    private GUIAdmin() {
        this.setTitle(MessageDialog.getInstance().adminTitle());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        initialSettings();

    }

    public static GUIAdmin getInstance() {
        if (m_instance == null) {
            m_instance = new GUIAdmin();
        }
        return m_instance;
    }

    private void initialSettings() {
        addColors();
        addListeners();
        notEditable();
        incidentModel = new DefaultListModel<>();
        usageModel = new DefaultListModel<>();
        lstIncidents.setModel(incidentModel);
        lstUsage.setModel(usageModel);
        btnEdit.setEnabled(false);
        enableTxtDetails(false);
        enableLblDetails(false);
        enableBtnDetails(false);
    }

    private void notEditable() {
        txtAlarmType.setEnabled(false);
        lstForces.setEnabled(false);
        lstUsage.setEnabled(false);
        lblAlarmType.setEnabled(false);
    }

    private void addColors() {
        this.getContentPane().setBackground(Color.WHITE);
        pnlAdministrate.setBackground(Color.WHITE);
        pnlAllDetails.setBackground(Color.WHITE);
        pnlSearch.setBackground(Color.WHITE);
        pnlSearchFor.setBackground(Color.WHITE);
        pnlInvolved.setBackground(Color.WHITE);
        pnlRemarks.setBackground(Color.WHITE);
        pnlDetail1.setBackground(Color.WHITE);
        pnlDetails2.setBackground(Color.WHITE);
        rdoBlind.setBackground(Color.WHITE);
        rdoExercise.setBackground(Color.WHITE);
        rdoFalse.setBackground(Color.WHITE);
        rdoIncident.setBackground(Color.WHITE);
        rdoAll.setBackground(Color.WHITE);
        cbxApproved.setBackground(Color.WHITE);

    }

    private void addListeners() {
        btnAction btn = new btnAction();
        mouseAction mse = new mouseAction();
        buttonGroup1.add(rdoBlind);
        buttonGroup1.add(rdoExercise);
        buttonGroup1.add(rdoFalse);
        buttonGroup1.add(rdoIncident);
        btnSearch.addActionListener(btn);
        btnFiremen.addActionListener(btn);
        btnVehicles.addActionListener(btn);
        btnShow.addActionListener(btn);
        btnPDF.addActionListener(btn);
        btnSave.addActionListener(btn);
        btnEdit.addActionListener(btn);
        lstIncidents.addMouseListener(mse);
    }

    private void enableTxtDetails(boolean enabled) {
        txtLeader.setEnabled(enabled);
        txtEvaNumber.setEnabled(enabled);
        txtFireReportNumber.setEnabled(enabled);
        txtInvolvedName.setEnabled(enabled);
        txtInvolvedAddress.setEnabled(enabled);
        txtDetectorNumber.setEnabled(enabled);
        txtGroupNumber.setEnabled(enabled);
        txtRemarks.setEnabled(enabled);
    }

    private void enableLblDetails(boolean enabled) {
        lblAlarmType.setEnabled(enabled);
        lblDetectorNumber.setEnabled(enabled);
        lblEvaNumber.setEnabled(enabled);
        lblReportNumber.setEnabled(enabled);
        lblGroupNumber.setEnabled(enabled);
        lblLeader.setEnabled(enabled);
        pnlInvolved.setEnabled(enabled);
        pnlRemarks.setEnabled(enabled);
    }

    private void enableBtnDetails(boolean enabled) {
        btnPDF.setEnabled(enabled);
        btnSave.setEnabled(enabled);
        btnEdit.setEnabled(enabled);
        cbxApproved.setEnabled(enabled);
    }

    private void clearDetails() {
        usageModel.clear();
        txtLeader.setText(MessageDialog.getInstance().emptyString());
        txtEvaNumber.setText(MessageDialog.getInstance().emptyString());
        txtFireReportNumber.setText(MessageDialog.getInstance().emptyString());
        txtInvolvedName.setText(MessageDialog.getInstance().emptyString());
        txtInvolvedAddress.setText(MessageDialog.getInstance().emptyString());
        txtDetectorNumber.setText(MessageDialog.getInstance().emptyString());
        txtAlarmType.setText(MessageDialog.getInstance().emptyString());
        txtGroupNumber.setText(MessageDialog.getInstance().emptyString());
        txtRemarks.setText(MessageDialog.getInstance().emptyString());
    }

    private void onClickUpdate() {
        BLLRead.getInstance().clearDetailsArray();
        incidentModel.clear();
        clearDetails();
        enableLblDetails(false);
        enableTxtDetails(false);
        enableBtnDetails(false);
        for (BEIncident recentIncidents : BLLRead.getInstance().readAllRecentIncidents()) {
            incidentModel.addElement(recentIncidents);
        }
    }

    private void onClickSearchDate() {

    }

    private void onListClick() {
        enableLblDetails(false);
        enableTxtDetails(false);
        enableBtnDetails(false);
        usageModel.clear();
        if (!incidentModel.isEmpty() && lstIncidents.getSelectedIndex() != -1) {
            getDetails();
            enableLblDetails(true);
            btnEdit.setEnabled(true);
        } else {
            clearDetails();
            btnEdit.setEnabled(false);
            enableLblDetails(false);
            enableTxtDetails(false);
            enableBtnDetails(false);
            return;
        }
        getUsage();
    }

    private void onClickEdit() {
        enableTxtDetails(true);
        enableLblDetails(true);
        enableBtnDetails(true);
    }

    private void getDetails() {
        for (BEIncidentDetails incidentDetails : BLLRead.getInstance().readIncidentDetails()) {
            if (((BEIncident) lstIncidents.getSelectedValue()).getM_id() == incidentDetails.getM_incident().getM_id()) {
                fillDetails(incidentDetails);
                return;
            }
        }
    }

    private void getUsage() {
        for (BEUsage incidentUsage : BLLRead.getInstance().readIncidentUsage()) {
            if (((BEIncident) lstIncidents.getSelectedValue()).getM_id() == incidentUsage.getM_incident().getM_id()) {
                usageModel.addElement(incidentUsage);
            }
        }
    }
    
    private void getForces(){

    }

    private void fillDetails(BEIncidentDetails incidentDetails) {
        txtLeader.setText(incidentDetails.getM_incidentLeader());
        txtEvaNumber.setText(incidentDetails.getM_evaNumber());
        txtFireReportNumber.setText(incidentDetails.getM_fireReport());
        txtInvolvedName.setText(incidentDetails.getM_involvedName());
        txtInvolvedAddress.setText(incidentDetails.getM_involvedAddress());
        txtDetectorNumber.setText(incidentDetails.getM_detectorNumber());
        txtAlarmType.setText(incidentDetails.getM_alarm().getM_description());
        txtGroupNumber.setText(incidentDetails.getM_groupNumber());
        txtRemarks.setText(incidentDetails.getM_remark());
    }



    private Date getDateFrom() {
        java.util.Date utilDate = dateChooserFrom.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        return sqlDate;
    }

    private Date getDateTo() {
        java.util.Date utilDate = dateChooserTo.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        return sqlDate;

    }

    private class btnAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(btnShow)) {
                onClickUpdate();
            } else if (e.getSource().equals(btnSearch)) {
                onClickSearchDate();
            } else if (e.getSource().equals(btnEdit)) {
                onClickEdit();
            }
        }
    }

    private class mouseAction extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 1) {
                onListClick();
            }
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
        pnlAllDetails = new javax.swing.JPanel();
        cbxApproved = new javax.swing.JCheckBox();
        btnSave = new javax.swing.JButton();
        btnPDF = new javax.swing.JButton();
        pnlDetails2 = new javax.swing.JPanel();
        txtFireReportNumber = new javax.swing.JTextField();
        txtEvaNumber = new javax.swing.JTextField();
        lblEvaNumber = new javax.swing.JLabel();
        txtLeader = new javax.swing.JTextField();
        lblLeader = new javax.swing.JLabel();
        lblReportNumber = new javax.swing.JLabel();
        pnlInvolved = new javax.swing.JPanel();
        txtInvolvedName = new javax.swing.JTextField();
        txtInvolvedAddress = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        lstForces = new javax.swing.JList();
        jScrollPane7 = new javax.swing.JScrollPane();
        lstUsage = new javax.swing.JList();
        pnlRemarks = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtRemarks = new javax.swing.JTextArea();
        btnEdit = new javax.swing.JButton();
        pnlDetail1 = new javax.swing.JPanel();
        lblAlarmType = new javax.swing.JLabel();
        lblDetectorNumber = new javax.swing.JLabel();
        lblGroupNumber = new javax.swing.JLabel();
        txtDetectorNumber = new javax.swing.JTextField();
        txtAlarmType = new javax.swing.JTextField();
        txtGroupNumber = new javax.swing.JTextField();
        pnlAdministrate = new javax.swing.JPanel();
        btnVehicles = new javax.swing.JButton();
        btnFiremen = new javax.swing.JButton();
        btnMaterial = new javax.swing.JButton();
        pnlSearch = new javax.swing.JPanel();
        pnlSearchFor = new javax.swing.JPanel();
        rdoFalse = new javax.swing.JRadioButton();
        rdoBlind = new javax.swing.JRadioButton();
        rdoExercise = new javax.swing.JRadioButton();
        rdoIncident = new javax.swing.JRadioButton();
        rdoAll = new javax.swing.JRadioButton();
        lblFrom = new javax.swing.JLabel();
        dateChooserFrom = new com.toedter.calendar.JDateChooser();
        lblTo = new javax.swing.JLabel();
        dateChooserTo = new com.toedter.calendar.JDateChooser();
        btnSearch = new javax.swing.JButton();
        btnShow = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstIncidents = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlAllDetails.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        cbxApproved.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cbxApproved.setText("Godkendt");

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnSave.setText("Gem");
        btnSave.setPreferredSize(new java.awt.Dimension(105, 38));

        btnPDF.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnPDF.setText("Print til PDF");
        btnPDF.setPreferredSize(new java.awt.Dimension(105, 38));

        pnlDetails2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtFireReportNumber.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtFireReportNumber.setPreferredSize(new java.awt.Dimension(250, 38));

        txtEvaNumber.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtEvaNumber.setPreferredSize(new java.awt.Dimension(250, 38));

        lblEvaNumber.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblEvaNumber.setText("EVA nr:");

        txtLeader.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtLeader.setPreferredSize(new java.awt.Dimension(250, 38));

        lblLeader.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblLeader.setText("Indsatsleder:");

        lblReportNumber.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblReportNumber.setText("Rapport nr:");

        javax.swing.GroupLayout pnlDetails2Layout = new javax.swing.GroupLayout(pnlDetails2);
        pnlDetails2.setLayout(pnlDetails2Layout);
        pnlDetails2Layout.setHorizontalGroup(
            pnlDetails2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetails2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDetails2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDetails2Layout.createSequentialGroup()
                        .addComponent(lblLeader, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtLeader, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDetails2Layout.createSequentialGroup()
                        .addComponent(lblReportNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFireReportNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDetails2Layout.createSequentialGroup()
                        .addComponent(lblEvaNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEvaNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        pnlDetails2Layout.setVerticalGroup(
            pnlDetails2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetails2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(pnlDetails2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLeader)
                    .addComponent(txtLeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDetails2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEvaNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEvaNumber))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDetails2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFireReportNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblReportNumber))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlInvolved.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Skadeslidte", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 18))); // NOI18N

        txtInvolvedName.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtInvolvedName.setPreferredSize(new java.awt.Dimension(250, 38));

        txtInvolvedAddress.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtInvolvedAddress.setPreferredSize(new java.awt.Dimension(250, 38));

        javax.swing.GroupLayout pnlInvolvedLayout = new javax.swing.GroupLayout(pnlInvolved);
        pnlInvolved.setLayout(pnlInvolvedLayout);
        pnlInvolvedLayout.setHorizontalGroup(
            pnlInvolvedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInvolvedLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlInvolvedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtInvolvedName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtInvolvedAddress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnlInvolvedLayout.setVerticalGroup(
            pnlInvolvedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInvolvedLayout.createSequentialGroup()
                .addComponent(txtInvolvedName, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtInvolvedAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        lstForces.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fremmødte", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 18))); // NOI18N
        jScrollPane6.setViewportView(lstForces);

        lstUsage.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Forbrug", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 18))); // NOI18N
        jScrollPane7.setViewportView(lstUsage);

        pnlRemarks.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bemærkninger", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 18))); // NOI18N

        txtRemarks.setColumns(20);
        txtRemarks.setRows(5);
        jScrollPane2.setViewportView(txtRemarks);

        javax.swing.GroupLayout pnlRemarksLayout = new javax.swing.GroupLayout(pnlRemarks);
        pnlRemarks.setLayout(pnlRemarksLayout);
        pnlRemarksLayout.setHorizontalGroup(
            pnlRemarksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRemarksLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlRemarksLayout.setVerticalGroup(
            pnlRemarksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRemarksLayout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        btnEdit.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnEdit.setText("Rediger");
        btnEdit.setPreferredSize(new java.awt.Dimension(105, 38));

        pnlDetail1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lblAlarmType.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblAlarmType.setText("Beretning:");

        lblDetectorNumber.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblDetectorNumber.setText("Detektor nr:");

        lblGroupNumber.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblGroupNumber.setText("Gruppe nr:");

        txtDetectorNumber.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtDetectorNumber.setPreferredSize(new java.awt.Dimension(250, 38));

        txtAlarmType.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtAlarmType.setPreferredSize(new java.awt.Dimension(250, 38));

        txtGroupNumber.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtGroupNumber.setPreferredSize(new java.awt.Dimension(250, 38));

        javax.swing.GroupLayout pnlDetail1Layout = new javax.swing.GroupLayout(pnlDetail1);
        pnlDetail1.setLayout(pnlDetail1Layout);
        pnlDetail1Layout.setHorizontalGroup(
            pnlDetail1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetail1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDetail1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblAlarmType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblGroupNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDetectorNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDetail1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtGroupNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDetectorNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtAlarmType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDetail1Layout.setVerticalGroup(
            pnlDetail1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetail1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDetail1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAlarmType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAlarmType))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDetail1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDetectorNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDetectorNumber))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDetail1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGroupNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGroupNumber))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlAllDetailsLayout = new javax.swing.GroupLayout(pnlAllDetails);
        pnlAllDetails.setLayout(pnlAllDetailsLayout);
        pnlAllDetailsLayout.setHorizontalGroup(
            pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAllDetailsLayout.createSequentialGroup()
                .addGroup(pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAllDetailsLayout.createSequentialGroup()
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(pnlDetail1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlDetails2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlInvolved, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30))
                    .addGroup(pnlAllDetailsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlRemarks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlAllDetailsLayout.createSequentialGroup()
                        .addGroup(pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxApproved))
                        .addGap(18, 18, 18)
                        .addGroup(pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlAllDetailsLayout.createSequentialGroup()
                                .addComponent(btnPDF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlAllDetailsLayout.setVerticalGroup(
            pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAllDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlDetail1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlRemarks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAllDetailsLayout.createSequentialGroup()
                        .addComponent(pnlDetails2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlInvolved, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane6)
                    .addComponent(jScrollPane7))
                .addGap(18, 18, 18)
                .addGroup(pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxApproved)
                        .addComponent(btnPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pnlAdministrate.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Administrer", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 18))); // NOI18N

        btnVehicles.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnVehicles.setText("Køretøjer");

        btnFiremen.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnFiremen.setText("Frivillige Brandmænd");

        btnMaterial.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnMaterial.setText("Materiel");

        javax.swing.GroupLayout pnlAdministrateLayout = new javax.swing.GroupLayout(pnlAdministrate);
        pnlAdministrate.setLayout(pnlAdministrateLayout);
        pnlAdministrateLayout.setHorizontalGroup(
            pnlAdministrateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAdministrateLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAdministrateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFiremen, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVehicles, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlAdministrateLayout.setVerticalGroup(
            pnlAdministrateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAdministrateLayout.createSequentialGroup()
                .addComponent(btnFiremen, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnVehicles, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlSearch.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Find", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 24))); // NOI18N

        pnlSearchFor.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        rdoFalse.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        rdoFalse.setText("Falske alarmer");

        rdoBlind.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        rdoBlind.setText("Blinde alarmer");

        rdoExercise.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        rdoExercise.setText("Øvelser");

        rdoIncident.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        rdoIncident.setText("Indsater");

        rdoAll.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        rdoAll.setText("Alle");

        lblFrom.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblFrom.setText("Fra");

        lblTo.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblTo.setText("Til");

        btnSearch.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnSearch.setText("Find");
        btnSearch.setPreferredSize(new java.awt.Dimension(105, 38));

        javax.swing.GroupLayout pnlSearchForLayout = new javax.swing.GroupLayout(pnlSearchFor);
        pnlSearchFor.setLayout(pnlSearchForLayout);
        pnlSearchForLayout.setHorizontalGroup(
            pnlSearchForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchForLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSearchForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdoIncident)
                    .addComponent(rdoExercise))
                .addGap(18, 18, 18)
                .addGroup(pnlSearchForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdoBlind)
                    .addGroup(pnlSearchForLayout.createSequentialGroup()
                        .addComponent(rdoFalse)
                        .addGap(18, 18, 18)
                        .addComponent(rdoAll)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(lblFrom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dateChooserFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(lblTo)
                .addGap(12, 12, 12)
                .addComponent(dateChooserTo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        pnlSearchForLayout.setVerticalGroup(
            pnlSearchForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchForLayout.createSequentialGroup()
                .addGroup(pnlSearchForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoIncident)
                    .addComponent(rdoFalse)
                    .addComponent(rdoAll))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(pnlSearchForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoExercise)
                    .addComponent(rdoBlind)))
            .addGroup(pnlSearchForLayout.createSequentialGroup()
                .addGroup(pnlSearchForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSearchForLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlSearchForLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(dateChooserTo, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                            .addComponent(dateChooserFrom, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                            .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnlSearchForLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(lblFrom)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSearchForLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblTo)
                .addGap(21, 21, 21))
        );

        btnShow.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnShow.setText("Nyeste meldinger");

        javax.swing.GroupLayout pnlSearchLayout = new javax.swing.GroupLayout(pnlSearch);
        pnlSearch.setLayout(pnlSearchLayout);
        pnlSearchLayout.setHorizontalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnShow, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(pnlSearchFor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlSearchLayout.setVerticalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlSearchFor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlSearchLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnShow, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9))
        );

        lstIncidents.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Resultat", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 18))); // NOI18N
        lstIncidents.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jScrollPane1.setViewportView(lstIncidents);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlAdministrate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addGap(18, 18, 18)
                        .addComponent(pnlAllDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(18, 18, 18)
                        .addComponent(pnlAdministrate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlAllDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnFiremen;
    private javax.swing.JButton btnMaterial;
    private javax.swing.JButton btnPDF;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnShow;
    private javax.swing.JButton btnVehicles;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox cbxApproved;
    private com.toedter.calendar.JDateChooser dateChooserFrom;
    private com.toedter.calendar.JDateChooser dateChooserTo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel lblAlarmType;
    private javax.swing.JLabel lblDetectorNumber;
    private javax.swing.JLabel lblEvaNumber;
    private javax.swing.JLabel lblFrom;
    private javax.swing.JLabel lblGroupNumber;
    private javax.swing.JLabel lblLeader;
    private javax.swing.JLabel lblReportNumber;
    private javax.swing.JLabel lblTo;
    private javax.swing.JList lstForces;
    private javax.swing.JList lstIncidents;
    private javax.swing.JList lstUsage;
    private javax.swing.JPanel pnlAdministrate;
    private javax.swing.JPanel pnlAllDetails;
    private javax.swing.JPanel pnlDetail1;
    private javax.swing.JPanel pnlDetails2;
    private javax.swing.JPanel pnlInvolved;
    private javax.swing.JPanel pnlRemarks;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlSearchFor;
    private javax.swing.JRadioButton rdoAll;
    private javax.swing.JRadioButton rdoBlind;
    private javax.swing.JRadioButton rdoExercise;
    private javax.swing.JRadioButton rdoFalse;
    private javax.swing.JRadioButton rdoIncident;
    private javax.swing.JTextField txtAlarmType;
    private javax.swing.JTextField txtDetectorNumber;
    private javax.swing.JTextField txtEvaNumber;
    private javax.swing.JTextField txtFireReportNumber;
    private javax.swing.JTextField txtGroupNumber;
    private javax.swing.JTextField txtInvolvedAddress;
    private javax.swing.JTextField txtInvolvedName;
    private javax.swing.JTextField txtLeader;
    private javax.swing.JTextArea txtRemarks;
    // End of variables declaration//GEN-END:variables
}
