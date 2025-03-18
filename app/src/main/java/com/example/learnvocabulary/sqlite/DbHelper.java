package com.example.learnvocabulary.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "management_db.sqlite";
    private static final int DB_VERSION = 1;
    public DbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    //Phương thức này được gọi khi cơ sở dữ liệu lần đầu tiên được tạo. Trong phương thức này, bạn thực hiện việc tạo các bảng trong cơ sở dữ liệu.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = ON;");
        String createTopicTableQuery = "CREATE TABLE IF NOT EXISTS Topic (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Name TEXT NOT NULL, " +
                "Description TEXT NOT NULL)";

        String createWordTableQuery = "CREATE TABLE IF NOT EXISTS Word (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TopicId INTEGER NOT NULL, " +
                "Word TEXT NOT NULL, " +
                "Meaning TEXT NOT NULL, " +
                "FOREIGN KEY (TopicId) REFERENCES Topic(Id) ON DELETE CASCADE)";

        db.execSQL(createTopicTableQuery);
        db.execSQL(createWordTableQuery);
    }
    //Phương thức này nâng cấp cơ sở dữ liệu khi có sự thay đổi trong cấu trúc (thêm cột, thay đổi bảng, v.v.).
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTopicTable = "DROP TABLE IF EXISTS Topic";
        String dropWordTable = "DROP TABLE IF EXISTS Word";
        db.execSQL(dropTopicTable);
        db.execSQL(dropWordTable);
        onCreate(db);
    }
}
