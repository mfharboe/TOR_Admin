package GUI;

import BE.BEAlarm;
import BE.BEIncident;
import BE.BEIncidentDetails;
import BE.BERoleTime;
import BE.BEUsage;
import BLL.BLLPDF;
import BLL.BLLRead;
import BLL.BLLUpdate;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;

public class GUIAdmin extends javax.swing.JFrame {

    private static GUIAdmin m_instance;
    private DefaultListModel<BEIncident> incidentModel;
    private DefaultListModel<BEUsage> usageModel;
    private DefaultListModel<BERoleTime> roleTimeModel;
    private BEIncidentDetails m_incidentDetails;
    private ArrayList<BERoleTime> m_roleTime;
    private ArrayList<BEUsage> m_usage;
    private boolean isUpdate;

    /**
     * Creates new form GUIAdmin
     */
    private GUIAdmin() {
        this.setTitle(MessageDialog.getInstance().adminTitle());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        initialSettings();
        
    }

    /**
     *
     * @return m_instance of GUIAdmin.
     */
    public static GUIAdmin getInstance() {
        if (m_instance == null) {
            m_instance = new GUIAdmin();
        }
        return m_instance;
    }

    /**
     * Sets the initial settings for this class.
     */
    private void initialSettings() {
        addColors();
        addListeners();
        notEditable();
        fillAlarmCombo();
        incidentModel = new DefaultListModel<>();
        usageModel = new DefaultListModel<>();
        roleTimeModel = new DefaultListModel<>();
        lstIncidents.setModel(incidentModel);
        lstUsage.setModel(usageModel);
        lstRoleTime.setModel(roleTimeModel);
        btnEdit.setEnabled(false);
        enableTextFields(false);
        enableCombobox(false);
        enableBtnDetails(false);
        m_usage = new ArrayList<>();
        m_roleTime = new ArrayList<>();
    }

    /**
     * Adds colors.
     */
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
    }

    /**
     * Adds listeners.
     */
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
        btnMaterial.addActionListener(btn);
        btnUpdate.addActionListener(btn);
        btnPDF.addActionListener(btn);
        btnSave.addActionListener(btn);
        btnEdit.addActionListener(btn);
        btnApprove.addActionListener(btn);
        lstIncidents.addMouseListener(mse);
    }

    /**
     * These lists are never enabled, only shown.
     */
    private void notEditable() {
        lstRoleTime.setEnabled(false);
        lstUsage.setEnabled(false);
    }

    private void fillAlarmCombo() {
        cmbAlarmType.addItem(MessageDialog.getInstance().alarmType());
        for (BEAlarm alarm : BLLRead.getInstance().readAllAlarms()) {
            cmbAlarmType.addItem(alarm);
        }
    }

    /**
     * Enables or disables the textfields.
     *
     * @param enabled
     */
    private void enableTextFields(boolean enabled) {
        txtLeader.setEnabled(enabled);
        txtEvaNumber.setEnabled(enabled);
        txtFireReportNumber.setEnabled(enabled);
        txtInvolvedName.setEnabled(enabled);
        txtInvolvedAddress.setEnabled(enabled);
        txtDetectorNumber.setEnabled(enabled);
        txtGroupNumber.setEnabled(enabled);
        txtRemarks.setEnabled(enabled);
    }

    /**
     * Enables or disables the AlarmType Combobox.
     *
     * @param enabled
     */
    private void enableCombobox(boolean enabled) {
        cmbAlarmType.setEnabled(enabled);
    }

    /**
     * Enables or disables buttons.
     *
     * @param enabled
     */
    private void enableBtnDetails(boolean enabled) {
        btnPDF.setEnabled(enabled);
        btnSave.setEnabled(enabled);
        btnEdit.setEnabled(enabled);
        btnApprove.setEnabled(enabled);
    }

    /**
     * Clears all the textfields etc.
     */
    private void clearDetails() {
        usageModel.clear();
        roleTimeModel.clear();
        txtLeader.setText(MessageDialog.getInstance().emptyString());
        txtEvaNumber.setText(MessageDialog.getInstance().emptyString());
        txtFireReportNumber.setText(MessageDialog.getInstance().emptyString());
        txtInvolvedName.setText(MessageDialog.getInstance().emptyString());
        txtInvolvedAddress.setText(MessageDialog.getInstance().emptyString());
        txtDetectorNumber.setText(MessageDialog.getInstance().emptyString());
        cmbAlarmType.setSelectedIndex(0);
        txtGroupNumber.setText(MessageDialog.getInstance().emptyString());
        txtRemarks.setText(MessageDialog.getInstance().emptyString());
    }

    private void fillDetails(BEIncidentDetails incidentDetails) {
        txtLeader.setText(incidentDetails.getM_incidentLeader());
        txtEvaNumber.setText(incidentDetails.getM_evaNumber());
        txtFireReportNumber.setText(incidentDetails.getM_fireReport());
        txtInvolvedName.setText(incidentDetails.getM_involvedName());
        txtInvolvedAddress.setText(incidentDetails.getM_involvedAddress());
        txtDetectorNumber.setText(incidentDetails.getM_detectorNumber());
        if (incidentDetails.getM_alarm() == null) {
            cmbAlarmType.setSelectedIndex(0);
        } else {
            cmbAlarmType.setSelectedIndex(incidentDetails.getM_alarm().getM_id());
        }
        txtGroupNumber.setText(incidentDetails.getM_groupNumber());
        txtRemarks.setText(incidentDetails.getM_remark());
    }

    private void getDetails() {
        for (BEIncidentDetails incidentDetails : BLLRead.getInstance().readIncidentDetails()) {
            if (((BEIncident) lstIncidents.getSelectedValue()).getM_id() == incidentDetails.getM_incident().getM_id()) {
                m_incidentDetails = incidentDetails;
                fillDetails(m_incidentDetails);
                return;
            }
        }
    }

    private void getUsage() {
        m_usage.clear();
        for (BEUsage incidentUsage : BLLRead.getInstance().readIncidentUsage()) {
            if (((BEIncident) lstIncidents.getSelectedValue()).getM_id() == incidentUsage.getM_incident().getM_id()) {
                usageModel.addElement(incidentUsage);
                m_usage.add(incidentUsage);
            }
        }
    }

    private void getRoleTime() {
        m_roleTime.clear();
        for (BERoleTime incidentRoleTime : BLLRead.getInstance().readIncidentRoleTime()) {
            if (((BEIncident) lstIncidents.getSelectedValue()).getM_id() == incidentRoleTime.getM_incident().getM_id()) {
                roleTimeModel.addElement(incidentRoleTime);
                m_roleTime.add(incidentRoleTime);
            }
        }
    }

    private BEIncidentDetails updatedDetails(boolean isUpdate) {
        m_incidentDetails.setM_incidentLeader(txtLeader.getText());
        m_incidentDetails.setM_evaNumber(txtEvaNumber.getText());
        m_incidentDetails.setM_fireReport(txtFireReportNumber.getText());
        m_incidentDetails.setM_involvedName(txtInvolvedName.getText());
        m_incidentDetails.setM_involvedAddress(txtInvolvedAddress.getText());
        m_incidentDetails.setM_detectorNumber(txtDetectorNumber.getText());
        m_incidentDetails.setM_groupNumber(txtGroupNumber.getText());
        m_incidentDetails.setM_remark(txtRemarks.getText());
        m_incidentDetails.setM_alarm(null);
        if (cmbAlarmType.getSelectedIndex() != 0) {
            m_incidentDetails.setM_alarm((BEAlarm) cmbAlarmType.getSelectedItem());
        }
        m_incidentDetails.getM_incident().getM_id();
        if (isUpdate == true) {
            m_incidentDetails.getM_incident().setM_isDone(false);
        } else {
            m_incidentDetails.getM_incident().setM_isDone(true);
        }
        return m_incidentDetails;
    }

    /**
     * Invoke this method when the Incident-list is clicked.
     */
    private void onListClick() {
        enableTextFields(false);
        enableBtnDetails(false);
        enableCombobox(false);
        usageModel.clear();
        roleTimeModel.clear();
        if (!incidentModel.isEmpty() && lstIncidents.getSelectedIndex() != -1) {
            getDetails();
            btnEdit.setEnabled(true);
            btnApprove.setEnabled(true);
            btnPDF.setEnabled(true);
            getUsage();
            getRoleTime();
        } else {
            clearDetails();
            enableTextFields(false);
            enableCombobox(false);
            enableBtnDetails(false);
        }
    }

    /**
     * Invoke this method when the Update-button is clicked.
     */
    private void onClickUpdate() {
        BLLRead.getInstance().clearDetailsArray();
        incidentModel.clear();
        clearDetails();
        enableTextFields(false);
        enableBtnDetails(false);
        enableCombobox(false);
        for (BEIncident recentIncidents : BLLRead.getInstance().readAllRecentIncidents()) {
            incidentModel.addElement(recentIncidents);
        }
    }

    /**
     * Invoke this method when the Edit-button is clicked.
     */
    private void onClickEdit() {
        enableTextFields(true);
        enableCombobox(true);
        enableBtnDetails(true);
    }

    /**
     * Invoke this method when the Save-button is clicked.
     */
    private void onClickSave() {
        isUpdate = true;
        BLLUpdate.getInstance().updateDetails(updatedDetails(isUpdate));
        onClickUpdate();
    }

    private void onClickApprove() {
        isUpdate = false;
        BLLUpdate.getInstance().updateDetails(updatedDetails(isUpdate));
        MessageDialog.getInstance().incidentApproved();
        onClickUpdate();
    }
    
    private void onClickPDF() {
        BLLPDF.getInstance().printToPDF(m_incidentDetails, m_roleTime, m_usage);
        MessageDialog.getInstance().pdfCreated();
    }

    /**
     * Invoke this method when the Firemen-button is clicked.
     */
    private void onClickFiremen() {
        JFrame firemenAdmin = GUIFiremenAdmin.getInstance();
        firemenAdmin.setVisible(true);
    }

    /**
     * Invoke this method when the Vehicles-button is clicked.
     */
    private void onClickVehicles() {
        JFrame vehicleAdmin = GUIVehicleAdmin.getInstance();
        vehicleAdmin.setVisible(true);
    }

    /**
     * Invoke this method when the Material-button is clicked.
     */
    private void onClickMaterial() {
        JFrame materialAdmin = GUIMaterialAdmin.getInstance();
        materialAdmin.setVisible(true);
    }

    /**
     * Listeners for the buttons.
     */
    private class btnAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(btnUpdate)) {
                onClickUpdate();
            } else if (e.getSource().equals(btnEdit)) {
                onClickEdit();
            } else if (e.getSource().equals(btnSave)) {
                onClickSave();
            } else if (e.getSource().equals(btnApprove)) {
                onClickApprove();
            } else if (e.getSource().equals(btnFiremen)) {
                onClickFiremen();
            } else if (e.getSource().equals(btnVehicles)) {
                onClickVehicles();
            } else if (e.getSource().equals(btnMaterial)) {
                onClickMaterial();
            } else if (e.getSource().equals(btnPDF)){
                onClickPDF();
            }
        }
    }

    /**
     * Listeners for the mouse.
     */
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
        lblInvolved = new javax.swing.JLabel();
        lblInvolvedAddress = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        lstRoleTime = new javax.swing.JList();
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
        txtGroupNumber = new javax.swing.JTextField();
        cmbAlarmType = new javax.swing.JComboBox();
        btnApprove = new javax.swing.JButton();
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
        btnUpdate = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstIncidents = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlAllDetails.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

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
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(pnlDetails2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLeader)
                    .addComponent(txtLeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pnlDetails2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEvaNumber)
                    .addComponent(txtEvaNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pnlDetails2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReportNumber)
                    .addComponent(txtFireReportNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        pnlInvolved.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 18))); // NOI18N

        txtInvolvedName.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtInvolvedName.setPreferredSize(new java.awt.Dimension(250, 38));

        txtInvolvedAddress.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtInvolvedAddress.setPreferredSize(new java.awt.Dimension(250, 38));

        lblInvolved.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblInvolved.setText("Skadeslidte:");

        lblInvolvedAddress.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblInvolvedAddress.setText("Adresse:");

        javax.swing.GroupLayout pnlInvolvedLayout = new javax.swing.GroupLayout(pnlInvolved);
        pnlInvolved.setLayout(pnlInvolvedLayout);
        pnlInvolvedLayout.setHorizontalGroup(
            pnlInvolvedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInvolvedLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInvolvedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInvolvedLayout.createSequentialGroup()
                        .addComponent(lblInvolvedAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtInvolvedAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlInvolvedLayout.createSequentialGroup()
                        .addComponent(lblInvolved, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtInvolvedName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        pnlInvolvedLayout.setVerticalGroup(
            pnlInvolvedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInvolvedLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInvolvedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtInvolvedName, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInvolved))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInvolvedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtInvolvedAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInvolvedAddress))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        lstRoleTime.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fremmødte", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 18))); // NOI18N
        lstRoleTime.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jScrollPane6.setViewportView(lstRoleTime);

        lstUsage.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Forbrug", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 18))); // NOI18N
        lstUsage.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
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
                .addComponent(jScrollPane2)
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

        txtGroupNumber.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtGroupNumber.setPreferredSize(new java.awt.Dimension(250, 38));

        cmbAlarmType.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

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
                    .addComponent(cmbAlarmType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDetail1Layout.setVerticalGroup(
            pnlDetail1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetail1Layout.createSequentialGroup()
                .addGroup(pnlDetail1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDetail1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblAlarmType)
                        .addGap(16, 16, 16))
                    .addGroup(pnlDetail1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cmbAlarmType, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(pnlDetail1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDetectorNumber)
                    .addComponent(txtDetectorNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pnlDetail1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGroupNumber)
                    .addComponent(txtGroupNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        btnApprove.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnApprove.setText("Godkend");

        javax.swing.GroupLayout pnlAllDetailsLayout = new javax.swing.GroupLayout(pnlAllDetails);
        pnlAllDetails.setLayout(pnlAllDetailsLayout);
        pnlAllDetailsLayout.setHorizontalGroup(
            pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAllDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlAllDetailsLayout.createSequentialGroup()
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlDetail1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDetails2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlInvolved, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlAllDetailsLayout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlRemarks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAllDetailsLayout.createSequentialGroup()
                        .addComponent(btnPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnApprove, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(jScrollPane6)
                    .addGroup(pnlAllDetailsLayout.createSequentialGroup()
                        .addComponent(pnlDetails2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlInvolved, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane7))
                .addGap(18, 18, 18)
                .addGroup(pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnApprove, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pnlAdministrate.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Administrer", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 18))); // NOI18N

        btnVehicles.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnVehicles.setText("Køretøjer");
        btnVehicles.setPreferredSize(new java.awt.Dimension(38, 250));

        btnFiremen.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnFiremen.setText("Mandskab");
        btnFiremen.setPreferredSize(new java.awt.Dimension(38, 250));

        btnMaterial.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnMaterial.setText("Materiel");
        btnMaterial.setPreferredSize(new java.awt.Dimension(38, 250));

        javax.swing.GroupLayout pnlAdministrateLayout = new javax.swing.GroupLayout(pnlAdministrate);
        pnlAdministrate.setLayout(pnlAdministrateLayout);
        pnlAdministrateLayout.setHorizontalGroup(
            pnlAdministrateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAdministrateLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAdministrateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnVehicles, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(btnFiremen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMaterial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        btnUpdate.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnUpdate.setText("Nyeste meldinger");
        btnUpdate.setPreferredSize(new java.awt.Dimension(38, 250));

        javax.swing.GroupLayout pnlSearchLayout = new javax.swing.GroupLayout(pnlSearch);
        pnlSearch.setLayout(pnlSearchLayout);
        pnlSearchLayout.setHorizontalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(pnlSearchFor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlSearchLayout.setVerticalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlSearchFor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlSearchLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9))
        );

        lstIncidents.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Meldinger", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 18))); // NOI18N
        lstIncidents.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jScrollPane1.setViewportView(lstIncidents);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlAdministrate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(pnlAllDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
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
    private javax.swing.JButton btnApprove;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnFiremen;
    private javax.swing.JButton btnMaterial;
    private javax.swing.JButton btnPDF;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnVehicles;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cmbAlarmType;
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
    private javax.swing.JLabel lblInvolved;
    private javax.swing.JLabel lblInvolvedAddress;
    private javax.swing.JLabel lblLeader;
    private javax.swing.JLabel lblReportNumber;
    private javax.swing.JLabel lblTo;
    private javax.swing.JList lstIncidents;
    private javax.swing.JList lstRoleTime;
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
