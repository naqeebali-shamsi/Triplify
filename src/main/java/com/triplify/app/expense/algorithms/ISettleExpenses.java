package com.triplify.app.expense.algorithms;

import java.util.HashMap;

public interface ISettleExpenses {
    HashMap<String, Float> fetchSettleExpenses(String userid, Long groupid);
}
