package co.istad.springdatajpa.util;

import java.util.concurrent.ThreadLocalRandom;

public class GeneratedUtil {

    public static String generateRandomCode(){
        String prefix = "ISTAD-PRO-";
        int randomNumber = ThreadLocalRandom.current().nextInt(0, 10000);
        return prefix + String.format("%05d", randomNumber);
    }

}
