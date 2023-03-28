package com.triplify.app.expense.algorithms;

import com.triplify.app.expense.model.Expenses;

import java.util.List;

public interface ISendUserExpense {
    List<Expenses> fetchMyExpenses(String userid, long groupid);
    float calculateTotalExpense(String userid);
}
