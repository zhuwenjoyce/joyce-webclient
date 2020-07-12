package com.joyce.webclient.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserModel {

    private Integer userId;
    private String username;
    private MoneyModel moneyModel;
    private List<MoneyModel> moneyModelList = new ArrayList<>();
    private String createTimeStr;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MoneyModel getMoneyModel() {
        return moneyModel;
    }

    public void setMoneyModel(MoneyModel moneyModel) {
        this.moneyModel = moneyModel;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public List<MoneyModel> getMoneyModelList() {
        return moneyModelList;
    }

    public void setMoneyModelList(List<MoneyModel> moneyModelList) {
        this.moneyModelList = moneyModelList;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", moneyModel=" + moneyModel +
                ", moneyModelList=" + moneyModelList +
                ", createTimeStr='" + createTimeStr + '\'' +
                '}';
    }
}
