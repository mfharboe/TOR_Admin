package BE;

import java.sql.Date;
import java.sql.Time;

public class BEIncident {

    private int m_id;
    private String m_incidentName;
    private Date m_date;
    private Time m_time;
    private BEIncidentType m_incidentType;
    private boolean m_isDone;

    /**
     * Read Incident
     *
     * @param id
     * @param incidentName
     * @param date
     * @param time
     * @param incidentType
     * @param isDone
     */
    public BEIncident(int id, String incidentName, Date date, Time time, BEIncidentType incidentType, boolean isDone) {
        m_id = id;
        m_incidentName = incidentName;
        m_date = date;
        m_time = time;
        m_incidentType = incidentType;
        m_isDone = isDone;
    }

    /**
     * @return the m_id
     */
    public int getM_id() {
        return m_id;
    }

    /**
     * @param m_id the m_id to set
     */
    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    /**
     * @return the m_incidentName
     */
    public String getM_incidentName() {
        return m_incidentName;
    }

    /**
     * @param m_incidentName the m_incidentName to set
     */
    public void setM_incidentName(String m_incidentName) {
        this.m_incidentName = m_incidentName;
    }

    /**
     * @return the m_date
     */
    public Date getM_date() {
        return m_date;
    }

    /**
     * @param m_date the m_date to set
     */
    public void setM_date(Date m_date) {
        this.m_date = m_date;
    }

    /**
     * @return the m_time
     */
    public Time getM_time() {
        return m_time;
    }

    /**
     * @param m_time the m_time to set
     */
    public void setM_time(Time m_time) {
        this.m_time = m_time;
    }

    /**
     * @return the m_incidentType
     */
    public BEIncidentType getM_incidentType() {
        return m_incidentType;
    }

    /**
     * @param m_incidentType the m_incidentType to set
     */
    public void setM_incidentType(BEIncidentType m_incidentType) {
        this.m_incidentType = m_incidentType;
    }

    /**
     * @return the m_isDone
     */
    public boolean isM_isDone() {
        return m_isDone;
    }

    /**
     * @param m_isDone the m_isDone to set
     */
    public void setM_isDone(boolean m_isDone) {
        this.m_isDone = m_isDone;
    }

    @Override
    public String toString() {
        return m_time + " - " + m_incidentName;
    }
}
