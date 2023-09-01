package com.blive.surveyApi.controller;

import com.blive.surveyApi.QuestionModelAssembler;
import com.blive.surveyApi.QuestionNotFoundException;
import com.blive.surveyApi.model.Question;
import com.blive.surveyApi.repository.QuestionRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class QuestionController {

    private final QuestionRepository repository;
    private final QuestionModelAssembler assembler;

    QuestionController(QuestionRepository repository, QuestionModelAssembler assembler){
        this.repository = repository;
        this.assembler=assembler;
    }

    @GetMapping("/questions")
   public CollectionModel<EntityModel<Question>> all() {
        List<EntityModel<Question>> questions = repository.findAll().stream() //
                .map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(questions,
                linkTo(methodOn(QuestionController.class).all()).withSelfRel());
    }

    @PostMapping("/saveQuestion")
    ResponseEntity<?> newQuestion(@RequestBody Question newquestion){
        EntityModel<Question> questionEntityModel = assembler.toModel(repository.save(newquestion));
       return ResponseEntity //
         .created(questionEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
               .body(questionEntityModel) ;
    }

    // single item
    @GetMapping("/question/{id}")
    public EntityModel<Question> one(@PathVariable Long id){
        Question question =  repository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
        return assembler.toModel(question);

    }
    @PutMapping("/questions/{id}")
   ResponseEntity<?> replaceQuestion(@RequestBody Question newQuestion, @PathVariable Long id) {

         Question updateQuestion = repository.findById(id)
                .map(question -> {
                    question.setTexte(newQuestion.getTexte());
                    question.setTitle(newQuestion.getTitle());
                    return repository.save(question);
                })
                .orElseGet(() -> {
                    newQuestion.setId(id);
                    return repository.save(newQuestion);
                });
         EntityModel<Question> entityModel = assembler.toModel(updateQuestion);

         return ResponseEntity //
                 .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                 .body(entityModel);
    }

    @DeleteMapping("/questions/{id}")
    ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

