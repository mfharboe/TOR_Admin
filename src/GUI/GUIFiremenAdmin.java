package GUI;

import BE.BEFireman;
import BE.BEZipcode;
import BLL.BLLCreate;
import BLL.BLLDelete;
import BLL.BLLRead;
import BLL.BLLUpdate;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.swing.DefaultListModel;

public class GUIFiremenAdmin extends javax.swing.JFrame {

    private static GUIFiremenAdmin m_instance;
    private DefaultListModel<BEFireman> firemenModel;
    private BEFireman m_fireman;
    private boolean isUpdate;

    /**
     * Creates new form GUIFiremenAdmin.
     */
    private GUIFiremenAdmin() {
        initComponents();
        this.setTitle(MessageDialog.getInstance().firemenAdminTitle());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initialSettings();
    }

    /**
     *
     * @return m_instance of GUIFiremenAdmin.
     */
    public static GUIFiremenAdmin getInstance() {
        if (m_instance == null) {
            m_instance = new GUIFiremenAdmin();
        }
        return m_instance;
    }

    /**
     * The initial settings for this class.
     */
    private void initialSettings() {
        firemenModel = new DefaultListModel<>();
        lstFiremen.setModel(firemenModel);
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
        btnBrowse.addActionListener(btn);
        btnDelete.addActionListener(btn);
        btnEdit.addActionListener(btn);
        btnSave.addActionListener(btn);
        btnNew.addActionListener(btn);
        lstFiremen.addMouseListener(mouse);
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
     * Sets everything to a default view
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
        btnDelete.setEnabled(enable);
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
            btnDelete.setEnabled(true);
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
        java.util.Date utilDate = dateChooser.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
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

    private BEFireman getNewDetails() {
        java.util.Date utilDate = dateChooser.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String address = txtAddress.getText();
        BEZipcode zip = ((BEZipcode) cmbZipcode.getSelectedItem());
        int phone = Integer.parseInt(txtPhone.getText());
        int payment = Integer.parseInt(txtPaymentNo.getText());
        boolean isteamleader = cbxIsTeamLeader.isSelected();
        String photo = txtPhotoPath.getText();
        BEFireman newFireman = new BEFireman(sqlDate, firstName, lastName, address, zip, phone, payment, isteamleader, photo);
        return newFireman;
    }

    /**
     * Invoke this method when the deletebutton os clicked.
     */
    private void onClickDelete() {
        BLLDelete.getInstance().deleteFireman(m_fireman);
        firemenModel.clear();
        fillFiremenList();
        clearSelection();
    }

    /**
     * Invoke this method when the editbutton is clicked.
     */
    private void onClickEdit() {
        enableTxtFields(true);
        btnSave.setEnabled(true);
        btnDelete.setEnabled(false);
        btnBrowse.setEnabled(true);
        isUpdate = true;
    }

    /**
     * Invoke this method when the browsebutton is clicked.
     */
    private void onClickBrowse() {

    }

    /**
     * Invoke this method when the savebutton is clicked.
     */
    private void onClickSave() {
        if (isUpdate == true) {
            getDetails();
            BLLUpdate.getInstance().updateFireman(m_fireman);
            firemenModel.clear();
            fillFiremenList();
            clearSelection();
        } else {
            BLLCreate.getInstance().createFireman(getNewDetails());
            firemenModel.clear();
            BLLRead.getInstance().clearFiremenArray();
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
        btnDelete.setEnabled(false);
        btnEdit.setEnabled(false);
        btnSave.setEnabled(true);
        isUpdate = false;
    }

    /**
     * Listeners for the buttons.
     */
    private class btnAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(btnDelete)) {
                onClickDelete();
            } else if (e.getSource().equals(btnEdit)) {
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
        btnDelete = new javax.swing.JButton();
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

        lstFiremen.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jScrollPane1.setViewportView(lstFiremen);

        txtFirstName.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtFirstName.setPreferredSize(new java.awt.Dimension(250, 38));

        txtLastName.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtLastName.setPreferredSize(new java.awt.Dimension(250, 38));

        txtAddress.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtAddress.setPreferredSize(new java.awt.Dimension(250, 38));

        txtPhone.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtPhone.setPreferredSize(new java.awt.Dimension(250, 38));

        cmbZipcode.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        txtPaymentNo.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtPaymentNo.setPreferredSize(new java.awt.Dimension(250, 38));

        txtPhotoPath.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnSave.setText("Gem");
        btnSave.setPreferredSize(new java.awt.Dimension(81, 38));

        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnDelete.setText("Slet");
        btnDelete.setPreferredSize(new java.awt.Dimension(81, 38));

        lblDate.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblDate.setText("Ansat d.");

        lblFirstName.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblFirstName.setText("Fornavn:");

        lblLastName.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblLastName.setText("Efternavn:");

        lblAddress.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblAddress.setText("Addresse:");

        lblZipcode.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblZipcode.setText("Post nr.");

        lblPhone.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblPhone.setText("Telefon nr.");

        lblPayment.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblPayment.setText("Betalings nr.");

        lblPicture.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblPicture.setText("Billede:");

        cbxIsTeamLeader.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cbxIsTeamLeader.setText("Holdleder uddannet");

        btnBrowse.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnBrowse.setText("Browse..");
        btnBrowse.setPreferredSize(new java.awt.Dimension(81, 38));

        btnEdit.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnEdit.setText("Rediger");
        btnEdit.setPreferredSize(new java.awt.Dimension(81, 38));

        btnNew.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnNew.setText("Ny..");

        javax.swing.GroupLayout pnlFiremenLayout = new javax.swing.GroupLayout(pnlFiremen);
        pnlFiremen.setLayout(pnlFiremenLayout);
        pnlFiremenLayout.setHorizontalGroup(
            pnlFiremenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFiremenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlFiremenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFiremenLayout.createSequentialGroup()
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlFiremenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlFiremenLayout.createSequentialGroup()
                            .addGroup(pnlFiremenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblLastName)
                                .addComponent(lblFirstName)
                                .addComponent(lblAddress)
                                .addComponent(lblZipcode)
                                .addComponent(lblPhone)
                                .addComponent(lblPayment)
                                .addComponent(lblPicture)
                                .addComponent(lblDate))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlFiremenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnlFiremenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtFirstName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtLastName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtPhone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtPaymentNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbZipcode, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(cbxIsTeamLeader)
                                .addComponent(txtPhotoPath, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFiremenLayout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlFiremenLayout.setVerticalGroup(
            pnlFiremenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFiremenLayout.createSequentialGroup()
                .addGroup(pnlFiremenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlFiremenLayout.createSequentialGroup()
                        .addGroup(pnlFiremenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlFiremenLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblDate)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFiremenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFirstName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFiremenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLastName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFiremenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAddress))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFiremenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbZipcode, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblZipcode))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFiremenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPhone))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFiremenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPaymentNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPayment))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxIsTeamLeader)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlFiremenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPhotoPath, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPicture))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addGroup(pnlFiremenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFiremenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnNew, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlFiremen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlFiremen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnDelete;
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
