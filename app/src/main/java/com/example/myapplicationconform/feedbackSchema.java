package com.example.myapplicationconform;

import java.util.HashMap;
import java.util.Map;

public class feedbackSchema {

    private Integer Uid;
    private Integer Pid;
    private String feedback;
    private Integer reply;
    private Integer RFid;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getUid() {
        return Uid;
    }

    public void setUid(Integer uid) {
        this.Uid = uid;
    }

    public Integer getPid() {
        return Pid;
    }

    public void setPid(Integer pid) {
        this.Pid = pid;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Integer getReply() {
        return reply;
    }

    public void setReply(Integer reply) {
        this.reply = reply;
    }

    public Integer getRFid() {
        return RFid;
    }

    public void setRFid(Integer rFid) {
        this.RFid = rFid;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


}
