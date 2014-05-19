package BE;

public class BEZipcode {

    private int m_zipCode;
    private String m_city;

    /**
     * Read ZipCode
     *
     * @param zipCode
     * @param city
     */
    public BEZipcode(int zipCode, String city) {

        m_zipCode = zipCode;
        m_city = city;
    }

    /**
     * @return the m_zipCode
     */
    public int getM_zipCode() {
        return m_zipCode;
    }

    /**
     * @param m_zipCode the m_zipCode to set
     */
    public void setM_zipCode(int m_zipCode) {
        this.m_zipCode = m_zipCode;
    }

    /**
     * @return the m_city
     */
    public String getM_city() {
        return m_city;
    }

    /**
     * @param m_city the m_city to set
     */
    public void setM_city(String m_city) {
        this.m_city = m_city;
    }
    
    @Override
    public String toString(){
        return String.valueOf(m_zipCode) + " - " + m_city;
    }

}
