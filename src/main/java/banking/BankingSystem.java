package banking;

import java.util.Scanner;

public class BankingSystem {
    Scanner scanner = new Scanner(System.in);

    public void menu(String fileName) {
        while (true) {
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");
            int input = scanner.nextInt();

            switch (input) {
                case 1:
                    createAnAccount(fileName);
                    break;
                case 2:
                    logIntoAccount(fileName);
                    break;
                case 0:
                    System.out.println("Bye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public void createAnAccount(String fileName) {
        DatabaseOperator.createDatabaseIfNotExisting(fileName);
        Account account = new Account(CardGenerator.generateNumber(), CardGenerator.generatePin(), 0);
        DatabaseOperator.addToDatabase(account);

        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(account.getCardNumber());
        System.out.println("Your card PIN:");
        System.out.println(account.getPin());
        System.out.println();
    }

    public void logIntoAccount(String fileName) {
        System.out.println("Enter your card number:");
        String enteredNumber = scanner.next();
        System.out.println("Enter your PIN:");
        String enteredPin = scanner.next();

        int id = DatabaseOperator.findInDatabase(enteredNumber, enteredPin, fileName);
        if (id == -1) {
            System.out.println("Wrong card number or PIN!");
            System.out.println();
        } else {
            System.out.println("You have successfully logged in!");
            System.out.println();
            boolean exit = false;
            while (!exit) {
                System.out.println("1. Balance");
                System.out.println("2. Add income");
                System.out.println("3. Do transfer");
                System.out.println("4. Close account");
                System.out.println("5. Log out");
                System.out.println("0. Exit");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println(DatabaseOperator.checkBalance(id));
                        break;
                    case 2:
                        System.out.println("Enter income:");
                        int income = scanner.nextInt();
                        DatabaseOperator.addIncome(income, id);
                        System.out.println("Income was added!");
                        break;
                    case 3:
                        System.out.println("Transfer");
                        System.out.println("Enter card number:");
                        String number = scanner.next();
                        if (!CardGenerator.isValid(number)) {
                            System.out.println("Probably you made mistake in the card number. Please try again!");
                        } else {
                            if (DatabaseOperator.doesExist(number) == -1) {
                                System.out.println("Such a card does not exist.");
                            } else {
                                System.out.println("Enter how much money you want to transfer:");
                                int amountOfMoney = scanner.nextInt();
                                if (DatabaseOperator.checkBalance(id) < amountOfMoney) {
                                    System.out.println("Not enough money!");
                                } else {
                                    System.out.println("Success!");
                                    DatabaseOperator.transfer(amountOfMoney, id, DatabaseOperator.doesExist(number));
                                }
                            }
                        }
                        break;
                    case 4:
                        DatabaseOperator.closeAccount(id);
                        System.out.println("The account has been closed!");
                        break;
                    case 5:
                        System.out.println("You have successfully logged out!");
                        System.out.println();
                        exit = true;
                        break;
                    case 0:
                        System.out.println("Bye!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        }
    }
}