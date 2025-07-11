package com.example.jobboard;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "jobs")
public class Job {

    @Id
    private String id;
    private String title;
    private String companyName;
    private String location;
    private String description;
    private String applyLink;
    private String userInput;
    private LocalDateTime insertedDate;

    public LocalDateTime getInsertedDate() {
        return insertedDate;
    }

    public void setInsertedDate(LocalDateTime insertedDate) {
        this.insertedDate = insertedDate;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApplyLink() {
        return applyLink;
    }

    public void setApplyLink(String applyLink) {
        this.applyLink = applyLink;
    }
}
