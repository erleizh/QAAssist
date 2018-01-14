package com.lll.auxiliary.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Topic implements Parcelable {
    private String question;
    private ArrayList<String> answers;


    public Topic(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public void addAnswer(String answers) {
        if (this.answers == null) this.answers = new ArrayList<>();
        this.answers.add(answers);
    }

    @Override
    public String toString() {
        return "Topic{" +
                "question='" + question + '\'' +
                ", answers=" + answers +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.question);
        dest.writeStringList(this.answers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Topic topic = (Topic) o;

        return (question != null ? question.equals(topic.question) : topic.question == null) && (answers != null ? answers.equals(topic.answers) : topic.answers == null);
    }

    @Override
    public int hashCode() {
        int result = question != null ? question.hashCode() : 0;
        result = 31 * result + (answers != null ? answers.hashCode() : 0);
        return result;
    }

    public Topic() {
    }

    protected Topic(Parcel in) {
        this.question = in.readString();
        this.answers = in.createStringArrayList();
    }

    public static final Creator<Topic> CREATOR = new Creator<Topic>() {
        @Override
        public Topic createFromParcel(Parcel source) {
            return new Topic(source);
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };
}