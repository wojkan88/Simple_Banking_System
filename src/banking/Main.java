package banking;

public class Main {
    public static void main(String[] args) {
        if (args.length == 2) {
            if (args[0].equals("-fileName")) {
                BankingSystem bs = new BankingSystem();
                bs.menu(args[1]);
            } else {
                System.out.println("Invalid argument");
            }
        } else {
            System.out.println("Invalid number of arguments");
        }
    }
}