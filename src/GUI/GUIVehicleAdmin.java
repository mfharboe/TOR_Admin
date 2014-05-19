package GUI;

import BE.BEVehicle;
import BLL.BLLDelete;
import BLL.BLLRead;
import BLL.BLLUpdate;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;

public class GUIVehicleAdmin extends javax.swing.JFrame {

    private static GUIVehicleAdmin m_instance;
    private DefaultListModel<BEVehicle> vehicleModel;
    private BEVehicle m_vehicle;

    /**
     * Creates new form GUIVehicleAdmin.
     */
    private GUIVehicleAdmin() {
        initComponents();
        this.setTitle(MessageDialog.getInstance().vehicleAdminTitle());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initialSettings();
    }

    /**
     *
     * @return m_instance of GUIVehicleAdmin.
     */
    public static GUIVehicleAdmin getInstance() {
        if (m_instance == null) {
            m_instance = new GUIVehicleAdmin();
        }
        return m_instance;
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
        btnDelete.addActionListener(btn);
        btnEdit.addActionListener(btn);
        btnSave.addActionListener(btn);
        lstVehicles.addMouseListener(mouse);
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
     * Envoke this method when a vehicle is clicked upon in the list.
     */
    private void onListClick() {
        enableBtn(false);
        enableTxtFields(false);
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
     * Envoke this method when the deletebutton is clicked.
     */
    private void onClickDelete() {
        BLLDelete.getInstance().deleteVehicle(m_vehicle);
        vehicleModel.clear();
        fillVehicleList();
        clearSelection();
    }

    /**
     * Envoke this method when the edit button is clicked.
     */
    private void onClickEdit() {
        enableTxtFields(true);
        btnSave.setEnabled(true);
        btnDelete.setEnabled(false);
    }

    /**
     * Envoke this method when the save button is clicked.
     */
    private void onClickSave() {
        getDetails();
        BLLUpdate.getInstance().updateVehicle(m_vehicle);
        vehicleModel.clear();
        fillVehicleList();
        clearSelection();
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

        lstVehicles.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jScrollPane1.setViewportView(lstVehicles);

        txtOdinNr.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtOdinNr.setPreferredSize(new java.awt.Dimension(250, 38));

        txtRegNr.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtRegNr.setPreferredSize(new java.awt.Dimension(250, 38));

        txtBrand.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtBrand.setPreferredSize(new java.awt.Dimension(250, 38));

        txtModel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtModel.setPreferredSize(new java.awt.Dimension(250, 38));

        txtDescription.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtDescription.setPreferredSize(new java.awt.Dimension(250, 38));

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnSave.setText("Gem");
        btnSave.setPreferredSize(new java.awt.Dimension(81, 38));

        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnDelete.setText("Slet");
        btnDelete.setPreferredSize(new java.awt.Dimension(81, 38));

        lblOdinNr.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblOdinNr.setText("Odin nr.");

        lblRegNr.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblRegNr.setText("Reg. nr.");

        lblBrand.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblBrand.setText("Mærke:");

        lblModel.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblModel.setText("Model:");

        lblDescription.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblDescription.setText("Type:");

        btnEdit.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnEdit.setText("Rediger");
        btnEdit.setPreferredSize(new java.awt.Dimension(81, 38));

        btnNew.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnNew.setText("Ny..");
        btnNew.setPreferredSize(new java.awt.Dimension(81, 38));

        javax.swing.GroupLayout pnlVehiclesLayout = new javax.swing.GroupLayout(pnlVehicles);
        pnlVehicles.setLayout(pnlVehiclesLayout);
        pnlVehiclesLayout.setHorizontalGroup(
            pnlVehiclesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVehiclesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlVehiclesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlVehiclesLayout.createSequentialGroup()
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlVehiclesLayout.createSequentialGroup()
                        .addGroup(pnlVehiclesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblOdinNr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblRegNr, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                            .addComponent(lblBrand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblModel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlVehiclesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtOdinNr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRegNr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtModel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlVehiclesLayout.setVerticalGroup(
            pnlVehiclesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVehiclesLayout.createSequentialGroup()
                .addGroup(pnlVehiclesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlVehiclesLayout.createSequentialGroup()
                        .addGroup(pnlVehiclesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlVehiclesLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblOdinNr))
                            .addComponent(txtOdinNr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlVehiclesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRegNr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRegNr))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlVehiclesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblBrand))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlVehiclesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtModel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblModel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlVehiclesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDescription))
                        .addGap(252, 252, 252)
                        .addGroup(pnlVehiclesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlVehicles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlVehicles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
