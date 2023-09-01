package com.blive.surveyApi;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.blive.surveyApi.controller.QuestionController;
import com.blive.surveyApi.model.Question;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class QuestionModelAssembler implements RepresentationModelAssembler<Question, EntityModel<Question>>
{
    @Override
    public EntityModel<Question> toModel(Question question)
    {
        return EntityModel.of(question,//
                linkTo(methodOn(QuestionController.class).one(question.getId())).withSelfRel(),
                linkTo(methodOn(QuestionController.class).all()).withRel("questions"));
    }
}
