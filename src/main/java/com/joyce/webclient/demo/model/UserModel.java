package com.joyce.webclient.demo.model;

import java.math.BigDecimal;

public class UserModel {

    private Integer userId;
    private String username;
    private MoneyModel moneyModel;

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
}
