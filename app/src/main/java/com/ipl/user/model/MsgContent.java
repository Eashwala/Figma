package com.ipl.user.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MsgContent {

    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("pQuestionId")
    @Expose
    private String pQuestionId;
    @SerializedName("questionId")
    @Expose
    private String questionId;
    @SerializedName("qStatus")
    @Expose
    private String qStatus;
    @SerializedName("entry_type")
    @Expose
    private String entryType;
    @SerializedName("options")
    @Expose
    private List<String> options = null;
    @SerializedName("time_out")
    @Expose
    private String timeOut;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("qType")
    @Expose
    private String qType;
    @SerializedName("content")
    @Expose
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getPQuestionId() {
        return pQuestionId;
    }

    public void setPQuestionId(String pQuestionId) {
        this.pQuestionId = pQuestionId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQStatus() {
        return qStatus;
    }

    public void setQStatus(String qStatus) {
        this.qStatus = qStatus;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQType() {
        return qType;
    }

    public void setQType(String qType) {
        this.qType = qType;
    }
}