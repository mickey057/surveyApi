package com.blive.surveyApi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;
    private String qTexte;
    private String qrepresent;
    private String title;

    public Question(){}

    public Question(String qTexte, String qrepresent, String title) {
        this.qTexte = qTexte;
        this.qrepresent = qrepresent;
        this.title = title;
    }

    public String getTexte(){
        return this.qTexte + " " + this.qrepresent;
    }
    public void setTexte(String texte)
    {
        String[] parts = texte.split(" ");
        this.qTexte = parts[0];
        this.qrepresent = parts[1];
    }
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getqTexte() {
        return this.qTexte;
    }

    public void setqTexte(String qTexte) {
        this.qTexte = qTexte;
    }

    public String getQrepresent() {
        return this.qrepresent;
    }

    public void setQrepresent(String qrepresent) {
        this.qrepresent = qrepresent;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question = (Question) o;
        return Objects.equals(getId(), question.getId()) && Objects.equals(getqTexte(), question.getqTexte()) && Objects.equals(getQrepresent(), question.getQrepresent()) && Objects.equals(getTitle(), question.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getqTexte(), getQrepresent(), getTitle());
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", qTexte='" + qTexte + '\'' +
                ", qrepresent='" + qrepresent + '\'' +
                ", Title='" + title + '\'' +
                '}';
    }
}
