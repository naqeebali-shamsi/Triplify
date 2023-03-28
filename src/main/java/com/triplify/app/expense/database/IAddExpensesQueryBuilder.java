package com.triplify.app.expense.database;

import com.triplify.app.expense.model.Expenses;

import java.sql.Connection;

public interface IAddExpensesQueryBuilder {
    int insertExpenseQuery(final Expenses expenses, Connection connection);
}
