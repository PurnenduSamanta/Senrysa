package com.purnendu.senrysa.Model;

public class JobModel {


    private String email;
    private String title;
    private String jd;
    private String ss1;
    private String ss2;
    private String department;
    private int experience;

    public JobModel(String email,String title, String jd, String ss1, String ss2, String department, int experience) {
        this.email = email;
        this.title=title;
        this.jd = jd;
        this.ss1 = ss1;
        this.ss2 = ss2;
        this.department = department;
        this.experience = experience;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJd() {
        return jd;
    }

    public void setJd(String jd) {
        this.jd = jd;
    }

    public String getSs1() {
        return ss1;
    }

    public void setSs1(String ss1) {
        this.ss1 = ss1;
    }

    public String getSs2() {
        return ss2;
    }

    public void setSs2(String ss2) {
        this.ss2 = ss2;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
