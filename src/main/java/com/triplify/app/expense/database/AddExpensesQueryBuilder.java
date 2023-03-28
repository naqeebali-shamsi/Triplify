package com.triplify.app.expense.database;

import com.triplify.app.expense.model.Expenses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.triplify.app.expense.database.ExpenseDatabaseContstant.*;
public class AddExpensesQueryBuilder implements IAddExpensesQueryBuilder{
    private static AddExpensesQueryBuilder instance;

    public AddExpensesQueryBuilder(){

    }

    public static AddExpensesQueryBuilder getInstance(){
        if (instance == null){
            instance = new AddExpensesQueryBuilder();
        }
        return instance;
    }
    @Override
    public int insertExpenseQuery(final Expenses expenses, Connection connection){
        String query = "INSERT INTO "+ expenses_table + "(" +
                expenses_table_transaction_id + ", " +
                expenses_table_description + ", " +
                expenses_table_amount + ", " +
                expenses_table_currency + ", " +
                expenses_table_from_username + ", " +
                expenses_table_to_username + ", " +
                expenses_table_full_amount + ", " +
                expenses_table_date_added + ", " +
                expenses_table_id_group_details + ") " +
                "VALUES (?,?,?,?,?,?,?,?,?);";
        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, expenses.getTransaction_id());
            pstmt.setString(2, expenses.getDescription());
            pstmt.setFloat(3, expenses.getAmount());
            pstmt.setString(4, expenses.getCurrency());
            pstmt.setString(5, expenses.getFromUsername());
            pstmt.setString(6, expenses.getToUsername());
            pstmt.setFloat(7, expenses.getFull_amount());
            pstmt.setString(8, expenses.getDate_added());
            pstmt.setLong(9, expenses.getGroupid());
            return pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
