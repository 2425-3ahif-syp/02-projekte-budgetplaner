package org.example.budgetplaner.databasepack.database.transactions;
import java.time.LocalDate;


public class Transactions {
        private long id;
        private LocalDate date;
        private double amount;
        private int category_id;
        private boolean is_income;

        public Transactions(long id, LocalDate date, double amount, int category_id, boolean is_income) {
            this.id = id;
            this.date = date;
            this.amount = amount;
            this.category_id = category_id;
            this.is_income = is_income;
        }

        public long getId() { return id; }
        public LocalDate getDate() { return date; }
        public double getAmount() { return amount; }
        public int getCategoryId() { return category_id; }
        public String getType() { return is_income ? "income" : "expense"; }

        @Override
        public String toString() {
            return String.format("[%s] %.2f € - %s (%s)", date, amount, category_id, is_income);
        }
    }


