package com.example.learnvocabulary.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learnvocabulary.R;
import com.example.learnvocabulary.model.Topic;
import com.example.learnvocabulary.sqlite.TopicDAO;

public class AddTopicActivity extends AppCompatActivity {
    private EditText edtName, edtDescription;
    private TopicDAO topicDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtopic_activity);

        // Ánh xạ view
        edtName = findViewById(R.id.NameTopic);
        edtDescription = findViewById(R.id.DesTopic);
        Button btnSave = findViewById(R.id.saveTopic);
        Button btnCancel = findViewById(R.id.cancelTopic);

        // Khởi tạo DAO
        topicDAO = new TopicDAO(this);

        // Xử lý sự kiện nút "Save"
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTopic();
            }
        });

//         Xử lý sự kiện nút "Cancel"
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng màn hình
            }
        });
    }

    private void addTopic() {
        String name = edtName.getText().toString().trim();
        String description = edtDescription.getText().toString().trim();

        // Tạo đối tượng Topic
        Topic topic = new Topic(name, description);

//        // Thêm vào database
        boolean success = topicDAO.addTopic(topic);

        if (success) {
            Toast.makeText(this, "Thêm chủ đề thành công!", Toast.LENGTH_SHORT).show();
            finish(); // Quay lại màn hình trước
        } else {
            Toast.makeText(this, "Lỗi! Không thể thêm chủ đề!", Toast.LENGTH_SHORT).show();
        }
    }
}
