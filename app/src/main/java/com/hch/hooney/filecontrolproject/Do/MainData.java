package com.hch.hooney.filecontrolproject.Do;

public class MainData {
    private String m_name;
    private String m_postNumber;
    private String m_address1;
    private String m_address2;

    public MainData() {
        this.m_name = null;
        this.m_postNumber = null;
        this.m_address1 = null;
        this.m_address2 = null;
    }

    public MainData(String m_name, String m_postNumber, String m_address1, String m_address2) {
        this.m_name = m_name;
        this.m_postNumber = m_postNumber;
        this.m_address1 = m_address1;
        this.m_address2 = m_address2;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getM_postNumber() {
        return m_postNumber;
    }

    public void setM_postNumber(String m_postNumber) {
        this.m_postNumber = m_postNumber;
    }

    public String getM_address1() {
        return m_address1;
    }

    public void setM_address1(String m_address1) {
        this.m_address1 = m_address1;
    }

    public String getM_address2() {
        return m_address2;
    }

    public void setM_address2(String m_address2) {
        this.m_address2 = m_address2;
    }

    @Override
    public String toString() {
        return this.m_name+","+this.m_postNumber+","+this.m_address1+","+this.m_address2;
    }
}
