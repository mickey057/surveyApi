package com.blive.surveyApi;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.blive.surveyApi.controller.AnswerController;
import com.blive.surveyApi.model.Answer;
import com.blive.surveyApi.status.Status;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
public class AnswerModelAssembler implements RepresentationModelAssembler<Answer, EntityModel<Answer>> {

    @Override
    public EntityModel<Answer> toModel(Answer answer){
        // Unconditional links to single-item resource and aggregate root

        EntityModel<Answer> answerModel = EntityModel.of(answer,
                linkTo(methodOn(AnswerController.class).one(answer.getId())).withSelfRel(),
                linkTo(methodOn(AnswerController.class).all()).withRel("orders"));

        // Conditional links based on state of the order

        if(answer.getStatus() == Status.IN_PROGRESS){

            answerModel.add(linkTo(methodOn(AnswerController.class).cancel(order.getId())).withRel("cancel"));
            answerModel.add(linkTo(methodOn(AnswerController.class).complete(order.getId())).withRel("complete"));
        }

        return answerModel;
    }


}
