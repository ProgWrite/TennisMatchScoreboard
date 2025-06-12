package TennisMatchScoreboard.util;

import TennisMatchScoreboard.exceptions.InvalidParameterException;

//TODO возможно нужна будет уникальная валидация на имя, либо проверка что такое имя есть в БД (на уровне БД)
public class ValidationUtils {
    private final static int REQUIRED_LENGTH_FOR_NAME = 20;



    //TODO тут тоже будет объект Dto
    public static void validate(String firstPlayerName, String secondPlayerName){
        checkName(firstPlayerName);
        checkName(secondPlayerName);


        if(firstPlayerName.trim().equalsIgnoreCase(secondPlayerName.trim())){
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
