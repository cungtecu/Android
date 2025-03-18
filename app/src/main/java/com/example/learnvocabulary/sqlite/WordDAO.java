package com.example.learnvocabulary.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.learnvocabulary.model.Word;

import java.util.ArrayList;
import java.util.List;

public class WordDAO {
    private SQLiteDatabase db;

    public WordDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Thêm từ vựng mới
    public boolean addWord(Word word) {
        ContentValues values = new ContentValues();
        values.put("TopicId", word.getTopicId());
        values.put("Word", word.getWord());
        values.put("Meaning", word.getMeaning());

        long result = db.insert("Word", null, values);
        if (result > 0) {
            // Set the generated Id in the Word object
            word.setId((int) result); // You might need to cast if it's a long to an int
            return true;  // Thêm thành công
        }
        return false;  // Thêm không thành công  // Trả về true nếu thêm thành công
    }

    // Lấy danh sách từ vựng theo TopicId
    public List<Word> getWordsByTopicId(int topicId) {
        List<Word> words = new ArrayList<>();
        String[] columns = {"Id", "TopicId", "Word", "Meaning"};
        String selection = "TopicId = ?";
        String[] selectionArgs = {String.valueOf(topicId)}; // Lọc theo TopicId

        // Truy vấn cơ sở dữ liệu với điều kiện topicId
        Cursor cursor = db.query("Word", columns, selection, selectionArgs, null, null, null);

        // Lặp qua các kết quả
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int fetchedTopicId = cursor.getInt(cursor.getColumnIndexOrThrow("TopicId"));
            String word = cursor.getString(cursor.getColumnIndexOrThrow("Word"));
            String meaning = cursor.getString(cursor.getColumnIndexOrThrow("Meaning"));

            // Tạo đối tượng Word với các giá trị lấy được
            Word _word = new Word(fetchedTopicId, word, meaning);
            _word.setId(id);
            words.add(_word); // Truyền đúng giá trị vào constructor
        }

        cursor.close(); // Đảm bảo đóng cursor sau khi sử dụng
        return words;
    }


    // Cập nhật từ vựng
    public boolean updateWord(Word word) {
        ContentValues values = new ContentValues();
        values.put("Word", word.getWord());
        values.put("Meaning", word.getMeaning());

        int result = db.update("Word", values, "Id = ?", new String[]{String.valueOf(word.getId())});
        return result > 0;  // Trả về true nếu cập nhật thành công
    }

    // Xóa từ vựng theo ID
    public boolean deleteWord(int id) {
        int rowsDeleted = db.delete("Word", "Id = ?", new String[]{String.valueOf(id)});
        return rowsDeleted > 0;
    }
}
