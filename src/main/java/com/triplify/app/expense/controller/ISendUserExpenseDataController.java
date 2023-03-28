package com.triplify.app.expense.controller;

import com.triplify.app.expense.model.Expenses;

import java.util.List;

public interface ISendUserExpenseDataController {
    List<Expenses> getAllExpenseDetails(String username, long groupid);
    float calculateUserTotalExpense(String username);
}
