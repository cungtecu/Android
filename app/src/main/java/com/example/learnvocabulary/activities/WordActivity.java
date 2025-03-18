package com.example.learnvocabulary.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learnvocabulary.R;
import com.example.learnvocabulary.adapters.WordAdapter;
import com.example.learnvocabulary.model.Word;
import com.example.learnvocabulary.sqlite.WordDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class WordActivity extends AppCompatActivity {
    private ListView listViewWords;
    private WordAdapter adapter;
    private List<Word> words;
    private WordDAO wordDAO;
    private int topicId;

    @Override
    protected void onResume() {
        super.onResume();
        loadWords(); // Load lại danh sách từ vựng khi quay lại màn hình
    }

    private void loadWords() {
        words.clear();  // Xóa danh sách cũ
        words.addAll(wordDAO.getWordsByTopicId(topicId));  // Lấy danh sách từ vựng theo topicId
        adapter.notifyDataSetChanged();  // Cập nhật lại ListView
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_activity);

        // Ánh xạ các thành phần giao diện
        listViewWords = findViewById(R.id.ListWord);
        FloatingActionButton btnAddWord = findViewById(R.id.iconAddWord);
        Button btnBack = findViewById(R.id.Back);

        // Nhận topicId từ Intent
        topicId = getIntent().getIntExtra("TOPIC_ID", -1);

        // Khởi tạo DAO
        wordDAO = new WordDAO(this);

        // Lấy danh sách từ vựng của chủ đề này
        words = wordDAO.getWordsByTopicId(topicId);

        // Gán adapter cho ListView
        adapter = new WordAdapter(this, words);
        listViewWords.setAdapter(adapter);

        // Sự kiện click item để xóa từ vựng
        listViewWords.setOnItemClickListener((parent, view, position, id) -> {
            Word selectedWord = words.get(position);
            showDeleteDialog(selectedWord);  // Hiển thị hộp thoại xác nhận xóa
        });

        // Chuyển sang màn hình thêm từ vựng
        btnAddWord.setOnClickListener(v -> {
            Intent intent = new Intent(WordActivity.this, AddVocabularyActivity.class);
            intent.putExtra("TOPIC_ID", topicId);  // Gửi topicId
            startActivity(intent);
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(WordActivity.this, TopicActivity.class);
            startActivity(intent);
        });
    }

    private void showDeleteDialog(Word word) {
        new AlertDialog.Builder(this)
                .setMessage("Bạn có chắc chắn muốn xóa từ vựng này?")
                .setPositiveButton("Xóa", (dialog, which) -> deleteWord(word))
                .setNegativeButton("Hủy", null)
                .show();
    }

    // Phương thức xử lý xóa từ vựng
    private void deleteWord(Word word) {
        boolean success = wordDAO.deleteWord(word.getId());
        if (success) {
            Toast.makeText(this, "Đã xóa từ vựng", Toast.LENGTH_SHORT).show();
            loadWords();  // Cập nhật lại danh sách sau khi xóa
        } else {
            Toast.makeText(this, "Lỗi! Không thể xóa từ vựng", Toast.LENGTH_SHORT).show();
        }
    }
}
