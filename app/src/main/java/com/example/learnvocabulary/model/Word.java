package com.example.learnvocabulary.model;

import java.io.Serializable;

public class Word implements Serializable {
    private int id;         // ID của từ vựng (int để khớp với SQLite)
    private int topicId;    // ID của chủ đề
    private String word;    // Từ vựng
    private String meaning; // Nghĩa của từ

    // Constructor đầy đủ
    public Word( int topicId, String word, String meaning) {
        this.topicId = topicId;
        this.word = word;
        this.meaning = meaning;
    }

    // Getter & Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    // Override toString() để hiển thị dễ dàng trong ListView/RecyclerView
    @Override
    public String toString() {
        return word + " - " + meaning;
    }
}
