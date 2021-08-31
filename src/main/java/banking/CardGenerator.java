package banking;

import java.util.Random;

public class CardGenerator {
    public static String generateNumber() {
        Random random = new Random();
        String iin = "400000";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            sb.append(random.nextInt(10));
        }
        String accountIdentifier = sb.toString();
        String checksum = generateChecksum(iin + accountIdentifier);

        return iin + accountIdentifier + checksum;
    }

    public static String generateChecksum(String partialNumber) {
        int[] array = new int[partialNumber.length()];
        for (int i = 0; i < partialNumber.length(); i++) {
            array[i] = Integer.parseInt(partialNumber.substring(i, i+1));
        }
        for (int i = 0; i < array.length; i = i +2) {
            array[i] *= 2;
            if (array[i] > 9) {
                array[i] -=9;
            }
        }
        int sum = 0;
        for (int j : array) {
            sum += j;
        }
        int checksum;
        if (sum %10 == 0) {
            checksum = 0;
        } else {
            checksum = 10 - sum%10;
        }
        return Integer.toString(checksum);
    }

    public static String generatePin() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static boolean isValid(String number) {
        int[] array = new int[number.length()];
        for (int i = 0; i < number.length(); i++) {
            array[i] = Integer.parseInt(number.substring(i, i+1));
        }
        for (int i = 0; i < array.length; i = i + 2) {
            array[i] *= 2;
            if (array[i] > 9) {
                array[i] -= 9;
            }
        }
        int sum = 0;
        for (int j : array) {
            sum += j;
        }
        if (sum %10 == 0) {
            return true;
        }
        return false;
    }
}