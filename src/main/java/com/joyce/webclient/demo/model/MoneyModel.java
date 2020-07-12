package com.joyce.webclient.demo.model;

import java.math.BigDecimal;

public class MoneyModel {

    private Integer id;
    private Integer userId;
    private BigDecimal money;
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "MoneyModel{" +
                "id=" + id +
                ", userId=" + userId +
                ", money=" + money +
                ", remark='" + remark + '\'' +
                '}';
    }
}
