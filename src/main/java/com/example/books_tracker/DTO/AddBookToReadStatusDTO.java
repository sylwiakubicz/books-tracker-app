package com.example.books_tracker.DTO;

public class AddBookToReadStatusDTO {

    private String startDate;
    private String endDate;
    private Integer rate;

    public String getEndDate() {
        return endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public Integer getRate() {
        return rate;
    }
}
