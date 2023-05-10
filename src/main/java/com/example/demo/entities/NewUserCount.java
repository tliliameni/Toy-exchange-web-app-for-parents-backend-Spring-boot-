package com.example.demo.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NewUserCount {

    private LocalDate date;
    private long count;

    public NewUserCount(LocalDate date2, long count) {
        this.date = date2;
        this.count = count;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
