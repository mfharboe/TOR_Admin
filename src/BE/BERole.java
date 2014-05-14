
package BE;

public class BERole {
    
    private int m_id;
    private String m_roleDescription;
    
    /**
     * Read Role
     *
     * @param id
     * @param roleDescription
     */
    public BERole(int id, String roleDescription) {

        m_id = id;
        m_roleDescription = roleDescription;
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
     * @return the m_roleDescription
     */
    public String getM_roleDescription() {
        return m_roleDescription;
    }

    /**
     * @param m_roleDescription the m_roleDescription to set
     */
    public void setM_roleDescription(String m_roleDescription) {
        this.m_roleDescription = m_roleDescription;
    }
    
    
}
