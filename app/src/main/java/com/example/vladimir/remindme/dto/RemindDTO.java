package com.example.vladimir.remindme.dto;

/**
 * Created by vladimir on 21.02.2017.
 */

public class RemindDTO {

    private String title;

    public RemindDTO(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
