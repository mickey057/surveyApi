package com.blive.surveyApi;

public class QuestionNotFoundException extends RuntimeException{

      public  QuestionNotFoundException(Long id){
            super("Question not found " + id);
        }
    }

