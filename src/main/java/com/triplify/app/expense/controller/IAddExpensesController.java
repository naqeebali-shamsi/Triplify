package com.triplify.app.expense.controller;

import java.util.ArrayList;
import java.util.Map;

public interface IAddExpensesController {
    Map<String, Object> postExpense(String description, float amount, String currency, ArrayList<String> usernamelist,
                                           String paidbyusername, long groupid, String date_added);
    Map<String, Object> settleExpense(float amount, String fromusername,
                              String tousername, long groupid);
}
