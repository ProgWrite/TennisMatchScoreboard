package TennisMatchScoreboard.util;

import TennisMatchScoreboard.exceptions.InvalidParameterException;


public class ValidationUtils {
    private final static int REQUIRED_LENGTH_FOR_NAME = 20;

    public static void validate(String firstPlayerName, String secondPlayerName){
        checkName(firstPlayerName);
        checkName(secondPlayerName);


        if(firstPlayerName.trim().replace(" ", "")
                .equalsIgnoreCase(secondPlayerName.trim().replace(" ", ""))){
            throw new InvalidParameterException("Имена игроков должны быть разными");
        }
    }

    private static void checkName(String name){
        if(name == null || name.isBlank()){
            throw new InvalidParameterException("Имя не может быть пустым");
        }

        if(name.length() > REQUIRED_LENGTH_FOR_NAME){
            throw new InvalidParameterException("Имя не может превышать " + REQUIRED_LENGTH_FOR_NAME + " символов");
        }
    }

}
