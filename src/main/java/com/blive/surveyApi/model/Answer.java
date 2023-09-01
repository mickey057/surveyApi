package com.blive.surveyApi.model;

import com.blive.surveyApi.status.Status;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Answer {

    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private Status status;
public Answer(){}

    public Answer(String description, Status status) {
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;
        Answer answer = (Answer) o;
        return Objects.equals(getId(), answer.getId()) && Objects.equals(getDescription(), answer.getDescription()) && getStatus() == answer.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription(), getStatus());
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
