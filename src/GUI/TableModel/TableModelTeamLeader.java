package GUI.TableModel;

import BE.BEIncidentDetails;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class TableModelTeamLeader extends AbstractTableModel {

    private ArrayList<BEIncidentDetails> teamLeaderDetails;

    private final String[] colNames = {"Indsats Leder",
        "EVA nr", "Rapport nr", "Skadeslidte", "Adresse", "Beretning", "Detektor nr", "Gruppe nr"
    };

    private final Class[] classes = {String.class,
        String.class, String.class, String.class, String.class, String.class, String.class, String.class
    };

    public TableModelTeamLeader(ArrayList<BEIncidentDetails> allDetails) {
        teamLeaderDetails = allDetails;
        fireTableDataChanged();
    }

    /**
     *
     * @return
     */
    @Override
    public int getRowCount() {
        return teamLeaderDetails.size();
    }

    /**
     *
     * @return
     */
    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    /**
     *
     * @param row
     * @param col
     * @return
     */
    @Override
    public Object getValueAt(int row, int col) {
        BEIncidentDetails e = teamLeaderDetails.get(row);
        switch (col) {
            case 0:
                return e.getM_incidentLeader();
            case 1:
                return e.getM_evaNumber();
            case 2:
                return e.getM_fireReport();
            case 3:
                return e.getM_involvedName();
            case 4:
                return e.getM_involvedAddress();
            case 5:
                return e.getM_alarm().getM_description();
            case 6:
                return e.getM_detectorNumber();
            case 7:
                return e.getM_groupNumber();
        }
        return null;
    }

    /**
     *
     * @param col
     * @return
     */
    @Override
    public String getColumnName(int col) {
        return colNames[col];
    }

    /**
     *
     * @param col
     * @return
     */
    @Override
    public Class<?> getColumnClass(int col) {
        return classes[col];
    }

    /**
     *
     * @param row
     * @param col
     * @return
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    /**
     *
     * @param detailList
     */
    public void setTeamLeaderDetailsList(ArrayList<BEIncidentDetails> detailList) {
        teamLeaderDetails = detailList;
        fireTableDataChanged();
    }

    /**
     *
     * @param row
     * @return
     */
    public BEIncidentDetails getTeamLeaderDetailsByRow(int row) {
        return teamLeaderDetails.get(row);
    }
}
