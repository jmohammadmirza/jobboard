package com.example.jobboard;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "audit")
public class Audit {

    @Id
    private String id;
    private String query;
    private Date timestamp;
    private String ipAddress;
    private String location;
    private String userAgent;
    private String auditTrackingId;
    private int responseCode;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    private String responseSentToUser;

    public String getResponseSentToUser() {
        return responseSentToUser;
    }

    public void setResponseSentToUser(String responseSentToUser) {
        this.responseSentToUser = responseSentToUser;
    }



    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

   

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getAuditTrackingId() {
        return auditTrackingId;
    }

    public void setAuditTrackingId(String auditTrackingId) {
        this.auditTrackingId = auditTrackingId;
    }
}
