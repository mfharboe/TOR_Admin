package GUI;

import BE.BEFireman;
import BE.BEZipcode;
import BLL.BLLCreate;
import BLL.BLLRead;
import BLL.BLLUpdate;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

public class GUIFiremenAdmin extends javax.swing.JFrame {

    private DefaultListModel<BEFireman> firemenModel;
    private JFileChooser fc;
    private BEFireman m_fireman;
    private boolean isUpdate;

    /**
     * Creates new form GUIFiremenAdmin.
     */
    public GUIFiremenAdmin() {
        initComponents();
        this.setTitle(MessageDialog.getInstance().firemenAdminTitle());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initialSettings();
    }

    /**
     * The initial settings for this class.
     */
    private void initialSettings() {
        firemenModel = new DefaultListModel<>();
        lstFiremen.setModel(firemenModel);
        fc = new JFileChooser();
        addListeners();
        addColors();
        fillFiremenList();
        fillZipCombo();
        clearSelection();
    }

    /**
     * Adds listeners
     */
    private void addListeners() {
        btnAction btn = new btnAction();
        mouseAction mouse = new mouseAction();
        txtAction txt = new txtAction();
        btnBrowse.addActionListener(btn);
        btnEdit.addActionListener(btn);
        btnSave.addActionListener(btn);
        btnNew.addActionListener(btn);
        lstFiremen.addMouseListener(mouse);
        lstFiremen.addKeyListener(txt);
        txtPhone.addKeyListener(txt);
        txtPaymentNo.addKeyListener(txt);
        txtFirstName.addKeyListener(txt);
        txtLastName.addKeyListener(txt);
        txtAddress.addKeyListener(txt);
    }

    /**
     * Adds colors.
     */
    private void addColors() {
        this.getContentPane().setBackground(Color.WHITE);
        pnlFiremen.setBackground(Color.WHITE);
        cbxIsTeamLeader.setBackground(Color.WHITE);
    }

    /**
     * Fills the Fireman List.
     */
    private void fillFiremenList() {
        for (BEFireman firemen : BLLRead.getInstance().readAllFiremen()) {
            firemenModel.addElement(firemen);
        }
    }

    /**
     * Fills the Zipcode Combobox.
     */
    private void fillZipCombo() {
        cmbZipcode.addItem(MessageDialog.getInstance().zipType());
        for (BEZipcode zipcode : BLLRead.getInstance().readAllZipcodes()) {
            cmbZipcode.addItem(zipcode);
        }
    }

    /**
     * Sets everything to a default view.
     */
    private void clearSelection() {
        clearDetails();
        enableBtn(false);
        enableTxtFields(false);
    }

    /**
     * Clears the textbokses etc.
     */
    private void clearDetails() {
        dateChooser.setDate(null);
        txtFirstName.setText(MessageDialog.getInstance().emptyString());
        txtLastName.setText(MessageDialog.getInstance().emptyString());
        txtAddress.setText(MessageDialog.getInstance().emptyString());
        cmbZipcode.setSelectedIndex(0);
        txtPhone.setText(MessageDialog.getInstance().emptyString());
        txtPaymentNo.setText(MessageDialog.getInstance().emptyString());
        cbxIsTeamLeader.setSelected(false);
        txtPhotoPath.setText(MessageDialog.getInstance().emptyString());
    }

    /**
     * Enables or disables the buttons.
     *
     * @param enable
     */
    private void enableBtn(boolean enable) {
        btnBrowse.setEnabled(enable);
        btnEdit.setEnabled(enable);
        btnSave.setEnabled(enable);
    }

    /**
     * Enables or disables the Textfields.
     *
     * @param enable
     */
    private void enableTxtFields(boolean enable) {
        dateChooser.setEnabled(enable);
        txtFirstName.setEnabled(enable);
        txtLastName.setEnabled(enable);
        txtAddress.setEnabled(enable);
        cmbZipcode.setEnabled(enable);
        txtPhone.setEnabled(enable);
        txtPaymentNo.setEnabled(enable);
        cbxIsTeamLeader.setEnabled(enable);
        txtPhotoPath.setEnabled(enable);
    }

    /**
     * Invoke this method when a fireman is clicked upon in the list.
     */
    private void onListClick() {
        enableBtn(false);
        enableTxtFields(false);
        if (!firemenModel.isEmpty() && lstFiremen.getSelectedIndex() != -1) {
            setDetails();
            btnEdit.setEnabled(true);
        } else {
            enableBtn(false);
            clearDetails();
        }
    }

    /**
     * Finds the details for the fireman that is marked in the list.
     */
    private void setDetails() {
        for (BEFireman fireman : BLLRead.getInstance().readAllFiremen()) {
            if (((BEFireman) lstFiremen.getSelectedValue()).getM_id() == fireman.getM_id()) {
                m_fireman = fireman;
                fillDetails(m_fireman);
                return;
            }
        }
    }

    /**
     * Fills the textbokses etc. with information from the fireman marked in the
     * list.
     *
     * @param fireman
     */
    private void fillDetails(BEFireman fireman) {
        dateChooser.setDate(fireman.getM_recruited());
        txtFirstName.setText(fireman.getM_firstName());
        txtLastName.setText(fireman.getM_lastName());
        txtAddress.setText(fireman.getM_address());
        cmbZipcode.setSelectedItem(fireman.getM_zipCode());
        txtPhone.setText(String.valueOf(fireman.getM_phone()));
        txtPaymentNo.setText(String.valueOf(fireman.getM_paymentNumber()));
        cbxIsTeamLeader.setSelected(fireman.isM_isTeamLeader());
        txtPhotoPath.setText(fireman.getM_photoPath());
    }

    /**
     * Sets all the new values from the textboxes etc. to the fireman marked in
     * the list.
     *
     * @return m_fireman
     */
    private BEFireman getDetails() {
        java.sql.Date sqlDate = null;
        if (!((JTextField) dateChooser.getDateEditor().getUiComponent()).getText().isEmpty()) {
            java.util.Date utilDate = dateChooser.getDate();
            sqlDate = new java.sql.Date(utilDate.getTime());
        }
        m_fireman.setM_recruited(sqlDate);
        m_fireman.setM_firstName(txtFirstName.getText());
        m_fireman.setM_lastName(txtLastName.getText());
        m_fireman.setM_address(txtAddress.getText());
        m_fireman.setM_zipCode(null);
        if (cmbZipcode.getSelectedIndex() != 0) {
            m_fireman.setM_zipCode((BEZipcode) cmbZipcode.getSelectedItem());
        }
        m_fireman.setM_phone(Integer.parseInt(txtPhone.getText()));
        m_fireman.setM_paymentNumber(Integer.parseInt(txtPaymentNo.getText()));
        m_fireman.setM_isTeamLeader(cbxIsTeamLeader.isSelected());
        m_fireman.setM_photoPath(txtPhotoPath.getText());
        return m_fireman;
    }

    /**
     *
     * @return new BEFireman
     */
    private BEFireman getNewDetails() {
        java.sql.Date sqlDate = null;
        if (!((JTextField) dateChooser.getDateEditor().getUiComponent()).getText().isEmpty()) {
            java.util.Date utilDate = dateChooser.getDate();
            sqlDate = new java.sql.Date(utilDate.getTime());
        }
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String address = txtAddress.getText();
        BEZipcode zip = null;
        if (cmbZipcode.getSelectedIndex() != 0) {
            zip = ((BEZipcode) cmbZipcode.getSelectedItem());
        }
        int phone = Integer.parseInt(txtPhone.getText());
        int payment = Integer.parseInt(txtPaymentNo.getText());
        boolean isteamleader = cbxIsTeamLeader.isSelected();
        String photo = txtPhotoPath.getText();
        BEFireman newFireman = new BEFireman(sqlDate, firstName, lastName, address, zip, phone, payment, isteamleader, photo);
        return newFireman;
    }

    private boolean checkForIntegers(String input) {
        return input.matches(MessageDialog.getInstance().txtIntChecker());
    }

    /**
     * Invoke this method when the editbutton is clicked.
     */
    private void onClickEdit() {
        enableTxtFields(true);
        btnEdit.setEnabled(false);
        btnSave.setEnabled(true);
        btnBrowse.setEnabled(true);
        isUpdate = true;
    }

    /**
     * Invoke this method when the browse-button is clicked.
     */
    private void onClickBrowse() {
        int status = fc.showOpenDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            txtPhotoPath.setText(selectedFile.getAbsolutePath());
        }
    }

    /**
     * Invoke this method when the savebutton is clicked.
     */
    private void onClickSave() {
        if (isUpdate == true) {
            BLLUpdate.getInstance().updateFireman(getDetails());
            firemenModel.clear();
            fillFiremenList();
            clearSelection();
        } else {
            BLLCreate.getInstance().createFireman(getNewDetails());
            firemenModel.clear();
            fillFiremenList();
            clearSelection();
        }
    }

    /**
     * Invoke this method when the newbutton is clicked.
     */
    private void onClickNew() {
        clearDetails();
        enableTxtFields(true);
        btnEdit.setEnabled(false);
        btnSave.setEnabled(false);
        btnBrowse.setEnabled(true);
        isUpdate = false;
    }

    private void onKeyPress() {
        if (txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty() || txtAddress.getText().isEmpty()
                || txtPhone.getText().isEmpty() || txtPaymentNo.getText().isEmpty()) {
            btnSave.setEnabled(false);
        } else {
            btnSave.setEnabled(true);
        }
        if (!(txtPhone.getText().isEmpty())) {
            if (!checkForIntegers(txtPhone.getText())) {
                txtPhone.setText(MessageDialog.getInstance().emptyString());
                MessageDialog.getInstance().noTextHere();
            }
        }
        if (!(txtPaymentNo.getText().isEmpty())) {
            if (!checkForIntegers(txtPaymentNo.getText())) {
                txtPaymentNo.setText(MessageDialog.getInstance().emptyString());
                MessageDialog.getInstance().noTextHere();
            }
        }
    }

    /**
     * Listeners for the buttons.
     */
    private class btnAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(btnEdit)) {
                onClickEdit();
            } else if (e.getSource().equals(btnBrowse)) {
                onClickBrowse();
            } else if (e.getSource().equals(btnSave)) {
                onClickSave();
            } else if (e.getSource().equals(btnNew)) {
                onClickNew();
            }

        }

    }

    /**
     * Listeners for the mouse.
     */
    private class mouseAction extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource().equals(lstFiremen) && e.getClickCount() == 1) {
                onListClick();
            }
        }

    }

    /**
     * Listeners for the txtfields.
     */
    private class txtAction extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getSource().equals(lstFiremen)) {
                onListClick();
            } else {
                onKeyPress();
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

        pnlFiremen = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstFiremen = new javax.swing.JList();
        txtFirstName = new javax.swing.JTextField();
        txtLastName = new javax.swing.JTextField();
        txtAddress = new javax.swing.JTextField();
        dateChooser = new com.toedter.calendar.JDateChooser();
        txtPhone = new javax.swing.JTextField();
        cmbZipcode = new javax.swing.JComboBox();
        txtPaymentNo = new javax.swing.JTextField();
        txtPhotoPath = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        lblDate = new javax.swing.JLabel();
        lblFirstName = new javax.swing.JLabel();
        lblLastName = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        lblZipcode = new javax.swing.JLabel();
        lblPhone = new javax.swing.JLabel();
        lblPayment = new javax.swing.JLabel();
        lblPicture = new javax.swing.JLabel();
        cbxIsTeamLeader = new javax.swing.JCheckBox();
        btnBrowse = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlFiremen.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mandskab", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 24))); // NOI18N
        pnlFiremen.setLayout(null);

        lstFiremen.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jScrollPane1.setViewportView(lstFiremen);

        pnlFiremen.add(jScrollPane1);
        jScrollPane1.setBounds(18, 32, 258, 572);

        txtFirstName.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtFirstName.setPreferredSize(new java.awt.Dimension(250, 38));
        pnlFiremen.add(txtFirstName);
        txtFirstName.setBounds(392, 72, 340, 38);

        txtLastName.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtLastName.setPreferredSize(new java.awt.Dimension(250, 38));
        pnlFiremen.add(txtLastName);
        txtLastName.setBounds(392, 112, 340, 38);

        txtAddress.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtAddress.setPreferredSize(new java.awt.Dimension(250, 38));
        pnlFiremen.add(txtAddress);
        txtAddress.setBounds(392, 152, 340, 38);
        pnlFiremen.add(dateChooser);
        dateChooser.setBounds(392, 32, 180, 38);

        txtPhone.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtPhone.setPreferredSize(new java.awt.Dimension(250, 38));
        pnlFiremen.add(txtPhone);
        txtPhone.setBounds(392, 232, 340, 38);

        cmbZipcode.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        pnlFiremen.add(cmbZipcode);
        cmbZipcode.setBounds(392, 192, 340, 38);

        txtPaymentNo.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtPaymentNo.setPreferredSize(new java.awt.Dimension(250, 38));
        pnlFiremen.add(txtPaymentNo);
        txtPaymentNo.setBounds(392, 272, 340, 38);

        txtPhotoPath.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        pnlFiremen.add(txtPhotoPath);
        txtPhotoPath.setBounds(392, 342, 340, 38);

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnSave.setText("Gem");
        btnSave.setPreferredSize(new java.awt.Dimension(81, 38));
        pnlFiremen.add(btnSave);
        btnSave.setBounds(670, 560, 81, 40);

        lblDate.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblDate.setText("*Ansat d.");
        pnlFiremen.add(lblDate);
        lblDate.setBounds(294, 42, 63, 19);

        lblFirstName.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblFirstName.setText("*Fornavn:");
        pnlFiremen.add(lblFirstName);
        lblFirstName.setBounds(294, 82, 66, 19);

        lblLastName.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblLastName.setText("*Efternavn:");
        pnlFiremen.add(lblLastName);
        lblLastName.setBounds(294, 122, 76, 19);

        lblAddress.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblAddress.setText("*Adresse:");
        pnlFiremen.add(lblAddress);
        lblAddress.setBounds(294, 162, 65, 19);

        lblZipcode.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblZipcode.setText("*By:");
        pnlFiremen.add(lblZipcode);
        lblZipcode.setBounds(294, 202, 30, 19);

        lblPhone.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblPhone.setText("*Telefon nr.");
        pnlFiremen.add(lblPhone);
        lblPhone.setBounds(294, 242, 80, 19);

        lblPayment.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblPayment.setText("*Betalings nr.");
        pnlFiremen.add(lblPayment);
        lblPayment.setBounds(294, 282, 88, 19);

        lblPicture.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblPicture.setText("(billede):");
        pnlFiremen.add(lblPicture);
        lblPicture.setBounds(294, 352, 55, 19);

        cbxIsTeamLeader.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cbxIsTeamLeader.setText("Holdleder uddannet");
        pnlFiremen.add(cbxIsTeamLeader);
        cbxIsTeamLeader.setBounds(392, 312, 190, 27);

        btnBrowse.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnBrowse.setText("Browse..");
        btnBrowse.setPreferredSize(new java.awt.Dimension(81, 38));
        pnlFiremen.add(btnBrowse);
        btnBrowse.setBounds(629, 390, 100, 38);

        btnEdit.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnEdit.setText("Rediger");
        btnEdit.setPreferredSize(new java.awt.Dimension(81, 38));
        pnlFiremen.add(btnEdit);
        btnEdit.setBounds(540, 560, 100, 40);

        btnNew.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnNew.setText("Ny..");
        pnlFiremen.add(btnNew);
        btnNew.setBounds(430, 560, 81, 40);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(pnlFiremen, javax.swing.GroupLayout.PREFERRED_SIZE, 776, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(pnlFiremen, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox cbxIsTeamLeader;
    private javax.swing.JComboBox cmbZipcode;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblPayment;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblPicture;
    private javax.swing.JLabel lblZipcode;
    private javax.swing.JList lstFiremen;
    private javax.swing.JPanel pnlFiremen;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtPaymentNo;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtPhotoPath;
    // End of variables declaration//GEN-END:variables
}
