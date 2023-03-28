package com.triplify.app.expense.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddExpenses {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id")
    private String transaction_id;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private float amount;

    @Column(name = "currency")
    private String currency;
    @Column(name = "usernamelist")
    private ArrayList<String> usernamelist;

    @Column(name = "paidbyusername")
    private String paidbyusername;
    @Column(name = "groupid")
    private Long groupid;
    @Column(name = "full_amount")
    private float full_amount;

    @Column(name = "date_added")
    private String date_added;

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public ArrayList<String> getUsernamelist() {
        return usernamelist;
    }

    public void setUsernamelist(ArrayList<String> usernamelist) {
        this.usernamelist = usernamelist;
    }

    public String getPaidbyusername() {
        return paidbyusername;
    }

    public void setPaidbyusername(String paidbyusername) {
        this.paidbyusername = paidbyusername;
    }

    public float getFull_amount() {
        return full_amount;
    }

    public void setFull_amount(float full_amount) {
        this.full_amount = full_amount;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }
}