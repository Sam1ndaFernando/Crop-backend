package lk.ijse.demo.util;

import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

public class IdGenerate {
    private static final Set<Integer> generatedNumbers = new HashSet<>();
    private static int minRange = 1;
    private static int maxRange = 1000;

    public static String generateCode(String prefix) {
        int randomNumber;

        do {
            randomNumber = (int) (Math.random() * (maxRange - minRange + 1)) + minRange;
        } while (generatedNumbers.contains(randomNumber));

        generatedNumbers.add(randomNumber);

        if (generatedNumbers.size() >= (maxRange - minRange + 1)) {
            generatedNumbers.clear();
            minRange = 1000;
            maxRange = 10000;
        }
        return prefix + randomNumber;
    }

    public static String imageBase64(byte[] image){

        return Base64.getEncoder().encodeToString(image);
    }
}
