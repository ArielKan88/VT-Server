package com.processver.model;
import java.util.*;
import com.google.gson.annotations.SerializedName;


public class ProcessStatus {

    @SerializedName("scans")
    private HashMap<String, String> scans;
    @SerializedName("scan_id")
    private String scanId;
    @SerializedName("resource")
    private String resource;
    @SerializedName("response_code")
    private Integer responseCode;
    @SerializedName("scan_date")
    private String scanDate;
    @SerializedName("permalink")
    private String permalink;
    @SerializedName("verbose_msg")
    private String verboseMessage;
    @SerializedName("total")
    private Integer total;
    @SerializedName("positives")
    private Integer positives;
    @SerializedName("sha256")
    private String sha256;

    public HashMap<String, String> getScans() {
        return scans;
    }

    public String getScanId() {
        return scanId;
    }

    public String getResource() {
        return resource;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getScanDate() {
        return scanDate;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getVerboseMessage() {
        return verboseMessage;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getPositives() {
        return positives;
    }

    public String getSha256() {
        return sha256;
    }

    public void setScans(HashMap<String, String> scans) {
        this.scans = scans;
    }

    public void setScanId(String scanId) {
        this.scanId = scanId;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public void setScanDate(String scanDate) {
        this.scanDate = scanDate;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public void setVerboseMessage(String verboseMessage) {
        this.verboseMessage = verboseMessage;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setPositives(Integer positives) {
        this.positives = positives;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }



}
