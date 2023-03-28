package com.triplify.app.expense.algorithms;

import com.triplify.app.expense.model.AddExpenses;
import com.triplify.app.expense.model.Expenses;

import java.util.Map;

public interface IAddNewExpenses {
    Map<String, Object> splitExpenses(AddExpenses expenses);
    Map<String, Object> setExpenses(AddExpenses expenses, float splittedAmount, String useridlist);
    Map<String, Object> settleMyExpenses(Expenses expenses);
}
