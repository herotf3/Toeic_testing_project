package com.example.huong.toeic_project.classes;

import java.io.Serializable;

/**
 * Created by dell on 6/29/2016.
 */
public class Status implements Serializable{
    private int[] customerAnswer=new int[100];
    private int[] key=new int[100];

    public Status()
    {
        for (int i=0;i<100;i++)
        {
            customerAnswer[i]=key[i]=0;
        }
    }

    public void setCustomerAnswer(int index,int answer)
    {
        customerAnswer[index]=answer;
    }

    public int getCustomerAnwser(int index)
    {
        return customerAnswer[index];
    }

    public void setKey(int index,int _key)
    {
        key[index]=_key;
    }

    public int[] getCustomerAnswer() {
        return customerAnswer;
    }

    public int[] getKey() {
        return key;
    }

    public void setKey(int[] key) {
        this.key = key;
    }
}
