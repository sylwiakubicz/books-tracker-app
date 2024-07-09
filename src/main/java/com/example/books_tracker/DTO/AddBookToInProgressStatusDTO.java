package com.example.books_tracker.DTO;

public class AddBookToInProgressStatusDTO {
    private String startDate;
    private Integer currentPage;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public String getStartDate() {
        return startDate;
    }
}
