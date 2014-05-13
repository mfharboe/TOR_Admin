package BE;

public class BEUsage {

    private int m_id;
    private BEMaterial m_material;
    private int m_amount;
    private BEIncident m_incident;

    /**
     * Read and Create Usage
     *
     * @param id
     * @param material
     * @param amount
     * @param incident
     */
    public BEUsage(int id, BEMaterial material, int amount, BEIncident incident) {

        m_id = id;
        m_material = material;
        m_amount = amount;
        m_incident = incident;
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
     * @return the m_material
     */
    public BEMaterial getM_material() {
        return m_material;
    }

    /**
     * @param m_material the m_material to set
     */
    public void setM_material(BEMaterial m_material) {
        this.m_material = m_material;
    }

    /**
     * @return the m_amount
     */
    public int getM_amount() {
        return m_amount;
    }

    /**
     * @param m_amount the m_amount to set
     */
    public void setM_amount(int m_amount) {
        this.m_amount = m_amount;
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

}
