package org.example.budgetplaner.databasepack.database.transactions;
import java.time.LocalDate;


public class Transactions {
        private long id;
        private LocalDate date;
        private double amount;
        private String category;
        private String type; // "income" or "expense"

        public Transactions(long id, LocalDate date, double amount, String category, String type) {
            this.id = id;
            this.date = date;
            this.amount = amount;
            this.category = category;
            this.type = type;
        }

        public long getId() { return id; }
        public LocalDate getDate() { return date; }
        public double getAmount() { return amount; }
        public String getCategory() { return category; }
        public String getType() { return type; }

        @Override
        public String toString() {
            return String.format("[%s] %.2f € - %s (%s)", date, amount, category, type);
        }
    }


