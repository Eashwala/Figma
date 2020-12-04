package com.ipl.user.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Game {

    @SerializedName("coverPhoto")
    @Expose
    private String coverPhoto;
    @SerializedName("entry_type")
    @Expose
    private String entryType;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("autoEnd")
    @Expose
    private Boolean autoEnd;
    @SerializedName("endTime")
    @Expose
    private String endTime;
    @SerializedName("autoStart")
    @Expose
    private Boolean autoStart;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("qPushed")
    @Expose
    private Integer qPushed;
    @SerializedName("points")
    @Expose
    private Integer points;
    @SerializedName("questionsAnswered")
    @Expose
    private Integer questionsAnswered;

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getQuestionsAnswered() {
        return questionsAnswered;
    }

    public void setQuestionsAnswered(Integer questionsAnswered) {
        this.questionsAnswered = questionsAnswered;
    }


    public Integer getQPushed() {
        return qPushed;
    }

    public void setQPushed(Integer qPushed) {
        this.qPushed = qPushed;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Boolean getAutoEnd() {
        return autoEnd;
    }

    public void setAutoEnd(Boolean autoEnd) {
        this.autoEnd = autoEnd;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public Boolean getAutoStart() {
        return autoStart;
    }

    public void setAutoStart(Boolean autoStart) {
        this.autoStart = autoStart;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


}
