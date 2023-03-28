package com.triplify.app.expense.controller;

import com.triplify.app.expense.model.Expenses;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("Send User Testing!!")
public class SendUserExpenseDataControllerTest {
    @Test
    @DisplayName("post expense testing!!")
    public void getAllExpenseDetailsTest() {
        final String username = "karan";
        final long groupid = 4;

        SendUserExpenseDataController sendUserExpenseDataController = new SendUserExpenseDataController();
        List<Expenses> expense = sendUserExpenseDataController.getAllExpenseDetails(username, groupid);

        int length = expense.size();

        Assertions.assertTrue(length > 0);
    }

    public void calculateUserTotalExpenseTest(){
        final String username = "karan";

        SendUserExpenseDataController sendUserExpenseDataController = new SendUserExpenseDataController();
        float value = sendUserExpenseDataController.calculateUserTotalExpense(username);

        value = value/value;

        Assertions.assertEquals(value,1,"Incorrect Insert Query Has been generated!!");

    }
}
