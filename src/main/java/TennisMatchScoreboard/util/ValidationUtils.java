package TennisMatchScoreboard.util;

import TennisMatchScoreboard.exceptions.InvalidParameterException;


public class ValidationUtils {
    private final static int REQUIRED_LENGTH_FOR_NAME = 20;


    //TODO сделать чтобы при ошибках ошибка вызывалась бы на странице текстом.
    //TODO тут тоже будет объект Dto
    public static void validate(String firstPlayerName, String secondPlayerName){
        checkName(firstPlayerName);
        checkName(secondPlayerName);


        if(firstPlayerName.trim().replace(" ", "")
                .equalsIgnoreCase(secondPlayerName.trim().replace(" ", ""))){
            throw new InvalidParameterException("Player names must be different");
        }
    }




    private static void checkName(String name){
        if(name == null || name.isBlank()){
            throw new InvalidParameterException("Name cannot be blank");
        }

        if(name.length() > REQUIRED_LENGTH_FOR_NAME){
            throw new InvalidParameterException("Name cannot exceed " + REQUIRED_LENGTH_FOR_NAME + " characters");
        }
    }

}
