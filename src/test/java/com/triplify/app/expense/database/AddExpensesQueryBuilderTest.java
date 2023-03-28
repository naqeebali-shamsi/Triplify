package com.triplify.app.expense.database;

import com.triplify.app.database.DatabaseConnection;
import com.triplify.app.database.DatabaseExceptionHandler;
import com.triplify.app.expense.model.Expenses;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

@DisplayName("Expenses addition Table Insert Query Builder Testing!!")
public class AddExpensesQueryBuilderTest {
    @Test
    @DisplayName("Insert Query By Expenses Object!!")
    public void expenseTableInsertQueryTest() {
        final long id = 45;
        final String transaction_id = "hbjgyjjh76567g";
        final String description = "fish & chips";
        final float amount = 56;
        final String currency = "CAD";
        final String fromusername = "14";
        final String tousername = "14";
        final Long groupId = Long.valueOf(14);
        final float full_amount = 56;
        final String date_added = "2022-02-01";

        Expenses expenses = new Expenses();

        expenses.setId(id);
        expenses.setTransaction_id(transaction_id);
        expenses.setDescription(description);
        expenses.setAmount(amount);
        expenses.setCurrency(currency);
        expenses.setFromUsername(fromusername);
        expenses.setToUsername(tousername);
        expenses.setGroupid(groupId);
        expenses.setFull_amount(full_amount);
        expenses.setDate_added(date_added);

        AddExpensesQueryBuilder addExpensesQueryBuilder =
                AddExpensesQueryBuilder.getInstance();
        try {
            Connection connection =
                    DatabaseConnection.getInstance().getDatabaseConnection();
            final String expectedInsertResult = String.valueOf(addExpensesQueryBuilder.insertExpenseQuery(expenses, connection));
            final String actualInsertResult = "1";

            System.out.println(expectedInsertResult);
            System.out.println(actualInsertResult);
            Assertions.assertEquals(expectedInsertResult,actualInsertResult,"Incorrect Insert Query Has been generated!!");
        } catch(DatabaseExceptionHandler e) {
            throw new RuntimeException(e);
        }
    }
}
