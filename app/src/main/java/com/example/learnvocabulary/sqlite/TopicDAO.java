package com.example.learnvocabulary.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.learnvocabulary.model.Topic;

import java.util.ArrayList;
import java.util.List;

public class TopicDAO {
    private SQLiteDatabase db;

    public TopicDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Thêm chủ đề mới
    public boolean addTopic(Topic topic) {
        ContentValues values = new ContentValues();
        values.put("Name", topic.getName());
        values.put("Description", topic.getDescription());

        long result = db.insert("Topic", null, values);
        return result > 0;
    }

    // Lấy danh sách tất cả chủ đề
    public List<Topic> getAllTopics() {
        List<Topic> topicList = new ArrayList<>();
        String query = "SELECT * FROM Topic";
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0); // Lấy id từ cột đầu tiên
            String name = cursor.getString(1); // Lấy name từ cột thứ hai
            String description = cursor.getString(2); // Lấy description từ cột thứ ba

            // Tạo đối tượng Topic với id, name, và description
            Topic topic = new Topic(name, description);
            topic.setId(id); // Gán id cho đối tượng Topic

            // Thêm topic vào danh sách
            topicList.add(topic);
        }
        cursor.close();
        return topicList;
    }


    // Cập nhật chủ đề
    public boolean updateTopic(Topic topic) {
        ContentValues values = new ContentValues();
        values.put("Name", topic.getName());
        values.put("Description", topic.getDescription());

        int result = db.update("Topic", values, "Id = ?", new String[]{String.valueOf(topic.getId())});
        return result > 0;
    }

    //  Xóa chủ đề
    public boolean deleteTopic(int id) {
        int result = db.delete("Topic", "Id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }
}
