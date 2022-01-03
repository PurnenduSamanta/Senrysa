package com.purnendu.senrysa.Model;


public class JobSeekerDetails {

    private int id ;
    private String name;
    private String email;
    private byte[] cv;


    public JobSeekerDetails(int id, String name, String email, byte[] cv) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cv = cv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getCv() {
        return cv;
    }

    public void setCv(byte[] cv) {
        this.cv = cv;
    }
}
