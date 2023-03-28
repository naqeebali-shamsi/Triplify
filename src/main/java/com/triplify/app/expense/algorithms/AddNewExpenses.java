package com.triplify.app.expense.algorithms;

import com.triplify.app.database.DatabaseConnection;
import com.triplify.app.database.DatabaseExceptionHandler;
import com.triplify.app.expense.database.AddExpensesQueryBuilder;
import com.triplify.app.expense.model.AddExpenses;
import com.triplify.app.expense.model.Expenses;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddNewExpenses implements IAddNewExpenses {
    private static final List<Expenses> addUserExpense = new ArrayList<>();
    @Override
    public Map<String,Object> splitExpenses(AddExpenses expenses) {
        Map<String,Object> response = new HashMap<>();
        ArrayList<String> usernamelist = expenses.getUsernamelist();
        int size = usernamelist.size();
        float split = expenses.getAmount()/size;
        int id = -1;
        for (int i = 0; i < size; i++) {
            if (usernamelist.get(i).equals(expenses.getPaidbyusername()))
            {
                response = setExpenses(expenses, expenses.getAmount() - split, usernamelist.get(i));
                id = i;
            } else {
                response = setExpenses(expenses, split * -1, usernamelist.get(i));
            }
        }
        if (id < 0) {
            response = setExpenses(expenses, expenses.getAmount(), "");
        }
        return response;
    }
    @Override
    public Map<String, Object> setExpenses(AddExpenses expenses, float splittedAmount, String useridlist)
    {
        Map<String,Object> response = new HashMap<>();
        try{

            Connection connection =
                    DatabaseConnection.getInstance().getDatabaseConnection();
            Expenses expense = new Expenses();
            expense.setId(expenses.getId());
            expense.setTransaction_id(expenses.getTransaction_id());
            expense.setDescription(expenses.getDescription());
            expense.setAmount(splittedAmount);
            expense.setCurrency(expenses.getCurrency());
            expense.setGroupid(expenses.getGroupid());
            expense.setFromUsername(expenses.getPaidbyusername());
            expense.setToUsername(useridlist);
            expense.setToUsername(useridlist);
            expense.setFull_amount(expenses.getFull_amount());
            expense.setDate_added(expenses.getDate_added());
            addUserExpense.add(expense);
            AddExpensesQueryBuilder addExpensesQueryBuilder = new AddExpensesQueryBuilder();
            final int rowInserted =
                    addExpensesQueryBuilder.insertExpenseQuery(expense, connection);

            if(rowInserted > 0){
                System.out.println("Yes row is inserted !!");
                response.put("SUCCESS",true);
                response.put("MESSAGE","Expenses are added successfully");
            }
            if(connection!=null){
                connection.close();
            }
        } catch (SQLException e) {
            response.put("SUCCESS",false);
            response.put("MESSAGE","Expenses are not added!!");
            throw new RuntimeException(e);
        } catch (DatabaseExceptionHandler e) {
            response.put("SUCCESS",false);
            response.put("MESSAGE","Expenses are not added!!");
            throw new RuntimeException(e);
        }
        return response;
    }
    @Override
    public Map<String, Object> settleMyExpenses(Expenses expenses){
        Map<String,Object> response = new HashMap<>();
        try {
        Connection connection =
                DatabaseConnection.getInstance().getDatabaseConnection();
            expenses.setAmount(expenses.getAmount()*-1);
            AddExpensesQueryBuilder addExpensesQueryBuilder = new AddExpensesQueryBuilder();
            final int rowInserted =
                    addExpensesQueryBuilder.insertExpenseQuery(expenses, connection);

            if(rowInserted > 0){
                System.out.println("Yes row is inserted !!");
                response.put("SUCCESS",true);
                response.put("MESSAGE","Expenses are added successfully");
            }
            if(connection!=null){
                connection.close();
            }
        } catch (SQLException e) {
            response.put("SUCCESS",false);
            response.put("MESSAGE","Expenses are not added!!");
            throw new RuntimeException(e);
        } catch (DatabaseExceptionHandler e) {
            response.put("SUCCESS",false);
            response.put("MESSAGE","Expenses are not added!!");
            throw new RuntimeException(e);
        }
        return response;
    }

}
