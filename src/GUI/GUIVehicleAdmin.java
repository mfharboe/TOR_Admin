package GUI;

import BE.BEVehicle;
import BLL.BLLCreate;
import BLL.BLLDelete;
import BLL.BLLRead;
import BLL.BLLUpdate;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;

public class GUIVehicleAdmin extends javax.swing.JFrame {

    private DefaultListModel<BEVehicle> vehicleModel;
    private BEVehicle m_vehicle;
    private boolean isUpdate;

    /**
     * Creates new form GUIVehicleAdmin.
     */
    public GUIVehicleAdmin() {
        initComponents();
        this.setTitle(MessageDialog.getInstance().vehicleAdminTitle());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initialSettings();
    }

    /**
     * Sets the initial settings for this class.
     */
    private void initialSettings() {
        vehicleModel = new DefaultListModel<>();
        lstVehicles.setModel(vehicleModel);
        txtOdinNr.setEnabled(false);
        addListeners();
        addColors();
        fillVehicleList();
        clearSelection();
    }

    /**
     * Adds listeners.
     */
    private void addListeners() {
        btnAction btn = new btnAction();
        mouseAction mouse = new mouseAction();
        txtAction txt = new txtAction();
        btnDelete.addActionListener(btn);
        btnEdit.addActionListener(btn);
        btnSave.addActionListener(btn);
        btnNew.addActionListener(btn);
        lstVehicles.addMouseListener(mouse);
        lstVehicles.addKeyListener(txt);
        txtOdinNr.addKeyListener(txt);
        txtRegNr.addKeyListener(txt);
        txtBrand.addKeyListener(txt);
        txtModel.addKeyListener(txt);
        txtDescription.addKeyListener(txt);
    }

    /**
     * Adds colors.
     */
    private void addColors() {
        this.getContentPane().setBackground(Color.WHITE);
        pnlVehicles.setBackground(Color.WHITE);
    }

    /**
     * Fills the Vehicle List.
     */
    private void fillVehicleList() {
        for (BEVehicle vehicle : BLLRead.getInstance().readAllVehicles()) {
            vehicleModel.addElement(vehicle);
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
     * Clears the textboxes.
     */
    private void clearDetails() {
        txtOdinNr.setText(MessageDialog.getInstance().emptyString());
        txtRegNr.setText(MessageDialog.getInstance().emptyString());
        txtBrand.setText(MessageDialog.getInstance().emptyString());
        txtModel.setText(MessageDialog.getInstance().emptyString());
        txtDescription.setText(MessageDialog.getInstance().emptyString());
    }

    /**
     * Enables or disables the buttons.
     *
     * @param enable
     */
    private void enableBtn(boolean enable) {
        btnDelete.setEnabled(enable);
        btnEdit.setEnabled(enable);
        btnSave.setEnabled(enable);
    }

    /**
     * Enables or disables the textfields.
     *
     * @param enable
     */
    private void enableTxtFields(boolean enable) {
        txtRegNr.setEnabled(enable);
        txtBrand.setEnabled(enable);
        txtModel.setEnabled(enable);
        txtDescription.setEnabled(enable);
    }

    /**
     * Invoke this method when a vehicle is clicked upon in the list.
     */
    private void onListClick() {
        enableBtn(false);
        enableTxtFields(false);
        txtOdinNr.setEnabled(false);
        if (!vehicleModel.isEmpty() && lstVehicles.getSelectedIndex() != -1) {
            setDetails();
            btnEdit.setEnabled(true);
            btnDelete.setEnabled(true);
        } else {
            enableBtn(false);
            clearDetails();
        }
    }

    /**
     * Finds the details for a vehicle marked in the list.
     */
    private void setDetails() {
        for (BEVehicle vehicle : BLLRead.getInstance().readAllVehicles()) {
            if (((BEVehicle) lstVehicles.getSelectedValue()).getM_odinNumber() == vehicle.getM_odinNumber()) {
                m_vehicle = vehicle;
                fillDetails(m_vehicle);
                return;
            }
        }
    }

    /**
     * Fills the textboxes with information from a vehicle marked in the list.
     *
     * @param vehicle
     */
    private void fillDetails(BEVehicle vehicle) {
        txtOdinNr.setText(String.valueOf(vehicle.getM_odinNumber()));
        txtRegNr.setText(vehicle.getM_registrationNumber());
        txtBrand.setText(vehicle.getM_brand());
        txtModel.setText(vehicle.getM_model());
        txtDescription.setText(vehicle.getM_description());
    }

    /**
     * Sets all the new values from the textboxes to the vehicle marked in the
     * list.
     *
     * @return m_vehicle
     */
    private BEVehicle getDetails() {
        m_vehicle.setM_odinNumber(Integer.parseInt(txtOdinNr.getText()));
        m_vehicle.setM_registrationNumber(txtRegNr.getText());
        m_vehicle.setM_brand(txtBrand.getText());
        m_vehicle.setM_model(txtModel.getText());
        m_vehicle.setM_description(txtDescription.getText());
        return m_vehicle;
    }

    /**
     *
     * @return new BEVehicle.
     */
    private BEVehicle getNewDetails() {
        int odinNumber = Integer.parseInt(txtOdinNr.getText());
        String regNr = txtRegNr.getText();
        String brand = txtBrand.getText();
        String model = txtModel.getText();
        String description = txtDescription.getText();
        BEVehicle newVehicle = new BEVehicle(odinNumber, regNr, brand, model, description);
        return newVehicle;
    }

    private boolean checkForIntegers(String input) {
        return input.matches(MessageDialog.getInstance().txtIntChecker());
    }

    /**
     * Invoke this method when the deletebutton is clicked.
     */
    private void onClickDelete() {
        boolean reply = MessageDialog.getInstance().deleteVehicle();
        if (reply == true) {
            BLLDelete.getInstance().deleteVehicle(m_vehicle);
            vehicleModel.clear();
            fillVehicleList();
            clearSelection();
        }
    }

    /**
     * Invoke this method when the edit button is clicked.
     */
    private void onClickEdit() {
        enableTxtFields(true);
        btnEdit.setEnabled(false);
        btnSave.setEnabled(true);
        btnDelete.setEnabled(false);
        isUpdate = true;
    }

    /**
     * Invoke this method when the save button is clicked.
     */
    private void onClickSave() {
        if (isUpdate == true) {
            BLLUpdate.getInstance().updateVehicle(getDetails());
            vehicleModel.clear();
            fillVehicleList();
            clearSelection();
        } else {
            BLLCreate.getInstance().createVehicle(getNewDetails());
            vehicleModel.clear();
            fillVehicleList();
            clearSelection();
            txtOdinNr.setEnabled(false);
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
        btnSave.setEnabled(false);
        txtOdinNr.setEnabled(true);
        isUpdate = false;
    }

    private void onKeyPress() {
        if (txtOdinNr.getText().isEmpty() || txtRegNr.getText().isEmpty() || txtBrand.getText().isEmpty() || txtModel.getText().isEmpty() || txtDescription.getText().isEmpty()) {
            btnSave.setEnabled(false);
        } else {
            btnSave.setEnabled(true);
        }
        if (!checkForIntegers(txtOdinNr.getText())) {
            txtOdinNr.setText(MessageDialog.getInstance().emptyString());
            MessageDialog.getInstance().noTextHere();
        }
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

    private class txtAction extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getSource().equals(lstVehicles)) {
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

        pnlVehicles = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstVehicles = new javax.swing.JList();
        txtOdinNr = new javax.swing.JTextField();
        txtRegNr = new javax.swing.JTextField();
        txtBrand = new javax.swing.JTextField();
        txtModel = new javax.swing.JTextField();
        txtDescription = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        lblOdinNr = new javax.swing.JLabel();
        lblRegNr = new javax.swing.JLabel();
        lblBrand = new javax.swing.JLabel();
        lblModel = new javax.swing.JLabel();
        lblDescription = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(679, 577));

        pnlVehicles.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Køretøjer", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 24))); // NOI18N
        pnlVehicles.setLayout(null);

        lstVehicles.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jScrollPane1.setViewportView(lstVehicles);

        pnlVehicles.add(jScrollPane1);
        jScrollPane1.setBounds(18, 32, 258, 572);

        txtOdinNr.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtOdinNr.setPreferredSize(new java.awt.Dimension(250, 38));
        pnlVehicles.add(txtOdinNr);
        txtOdinNr.setBounds(384, 32, 340, 38);

        txtRegNr.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtRegNr.setPreferredSize(new java.awt.Dimension(250, 38));
        pnlVehicles.add(txtRegNr);
        txtRegNr.setBounds(384, 72, 340, 38);

        txtBrand.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtBrand.setPreferredSize(new java.awt.Dimension(250, 38));
        pnlVehicles.add(txtBrand);
        txtBrand.setBounds(384, 112, 340, 38);

        txtModel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtModel.setPreferredSize(new java.awt.Dimension(250, 38));
        pnlVehicles.add(txtModel);
        txtModel.setBounds(384, 152, 340, 38);

        txtDescription.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtDescription.setPreferredSize(new java.awt.Dimension(250, 38));
        pnlVehicles.add(txtDescription);
        txtDescription.setBounds(384, 192, 340, 38);

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnSave.setText("Gem");
        btnSave.setPreferredSize(new java.awt.Dimension(81, 38));
        pnlVehicles.add(btnSave);
        btnSave.setBounds(677, 566, 81, 38);

        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnDelete.setText("Slet");
        btnDelete.setPreferredSize(new java.awt.Dimension(81, 38));
        pnlVehicles.add(btnDelete);
        btnDelete.setBounds(382, 566, 81, 38);

        lblOdinNr.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblOdinNr.setText("*Odin nr.");
        pnlVehicles.add(lblOdinNr);
        lblOdinNr.setBounds(294, 42, 69, 19);

        lblRegNr.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblRegNr.setText("*Reg. nr.");
        pnlVehicles.add(lblRegNr);
        lblRegNr.setBounds(294, 82, 69, 19);

        lblBrand.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblBrand.setText("*Mærke:");
        pnlVehicles.add(lblBrand);
        lblBrand.setBounds(294, 122, 69, 19);

        lblModel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblModel.setText("*Model:");
        pnlVehicles.add(lblModel);
        lblModel.setBounds(294, 162, 69, 19);

        lblDescription.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblDescription.setText("*Type:");
        pnlVehicles.add(lblDescription);
        lblDescription.setBounds(294, 202, 69, 19);

        btnEdit.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnEdit.setText("Rediger");
        btnEdit.setPreferredSize(new java.awt.Dimension(81, 38));
        pnlVehicles.add(btnEdit);
        btnEdit.setBounds(470, 566, 81, 38);

        btnNew.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnNew.setText("Ny..");
        btnNew.setPreferredSize(new java.awt.Dimension(81, 38));
        pnlVehicles.add(btnNew);
        btnNew.setBounds(294, 566, 81, 38);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(pnlVehicles, javax.swing.GroupLayout.PREFERRED_SIZE, 776, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(pnlVehicles, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBrand;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblModel;
    private javax.swing.JLabel lblOdinNr;
    private javax.swing.JLabel lblRegNr;
    private javax.swing.JList lstVehicles;
    private javax.swing.JPanel pnlVehicles;
    private javax.swing.JTextField txtBrand;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtModel;
    private javax.swing.JTextField txtOdinNr;
    private javax.swing.JTextField txtRegNr;
    // End of variables declaration//GEN-END:variables
}
