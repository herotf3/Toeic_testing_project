package com.example.huong.toeic_project.classes;

import java.util.ArrayList;

/**
 * Created by 508-11 on 6/25/2016.
 */
public class Part
{
    private static int numberOfQuestion;   //số lượng câu hỏi
    private String audioLink;    //file nghe
    private ArrayList<Question> questionList;    //danh sách câu hỏi
    private static int numberHasDone;    //số câu hỏi người dùng đã làm(đã check ô nào đó)


    public String showStatus()
    {
        String total=String.valueOf(getNumberOfQuestion());
        String done=String.valueOf(getNumberHasDone());
        return (done+"/"+total);
    }

    public static int getNumberOfQuestion() {
        return numberOfQuestion;
    }

    public static void setNumberOfQuestion(int numberOfQuestion) {
        Part.numberOfQuestion = numberOfQuestion;
    }

    public static int getNumberHasDone() {
        return numberHasDone;
    }

    public static void setNumberHasDone(int numberHasDone) {
        Part.numberHasDone = numberHasDone;
    }

    public String getAudioLink() {
        return audioLink;
    }

    public void setAudioLink(String audioLink) {
        this.audioLink = audioLink;
    }

    public ArrayList<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }
}
