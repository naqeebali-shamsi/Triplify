package com.triplify.app.expense.algorithms;

import com.triplify.app.database.DatabaseConnection;
import com.triplify.app.database.DatabaseExceptionHandler;
import com.triplify.app.expense.model.Expenses;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.triplify.app.expense.database.ExpenseDatabaseContstant.*;

public class SendUserExpense implements ISendUserExpense {

    @Override
    public List<Expenses> fetchMyExpenses(String username, long groupid) {
        List<Expenses> listOfuserExpenses = new ArrayList<>();

        try {
            Connection connection =
                    DatabaseConnection.getInstance().getDatabaseConnection();

            System.out.println(connection.getCatalog());

            ResultSet userDetailsResultSet =
                    connection.createStatement().executeQuery("select * from User_expenses where id_group_details " +
                            "= " + groupid + " ;");


            while (userDetailsResultSet.next()) {
                String from_user_id = userDetailsResultSet.getString("" + expenses_table_from_username);
                String to_user_id = userDetailsResultSet.getString("" + expenses_table_to_username);
                if((from_user_id.equals(username) || to_user_id.equals(username)) && !from_user_id.equals(to_user_id)) {

                    Long id = userDetailsResultSet.getLong("" + expenses_table_id);
                    String transaction_id = userDetailsResultSet.getString("" + expenses_table_transaction_id);
                    String description = userDetailsResultSet.getString("" + expenses_table_description);
                    Float amount = userDetailsResultSet.getFloat("" + expenses_table_amount);
                    String currency = userDetailsResultSet.getString("" + expenses_table_currency);
                    Long id_group_details = userDetailsResultSet.getLong("" + expenses_table_id_group_details);
                    Float total_amount = userDetailsResultSet.getFloat("" + expenses_table_full_amount);
                    String date_added = userDetailsResultSet.getString("" + expenses_table_date_added);

                    Expenses expense = new Expenses();
                    expense.setId(id);
                    expense.setTransaction_id(transaction_id);
                    expense.setDescription(description);
                    expense.setAmount(amount);
                    expense.setCurrency(currency);
                    expense.setGroupid(id_group_details);
                    expense.setFromUsername(from_user_id);
                    expense.setToUsername(to_user_id);
                    expense.setFull_amount(total_amount);
                    expense.setDate_added(date_added);
                    listOfuserExpenses.add(expense);
                }
            }

            if (connection != null) {
                connection.close();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DatabaseExceptionHandler e) {
            throw new RuntimeException(e);
        }
        return listOfuserExpenses;
    }
    @Override
    public float calculateTotalExpense(String username) {
        long total = 0;
        try {
            Connection connection =
                    DatabaseConnection.getInstance().getDatabaseConnection();
            ResultSet userDetailsResultSet =
                    connection.createStatement().executeQuery("select * from User_expenses");
            while (userDetailsResultSet.next()) {
                String from_user_id = userDetailsResultSet.getString("" + expenses_table_from_username);
                String to_user_id = userDetailsResultSet.getString("" + expenses_table_to_username);
                Float amount = userDetailsResultSet.getFloat("" + expenses_table_amount);
                if(from_user_id.equals(username) && (to_user_id.equals(username)) && from_user_id.equals(to_user_id)){
                    total = (long) (total + amount);
                }
                if(to_user_id.equals(username) && !from_user_id.equals(to_user_id)) {
                    total = (long) (total + amount);
                }
            }

            if (connection != null) {
                connection.close();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DatabaseExceptionHandler e) {
            throw new RuntimeException(e);
        }
        return total;
    }
}
