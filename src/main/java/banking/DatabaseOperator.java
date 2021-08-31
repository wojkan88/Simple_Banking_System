package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseOperator {
    static SQLiteDataSource dataSource = new SQLiteDataSource();

    public static void createDatabaseIfNotExisting(String fileName) {
        String url = "jdbc:sqlite:" + fileName;
        dataSource.setUrl(url);
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                        "id INTEGER PRIMARY KEY," +
                        "number TEXT," +
                        "pin TEXT," +
                        "balance INTEGER DEFAULT 0)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addToDatabase(Account account) {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("INSERT INTO card (number, pin, balance) VALUES ('" +
                        account.getCardNumber() + "', '" +
                        account.getPin() + "', '" +
                        account.getBalance() + "')");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int findInDatabase(String enteredNumber, String enteredPin, String fileName) {
        String url = "jdbc:sqlite:" + fileName;
        dataSource.setUrl(url);
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet cards = statement.executeQuery("SELECT * FROM card")) {
                    while (cards.next()) {
                        int id = cards.getInt("id");
                        String number = cards.getString("number");
                        String pin = cards.getString("pin");

                        if (enteredNumber.equals(number) && enteredPin.equals(pin)) {
                            return id;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int checkBalance(int cardId) {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet cards = statement.executeQuery("SELECT * FROM card")) {
                    while (cards.next()) {
                        int id = cards.getInt("id");
                        int balance = cards.getInt("balance");

                        if (cardId == id) {
                            return balance;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void addIncome(int income, int id) {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("UPDATE card SET balance = balance + " +
                        income + " WHERE id = " + id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeAccount(int id) {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DELETE FROM card WHERE id = " +id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int doesExist(String number) {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet cards = statement.executeQuery("SELECT * FROM card")) {
                    while (cards.next()) {
                        int id = cards.getInt("id");
                        String cardNumber = cards.getString("number");

                        if (number.equals(cardNumber)) {
                            return id;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void transfer(int amountOfMoney, int senderId, int receiverId) {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("UPDATE card SET balance = balance - " +
                        amountOfMoney + " WHERE id = " + senderId);
                statement.executeUpdate("UPDATE card SET balance = balance + " +
                        amountOfMoney + " WHERE id = " + receiverId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}