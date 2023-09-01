package com.blive.surveyApi.controller;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.blive.surveyApi.AnswerModelAssembler;
import com.blive.surveyApi.AnswerNotFoundException;
import com.blive.surveyApi.model.Answer;
import com.blive.surveyApi.repository.AnswerRepository;
import com.blive.surveyApi.status.Status;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AnswerController {

    private final AnswerRepository answerRepository;
    private final AnswerModelAssembler assembler;

    public AnswerController(AnswerRepository answerRepository, AnswerModelAssembler assembler) {
        this.answerRepository = answerRepository;
        this.assembler = assembler;
    }
    @GetMapping("/answers")
    CollectionModel<EntityModel<Answer>> all() {

        List<EntityModel<Answer>> answers = answerRepository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(answers, //
                linkTo(methodOn(AnswerController.class).all()).withSelfRel());
    }

    @GetMapping("/answers/{id}")
    EntityModel<Answer> one(@PathVariable Long id) {

        Answer answer = answerRepository.findById(id) //
                .orElseThrow(() -> new AnswerNotFoundException(id));

        return assembler.toModel(answer);
    }

    @PostMapping("/answers")
    ResponseEntity<EntityModel<Answer>> newAnswer(@RequestBody Answer answer) {

        answer.setStatus(Status.IN_PROGRESS);
        Answer newAnswer = answerRepository.save(answer);

        return ResponseEntity //
                .created(linkTo(methodOn(AnswerController.class).one(newAnswer.getId())).toUri()) //
                .body(assembler.toModel(newAnswer));
    }
    // end::main[]

    // tag::delete[]
    @DeleteMapping("/answers/{id}/cancel")
    ResponseEntity<?> cancel(@PathVariable Long id) {

        Answer answer = answerRepository.findById(id) //
                .orElseThrow(() -> new AnswerNotFoundException(id));

        if (answer.getStatus() == Status.IN_PROGRESS) {
            answer.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(assembler.toModel(answerRepository.save(answer)));
        }

        return ResponseEntity //
                .status(HttpStatus.METHOD_NOT_ALLOWED) //
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                .body(Problem.create() //
                        .withTitle("Method not allowed") //
                        .withDetail("You can't cancel an answer that is in the " + answer.getStatus() + " status"));
    }
    // end::delete[]

    // tag::complete[]
    @PutMapping("/answers/{id}/complete")
    ResponseEntity<?> complete(@PathVariable Long id) {

        Answer answer = answerRepository.findById(id) //
                .orElseThrow(() -> new AnswerNotFoundException(id));

        if (answer.getStatus() == Status.IN_PROGRESS) {
            answer.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(assembler.toModel(answerRepository.save(answer)));
        }

        return ResponseEntity //
                .status(HttpStatus.METHOD_NOT_ALLOWED) //
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                .body(Problem.create() //
                        .withTitle("Method not allowed") //
                        .withDetail("You can't complete an answer that is in the " + answer.getStatus() + " status"));
    }
    // end::complete[]

}
