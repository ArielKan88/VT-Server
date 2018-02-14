package com.processver.model;

public class ProcessInfo {

    private String pid;
    private String pidSHA256;

    public ProcessInfo()
    {
        pid = null;
        pidSHA256 = null;
    }

    public ProcessInfo(String id,String sha256)
    {
        this.pid = id;
        this.pidSHA256 = sha256;
    }

    public String getPid() {
        return pid;
    }

    public String getPidSHA256() {
        return pidSHA256;
    }

    public void setPid(String pid)
    {
        this.pid = pid;
    }

    public void setPidSHA256(String sha256)
    {
        this.pidSHA256 = sha256;
    }
}
