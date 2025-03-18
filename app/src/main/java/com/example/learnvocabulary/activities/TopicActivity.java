package com.example.learnvocabulary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learnvocabulary.R;
import com.example.learnvocabulary.adapters.TopicAdapter;
import com.example.learnvocabulary.model.Topic;
import com.example.learnvocabulary.sqlite.TopicDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TopicActivity extends AppCompatActivity {
    private ListView listViewTopics;
    private TopicAdapter adapter;
    private List<Topic> topics;
    private TopicDAO topicDAO;

    @Override
    protected void onResume() {
        super.onResume();
        loadTopics(); // Load lại danh sách khi quay lại màn hình
    }

    private void loadTopics() {
        topics.clear(); // Xóa danh sách cũ
        topics.addAll(topicDAO.getAllTopics()); // Lấy danh sách mới từ database
        adapter.notifyDataSetChanged(); // Cập nhật lại ListView
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_activity);

        // Ánh xạ các thành phần giao diện
        listViewTopics = findViewById(R.id.ListTopic);
        FloatingActionButton btnAddTopic = findViewById(R.id.iconAddTopic);

        // Lấy dữ liệu từ SQLite
        topicDAO = new TopicDAO(this);
        topics = topicDAO.getAllTopics();

        // Gán adapter cho ListView
        adapter = new TopicAdapter(this, topics);
        listViewTopics.setAdapter(adapter);

        // Chuyển sang màn hình thêm Topic
        btnAddTopic.setOnClickListener(v -> {
            startActivity(new Intent(TopicActivity.this, AddTopicActivity.class));
        });

        // Lắng nghe sự kiện chọn một item trong ListView
        listViewTopics.setOnItemClickListener((parent, view, position, id) -> {
            // Lấy Topic được chọn từ danh sách
            Topic selectedTopic = topics.get(position);

            //  selectedTopic không null và ID hợp lệ ?
            if (selectedTopic != null) {
                int topicId = selectedTopic.getId();
                String topicName = selectedTopic.getName();

                // Kiểm tra lại ID để tránh gửi ID null hoặc không hợp lệ
                if (topicId > 0) {
                    Intent intent = new Intent(TopicActivity.this, WordActivity.class);
                    intent.putExtra("TOPIC_ID", topicId); // Gửi ID chủ đề
                    intent.putExtra("TOPIC_NAME", topicName); // Gửi tên chủ đề
                    startActivity(intent);
                } else {
                    // Nếu ID không hợp lệ, bạn có thể hiển thị thông báo lỗi
                    Toast.makeText(TopicActivity.this, "Lỗi: ID chủ đề không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
