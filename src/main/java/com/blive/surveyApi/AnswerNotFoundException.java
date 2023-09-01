package com.blive.surveyApi;

public class AnswerNotFoundException extends RuntimeException{

    public  AnswerNotFoundException(Long id){
        super("Answer not found " + id);
    }
}