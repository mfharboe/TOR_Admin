package BE;

public class BEAlarm {

    private int m_id;
    private String m_description;

    /**
     * Read Alarms
     *
     * @param id
     * @param description
     */
    public BEAlarm(int id, String description) {
        m_id = id;
        m_description = description;
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
     * @return the m_description
     */
    public String getM_description() {
        return m_description;
    }

    /**
     * @param m_description the m_description to set
     */
    public void setM_description(String m_description) {
        this.m_description = m_description;
    }

}
