package com.blive.surveyApi.repository;

import com.blive.surveyApi.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
