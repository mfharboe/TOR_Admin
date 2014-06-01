package GUI;

import BE.BEAlarm;
import BE.BEIncident;
import BE.BEIncidentDetails;
import BE.BERoleTime;
import BE.BEUsage;
import BLL.BLLCreate;
import BLL.BLLError;
import BLL.BLLPDF;
import BLL.BLLRead;
import BLL.BLLUpdate;
import DAL.DALCreate;
import DAL.DALRead;
import DAL.DALUpdate;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GUIAdmin extends javax.swing.JFrame {

    private static GUIAdmin m_instance;
    private DefaultListModel<BEIncident> incidentModel;
    private DefaultListModel<BEUsage> usageModel;
    private DefaultListModel<BERoleTime> roleTimeModel;
    private BEIncidentDetails m_incidentDetails;
    private ArrayList<BERoleTime> m_roleTime;
    private ArrayList<BEUsage> m_usage;
    private boolean isDone;

    ImageIcon image;
    ImageIcon imageLogo;

    /**
     * Creates new form GUIAdmin
     */
    private GUIAdmin() {
        setUpBLL();
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
     * Startup the BLL classes with a given DAL class.
     */
    private void setUpBLL() {
        BLLError.getInstance().register(MessageDialog.getInstance());
        BLLRead.getInstance().setDAL(DALRead.getInstance());
        BLLCreate.getInstance().setDAL(DALCreate.getInstance());
        BLLUpdate.getInstance().setDAL(DALUpdate.getInstance());
    }

    /**
     * Sets the initial settings for this class.
     */
    private void initialSettings() {
        addColors();
        addListeners();
        fillAlarmCombo();
        incidentModel = new DefaultListModel<>();
        usageModel = new DefaultListModel<>();
        roleTimeModel = new DefaultListModel<>();
        lstIncidents.setModel(incidentModel);
        lstUsage.setModel(usageModel);
        lstRoleTime.setModel(roleTimeModel);
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
        pnlInvolved.setBackground(Color.WHITE);
        pnlDetail1.setBackground(Color.WHITE);
        pnlDetails2.setBackground(Color.WHITE);
        pnlPeople.setBackground(Color.WHITE);
        pnlUsage.setBackground(Color.WHITE);
        imageLogo = new ImageIcon("ebr.jpg");
        lblLogo.setIcon(imageLogo);
    }

    /**
     * Adds listeners.
     */
    private void addListeners() {
        btnAction btn = new btnAction();
        mouseAction mse = new mouseAction();
        txtAction txt = new txtAction();
        btnFiremen.addActionListener(btn);
        btnVehicles.addActionListener(btn);
        btnMaterial.addActionListener(btn);
        btnUpdate.addActionListener(btn);
        btnSave.addActionListener(btn);
        btnEdit.addActionListener(btn);
        btnApprove.addActionListener(btn);
        lstIncidents.addMouseListener(mse);
        lstIncidents.addKeyListener(txt);
    }

    /**
     * Fills the AlarmType Combobox.
     */
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
        txtRemark.setEnabled(enabled);
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
        btnSave.setEnabled(enabled);
        btnEdit.setEnabled(enabled);
        btnApprove.setEnabled(enabled);
    }

    /**
     * Clears the textfields etc.
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
        txtRemark.setText(MessageDialog.getInstance().emptyString());
    }

    /**
     * Fills the textfields etc with the given IncidentDetails.
     * @param incidentDetails 
     */
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
        txtRemark.setText(incidentDetails.getM_remark());
    }

    /**
     * Finds and sets the IncidentDetails of an Incident marked in the list.
     */
    private void getDetails() {
        for (BEIncidentDetails incidentDetails : BLLRead.getInstance().returnIncidentDetails()) {
            if (((BEIncident) lstIncidents.getSelectedValue()).getM_id() == incidentDetails.getM_incident().getM_id()) {
                m_incidentDetails = incidentDetails;
                fillDetails(m_incidentDetails);
                return;
            }
        }
    }

    /**
     * Finds and sets the Usage of an Incident marked in the list.
     */
    private void getUsage() {
        m_usage.clear();
        for (BEUsage incidentUsage : BLLRead.getInstance().returnIncidentUsage()) {
            if (((BEIncident) lstIncidents.getSelectedValue()).getM_id() == incidentUsage.getM_incident().getM_id()) {
                usageModel.addElement(incidentUsage);
                m_usage.add(incidentUsage);
            }
        }
    }

    /**
     * Finds and sets the RoleTime of an Incident marked in the list.
     */
    private void getRoleTime() {
        m_roleTime.clear();
        for (BERoleTime incidentRoleTime : BLLRead.getInstance().returnIncidentRoleTime()) {
            if (((BEIncident) lstIncidents.getSelectedValue()).getM_id() == incidentRoleTime.getM_incident().getM_id()) {
                roleTimeModel.addElement(incidentRoleTime);
                m_roleTime.add(incidentRoleTime);
            }
        }
    }

    /**
     * Updates the IncidentDetails of the Incident marked in the list. 
     * @param isDone if the Incident is to be finished or not
     * @return m_incidentDetails
     */
    private BEIncidentDetails updatedDetails(boolean isDone) {
        m_incidentDetails.setM_incidentLeader(txtLeader.getText());
        m_incidentDetails.setM_evaNumber(txtEvaNumber.getText());
        m_incidentDetails.setM_fireReport(txtFireReportNumber.getText());
        m_incidentDetails.setM_involvedName(txtInvolvedName.getText());
        m_incidentDetails.setM_involvedAddress(txtInvolvedAddress.getText());
        m_incidentDetails.setM_detectorNumber(txtDetectorNumber.getText());
        m_incidentDetails.setM_groupNumber(txtGroupNumber.getText());
        m_incidentDetails.setM_remark(txtRemark.getText());
        m_incidentDetails.setM_alarm(null);
        if (cmbAlarmType.getSelectedIndex() != 0) {
            m_incidentDetails.setM_alarm((BEAlarm) cmbAlarmType.getSelectedItem());
        }
        m_incidentDetails.getM_incident().getM_id();
        m_incidentDetails.getM_incident().setM_isDone(isDone);
        return m_incidentDetails;
    }

    /**
     * Prints a pdf with the details of the incident.
     */
    private boolean printReport() {
        return BLLPDF.getInstance().printToPDF(m_incidentDetails, m_roleTime, m_usage);
        
    }

    /**
     * Invoke this method when the Incident-list is clicked.
     */
    private void onListClick() {
        enableTextFields(false);
        enableBtnDetails(false);
        enableCombobox(false);
        clearDetails();
        if (!incidentModel.isEmpty() && lstIncidents.getSelectedIndex() != -1) {
            getDetails();
            btnEdit.setEnabled(true);
            btnApprove.setEnabled(true);
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
        btnApprove.setEnabled(false);
        btnSave.setEnabled(true);
        btnEdit.setEnabled(false);
    }

    /**
     * Invoke this method when the Save-button is clicked.
     */
    private void onClickSave() {
        isDone = false;
        BLLUpdate.getInstance().updateDetails(updatedDetails(isDone));
        onClickUpdate();
    }

    /**
     * Invoke this method when the Approve-button is clicked.
     */
    private void onClickApprove() {
        isDone = printReport();
        BLLUpdate.getInstance().updateDetails(updatedDetails(isDone));
        onClickUpdate();
    }

    /**
     * Invoke this method when the Firemen-button is clicked.
     */
    private void onClickFiremen() {
        JFrame firemenAdmin = new GUIFiremenAdmin();
        firemenAdmin.setVisible(true);
    }

    /**
     * Invoke this method when the Vehicles-button is clicked.
     */
    private void onClickVehicles() {
        JFrame vehicleAdmin = new GUIVehicleAdmin();
        vehicleAdmin.setVisible(true);
    }

    /**
     * Invoke this method when the Material-button is clicked.
     */
    private void onClickMaterial() {
        JFrame materialAdmin = new GUIMaterialAdmin();
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
     * Listener for the keyboard.
     */
    private class txtAction extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getSource().equals(lstIncidents)) {
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

        pnlAllDetails = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
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
        txtRemark = new javax.swing.JTextField();
        lblRemark = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();
        pnlDetail1 = new javax.swing.JPanel();
        lblAlarmType = new javax.swing.JLabel();
        lblDetectorNumber = new javax.swing.JLabel();
        lblGroupNumber = new javax.swing.JLabel();
        txtDetectorNumber = new javax.swing.JTextField();
        txtGroupNumber = new javax.swing.JTextField();
        cmbAlarmType = new javax.swing.JComboBox();
        btnApprove = new javax.swing.JButton();
        pnlPeople = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        lstRoleTime = new javax.swing.JList();
        pnlUsage = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        lstUsage = new javax.swing.JList();
        pnlAdministrate = new javax.swing.JPanel();
        btnVehicles = new javax.swing.JButton();
        btnFiremen = new javax.swing.JButton();
        btnMaterial = new javax.swing.JButton();
        pnlSearch = new javax.swing.JPanel();
        btnUpdate = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstIncidents = new javax.swing.JList();
        lblLogo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlAllDetails.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnSave.setText("Gem");
        btnSave.setPreferredSize(new java.awt.Dimension(105, 38));

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
                .addGroup(pnlDetails2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblEvaNumber, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblReportNumber, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblLeader, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlDetails2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFireReportNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLeader, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEvaNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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

        txtRemark.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        lblRemark.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblRemark.setText("Bemærkning:");

        javax.swing.GroupLayout pnlInvolvedLayout = new javax.swing.GroupLayout(pnlInvolved);
        pnlInvolved.setLayout(pnlInvolvedLayout);
        pnlInvolvedLayout.setHorizontalGroup(
            pnlInvolvedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInvolvedLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInvolvedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblInvolvedAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblInvolved, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                    .addComponent(lblRemark, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlInvolvedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtInvolvedAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtInvolvedName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtRemark))
                .addContainerGap())
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInvolvedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRemark, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRemark))
                .addContainerGap(22, Short.MAX_VALUE))
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
                    .addComponent(lblGroupNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDetectorNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                    .addComponent(lblAlarmType, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(pnlDetail1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtGroupNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDetectorNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbAlarmType, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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
        btnApprove.setText("Godkend & Opret Rapport");

        pnlPeople.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fremmødte", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 18))); // NOI18N

        lstRoleTime.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 18))); // NOI18N
        lstRoleTime.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jScrollPane6.setViewportView(lstRoleTime);

        javax.swing.GroupLayout pnlPeopleLayout = new javax.swing.GroupLayout(pnlPeople);
        pnlPeople.setLayout(pnlPeopleLayout);
        pnlPeopleLayout.setHorizontalGroup(
            pnlPeopleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPeopleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlPeopleLayout.setVerticalGroup(
            pnlPeopleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPeopleLayout.createSequentialGroup()
                .addComponent(jScrollPane6)
                .addContainerGap())
        );

        pnlUsage.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Forbrug", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 18))); // NOI18N

        lstUsage.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jScrollPane7.setViewportView(lstUsage);

        javax.swing.GroupLayout pnlUsageLayout = new javax.swing.GroupLayout(pnlUsage);
        pnlUsage.setLayout(pnlUsageLayout);
        pnlUsageLayout.setHorizontalGroup(
            pnlUsageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUsageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlUsageLayout.setVerticalGroup(
            pnlUsageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUsageLayout.createSequentialGroup()
                .addComponent(jScrollPane7)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlAllDetailsLayout = new javax.swing.GroupLayout(pnlAllDetails);
        pnlAllDetails.setLayout(pnlAllDetailsLayout);
        pnlAllDetailsLayout.setHorizontalGroup(
            pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAllDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAllDetailsLayout.createSequentialGroup()
                        .addGroup(pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlDetail1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlDetails2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlInvolved, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(pnlPeople, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAllDetailsLayout.createSequentialGroup()
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnApprove, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlUsage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlAllDetailsLayout.setVerticalGroup(
            pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAllDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnlAllDetailsLayout.createSequentialGroup()
                        .addComponent(pnlDetail1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlDetails2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlInvolved, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlPeople, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlUsage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlAllDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnApprove, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGroup(pnlAdministrateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFiremen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVehicles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMaterial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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

        pnlSearch.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Indsatser", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 24))); // NOI18N

        btnUpdate.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnUpdate.setText("Opdater");
        btnUpdate.setPreferredSize(new java.awt.Dimension(38, 250));

        lstIncidents.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jScrollPane1.setViewportView(lstIncidents);

        javax.swing.GroupLayout pnlSearchLayout = new javax.swing.GroupLayout(pnlSearch);
        pnlSearch.setLayout(pnlSearchLayout);
        pnlSearchLayout.setHorizontalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlSearchLayout.setVerticalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlAdministrate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlAllDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlAllDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(pnlSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlAdministrate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApprove;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnFiremen;
    private javax.swing.JButton btnMaterial;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnVehicles;
    private javax.swing.JComboBox cmbAlarmType;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel lblAlarmType;
    private javax.swing.JLabel lblDetectorNumber;
    private javax.swing.JLabel lblEvaNumber;
    private javax.swing.JLabel lblGroupNumber;
    private javax.swing.JLabel lblInvolved;
    private javax.swing.JLabel lblInvolvedAddress;
    private javax.swing.JLabel lblLeader;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblRemark;
    private javax.swing.JLabel lblReportNumber;
    private javax.swing.JList lstIncidents;
    private javax.swing.JList lstRoleTime;
    private javax.swing.JList lstUsage;
    private javax.swing.JPanel pnlAdministrate;
    private javax.swing.JPanel pnlAllDetails;
    private javax.swing.JPanel pnlDetail1;
    private javax.swing.JPanel pnlDetails2;
    private javax.swing.JPanel pnlInvolved;
    private javax.swing.JPanel pnlPeople;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlUsage;
    private javax.swing.JTextField txtDetectorNumber;
    private javax.swing.JTextField txtEvaNumber;
    private javax.swing.JTextField txtFireReportNumber;
    private javax.swing.JTextField txtGroupNumber;
    private javax.swing.JTextField txtInvolvedAddress;
    private javax.swing.JTextField txtInvolvedName;
    private javax.swing.JTextField txtLeader;
    private javax.swing.JTextField txtRemark;
    // End of variables declaration//GEN-END:variables
}
