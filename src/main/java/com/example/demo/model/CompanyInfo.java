package com.example.demo.model;

import java.io.Serializable;

public class CompanyInfo implements Serializable {
    private String company;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
