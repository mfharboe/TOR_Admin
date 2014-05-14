package BE;

public class BEIncidentDetails {

    private String m_incidentLeader;
    private String m_evaNumber;
    private String m_fireReport;
    private BEIncident m_incident;
    private String m_involvedName;
    private String m_involvedAddress;
    private String m_remark;
    private BEAlarm m_alarm;
    private String m_detectorNumber;
    private String m_groupNumber;

    /**
     * Read and Create IncidentDetails
     *
     * @param incidentLeader
     * @param evaNumber
     * @param fireReport
     * @param message
     * @param incident
     * @param involvedName
     * @param involvedAddress
     * @param remark
     * @param alarm
     * @param detectorNumber
     * @param groupNumber
     */
    public BEIncidentDetails(String incidentLeader, String evaNumber, String fireReport,
            BEIncident incident, String involvedName, String involvedAddress, String remark, BEAlarm alarm,
            String detectorNumber, String groupNumber) {

        m_incidentLeader = incidentLeader;
        m_evaNumber = evaNumber;
        m_fireReport = fireReport;
        m_incident = incident;
        m_involvedName = involvedName;
        m_involvedAddress = involvedAddress;
        m_remark = remark;
        m_alarm = alarm;
        m_detectorNumber = detectorNumber;
        m_groupNumber = groupNumber;

    }

    /**
     * @return the m_incidentLeader
     */
    public String getM_incidentLeader() {
        return m_incidentLeader;
    }

    /**
     * @param m_incidentLeader the m_incidentLeader to set
     */
    public void setM_incidentLeader(String m_incidentLeader) {
        this.m_incidentLeader = m_incidentLeader;
    }

    /**
     * @return the m_evaNumber
     */
    public String getM_evaNumber() {
        return m_evaNumber;
    }

    /**
     * @param m_evaNumber the m_evaNumber to set
     */
    public void setM_evaNumber(String m_evaNumber) {
        this.m_evaNumber = m_evaNumber;
    }

    /**
     * @return the m_fireReport
     */
    public String getM_fireReport() {
        return m_fireReport;
    }

    /**
     * @param m_fireReport the m_fireReport to set
     */
    public void setM_fireReport(String m_fireReport) {
        this.m_fireReport = m_fireReport;
    }

    /**
     * @return the m_incident
     */
    public BEIncident getM_incident() {
        return m_incident;
    }

    /**
     * @param m_incident the m_incident to set
     */
    public void setM_incident(BEIncident m_incident) {
        this.m_incident = m_incident;
    }

    /**
     * @return the m_involvedName
     */
    public String getM_involvedName() {
        return m_involvedName;
    }

    /**
     * @param m_involvedName the m_involvedName to set
     */
    public void setM_involvedName(String m_involvedName) {
        this.m_involvedName = m_involvedName;
    }

    /**
     * @return the m_involvedAddress
     */
    public String getM_involvedAddress() {
        return m_involvedAddress;
    }

    /**
     * @param m_involvedAddress the m_involvedAddress to set
     */
    public void setM_involvedAddress(String m_involvedAddress) {
        this.m_involvedAddress = m_involvedAddress;
    }

    /**
     * @return the m_remark
     */
    public String getM_remark() {
        return m_remark;
    }

    /**
     * @param m_remark the m_remark to set
     */
    public void setM_remark(String m_remark) {
        this.m_remark = m_remark;
    }

    /**
     * @return the m_alarm
     */
    public BEAlarm getM_alarm() {
        return m_alarm;
    }

    /**
     * @param m_alarm the m_alarm to set
     */
    public void setM_alarm(BEAlarm m_alarm) {
        this.m_alarm = m_alarm;
    }

    /**
     * @return the m_detectorNumber
     */
    public String getM_detectorNumber() {
        return m_detectorNumber;
    }

    /**
     * @param m_detectorNumber the m_detectorNumber to set
     */
    public void setM_detectorNumber(String m_detectorNumber) {
        this.m_detectorNumber = m_detectorNumber;
    }

    /**
     * @return the m_groupNumber
     */
    public String getM_groupNumber() {
        return m_groupNumber;
    }

    /**
     * @param m_groupNumber the m_groupNumber to set
     */
    public void setM_groupNumber(String m_groupNumber) {
        this.m_groupNumber = m_groupNumber;
    }

}
