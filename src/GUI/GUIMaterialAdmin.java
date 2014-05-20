package GUI;

import BE.BEMaterial;
import BLL.BLLCreate;
import BLL.BLLDelete;
import BLL.BLLRead;
import BLL.BLLUpdate;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;

public class GUIMaterialAdmin extends javax.swing.JFrame {

    private static GUIMaterialAdmin m_instance;
    private DefaultListModel<BEMaterial> materialModel;
    private BEMaterial m_material;
    private boolean isUpdate;

    /**
     * Creates new form GUIMaterialAdmin.
     */
    private GUIMaterialAdmin() {
        initComponents();
        this.setTitle(MessageDialog.getInstance().materialAdminTitle());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initialSettings();
    }

    /**
     *
     * @return m_instance of GUIMaterialAdmin.
     */
    public static GUIMaterialAdmin getInstance() {
        if (m_instance == null) {
            m_instance = new GUIMaterialAdmin();
        }
        return m_instance;
    }

    /**
     * Sets the initial settings for this class.
     */
    private void initialSettings() {
        materialModel = new DefaultListModel<>();
        lstMaterial.setModel(materialModel);
        addListeners();
        addColors();
        fillMaterialList();
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
        btnNew.addActionListener(btn);
        lstMaterial.addMouseListener(mouse);
    }

    /**
     * Adds colors.
     */
    private void addColors() {
        this.getContentPane().setBackground(Color.WHITE);
        pnlMaterial.setBackground(Color.WHITE);
    }

    /**
     * Fills the Material List.
     */
    private void fillMaterialList() {
        for (BEMaterial material : BLLRead.getInstance().readAllMaterials()) {
            materialModel.addElement(material);
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
     * Clears the textboks.
     */
    private void clearDetails() {
        txtMaterial.setText(MessageDialog.getInstance().emptyString());
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
     * Enables or disables the textbox.
     *
     * @param enable
     */
    private void enableTxtFields(boolean enable) {
        txtMaterial.setEnabled(enable);
    }

    /**
     * Invoke this method when some material is clicked upon in the list.
     */
    private void onListClick() {
        enableBtn(false);
        enableTxtFields(false);
        if (!materialModel.isEmpty() && lstMaterial.getSelectedIndex() != -1) {
            setDetails();
            btnEdit.setEnabled(true);
            btnDelete.setEnabled(true);
        } else {
            enableBtn(false);
            clearDetails();
        }
    }

    /**
     * Finds the details for the material marked in the list.
     */
    private void setDetails() {
        for (BEMaterial material : BLLRead.getInstance().readAllMaterials()) {
            if (((BEMaterial) lstMaterial.getSelectedValue()).getM_id() == material.getM_id()) {
                m_material = material;
                fillDetails(m_material);
                return;
            }
        }
    }

    /**
     * Fills the textboks with information from the material marked in the list.
     *
     * @param material
     */
    private void fillDetails(BEMaterial material) {
        txtMaterial.setText(material.getM_description());
    }

    /**
     * Sets all the new values from the textbox to the material marked in the
     * list.
     *
     * @return m_material
     */
    private BEMaterial getDetails() {
        m_material.setM_description(txtMaterial.getText());
        return m_material;
    }

    /**
     *
     * @return new BEMaterial
     */
    private BEMaterial getNewDetails() {
        String description = txtMaterial.getText();
        BEMaterial newMaterial = new BEMaterial(description);
        return newMaterial;
    }

    /**
     * Invoke this method when the deletebutton is clicked.
     */
    private void onClickDelete() {
        BLLDelete.getInstance().deleteMaterial(m_material);
        materialModel.clear();
        fillMaterialList();
        clearSelection();
    }

    /**
     * Invoke this method when the editbutton is clicked.
     */
    private void onClickEdit() {
        enableTxtFields(true);
        btnSave.setEnabled(true);
        btnDelete.setEnabled(false);
        isUpdate = true;
    }

    /**
     * Invoke this method when the savebutton is clicked.
     */
    private void onClickSave() {
        if (isUpdate == true) {
            BLLUpdate.getInstance().updateMaterial(getDetails());
            materialModel.clear();
            fillMaterialList();
            clearSelection();
        } else {
            BLLCreate.getInstance().createMaterial(getNewDetails());
            materialModel.clear();
            fillMaterialList();
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

        pnlMaterial = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstMaterial = new javax.swing.JList();
        txtMaterial = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        lblMaterial = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(679, 577));

        pnlMaterial.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Materiel", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 24))); // NOI18N

        lstMaterial.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jScrollPane1.setViewportView(lstMaterial);

        txtMaterial.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtMaterial.setPreferredSize(new java.awt.Dimension(250, 38));

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnSave.setText("Gem");
        btnSave.setPreferredSize(new java.awt.Dimension(81, 38));

        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnDelete.setText("Slet");
        btnDelete.setPreferredSize(new java.awt.Dimension(81, 38));

        lblMaterial.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblMaterial.setText("Materiel:");

        btnEdit.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnEdit.setText("Rediger");
        btnEdit.setPreferredSize(new java.awt.Dimension(81, 38));

        btnNew.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnNew.setText("Ny..");
        btnNew.setPreferredSize(new java.awt.Dimension(81, 38));

        javax.swing.GroupLayout pnlMaterialLayout = new javax.swing.GroupLayout(pnlMaterial);
        pnlMaterial.setLayout(pnlMaterialLayout);
        pnlMaterialLayout.setHorizontalGroup(
            pnlMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMaterialLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMaterialLayout.createSequentialGroup()
                        .addComponent(lblMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlMaterialLayout.createSequentialGroup()
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlMaterialLayout.setVerticalGroup(
            pnlMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMaterialLayout.createSequentialGroup()
                .addGroup(pnlMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlMaterialLayout.createSequentialGroup()
                        .addGroup(pnlMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlMaterialLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblMaterial))
                            .addComponent(txtMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(428, 428, 428)
                        .addGroup(pnlMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    private javax.swing.JLabel lblMaterial;
    private javax.swing.JList lstMaterial;
    private javax.swing.JPanel pnlMaterial;
    private javax.swing.JTextField txtMaterial;
    // End of variables declaration//GEN-END:variables
}
