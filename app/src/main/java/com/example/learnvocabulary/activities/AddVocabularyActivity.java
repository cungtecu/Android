package com.example.learnvocabulary.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learnvocabulary.R;
import com.example.learnvocabulary.model.Word;
import com.example.learnvocabulary.sqlite.WordDAO;

public class AddVocabularyActivity extends AppCompatActivity {
    private EditText edtWord, edtMeaning;
    private WordDAO wordDAO;
    private int topicId; // ID của chủ đề mà từ vựng thuộc về

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addword_activity);

        // Ánh xạ view
        edtWord = findViewById(R.id.edtWord);
        edtMeaning = findViewById(R.id.edtMeaning);
        Button btnSave = findViewById(R.id.saveWord);
        Button btnCancel = findViewById(R.id.cancelWord);

        // Nhận topicId từ Intent
        topicId = getIntent().getIntExtra("TOPIC_ID", -1);

        // Khởi tạo DAO
        wordDAO = new WordDAO(this);

        // Xử lý sự kiện "Save"
        btnSave.setOnClickListener(v -> addVocabulary());

        // Xử lý sự kiện "Cancel"
        btnCancel.setOnClickListener(v -> finish()); // Đóng màn hình
    }

    private void addVocabulary() {
        String wordText = edtWord.getText().toString().trim();
        String meaningText = edtMeaning.getText().toString().trim();

        if (wordText.isEmpty() || meaningText.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo đối tượng Word
        Word newWord = new Word( topicId, wordText, meaningText);

        // Thêm vào database
        boolean success = wordDAO.addWord(newWord);

        if (success) {
            Toast.makeText(this, "Thêm từ vựng thành công!", Toast.LENGTH_SHORT).show();
            finish(); // Quay lại màn hình trước
        } else {
            Toast.makeText(this, "Lỗi! Không thể thêm từ vựng!", Toast.LENGTH_SHORT).show();
        }
    }
}
