package com.skptech.demo.model;

public class UploadedFile {

    private int no;
    private String name;
    private String uri;

    public UploadedFile(int no, String name, String uri){
        this.no = no;
        this.name = name;
        this.uri = uri;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }


    public void setUri(String uri) {
        this.uri = uri;
    }

}
