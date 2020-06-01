package com.example.qiqi.xianwan.zhishidabipin.bean;

import java.util.List;

public class QuestionBean extends BaseBean{

    private String questionId;// 题目ID
    private String description;// 题目描述
    private int questionType;// 题目类型
    private String knowledgePointName; // 知识点名
    private String knowledgePointId; // 知识点id
    private List<QuestionOptionBean> questionOptions; // 选项集合

    public QuestionBean() {
        super();
    }

    public QuestionBean(String questionId, String description,
                        int questionType, String knowledgePointName,
                        String knowledgePointId, List<QuestionOptionBean> questionOptions) {
        super();
        this.questionId = questionId;
        this.description = description;
        this.questionType = questionType;
        this.knowledgePointName = knowledgePointName;
        this.knowledgePointId = knowledgePointId;
        this.questionOptions = questionOptions;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public String getKnowledgePointName() {
        return knowledgePointName;
    }

    public void setKnowledgePointName(String knowledgePointName) {
        this.knowledgePointName = knowledgePointName;
    }

    public String getKnowledgePointId() {
        return knowledgePointId;
    }

    public void setKnowledgePointId(String knowledgePointId) {
        this.knowledgePointId = knowledgePointId;
    }

    public List<QuestionOptionBean> getQuestionOptions() {
        return questionOptions;
    }

    public void setQuestionOptions(List<QuestionOptionBean> questionOptions) {
        this.questionOptions = questionOptions;
    }

    @Override
    public String toString() {
        return "QuestionBean [questionId=" + questionId + ", description="
                + description + ", questionType=" + questionType
                + ", knowledgePointName=" + knowledgePointName
                + ", knowledgePointId=" + knowledgePointId
                + ", questionOptions=" + questionOptions + "]";
    }
}
