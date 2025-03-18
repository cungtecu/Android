package com.example.learnvocabulary.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.learnvocabulary.R;
import com.example.learnvocabulary.model.Word;
import com.example.learnvocabulary.sqlite.WordDAO;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class WordAdapter extends ArrayAdapter<Word> {
    private Context context;
    private List<Word> words;
    private WordDAO wordDAO;

    public WordAdapter(Context context, List<Word> words) {
        super(context, R.layout.item_words, words);
        this.context = context;
        this.words = words;
        this.wordDAO = new WordDAO(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_words, parent, false);
        }

        Word word = words.get(position);

        TextView tvWord = convertView.findViewById(R.id.NameWord);
        TextView tvMeaning = convertView.findViewById(R.id.Meaning);
        ImageView imgDelete = convertView.findViewById(R.id.imageDelete);
//        ImageView imgEdit = convertView.findViewById(R.id.);

        tvWord.setText(word.getWord());
        tvMeaning.setText(word.getMeaning());

        // Xoá từ vựng khi bấm vào nút Xoá
        imgDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xóa từ vựng");
            builder.setMessage("Bạn có chắc chắn muốn xóa từ này không?");
            builder.setPositiveButton("Có", (dialog, which) -> {
                wordDAO.deleteWord(word.getId());
                words.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        // Sửa từ vựng khi bấm vào nút Sửa
//        imgEdit.setOnClickListener(v -> showEditDialog(word, position));

        return convertView;
    }

    // Hiển thị hộp thoại chỉnh sửa từ vựng
//    private void showEditDialog(Word word, int position) {
//        Dialog dialog = new Dialog(context);
//        dialog.setContentView(R.layout.editword_activity);
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        dialog.setCancelable(false);
//
//        TextInputEditText edtWord = dialog.findViewById(R.id.edtWord);
//        TextInputEditText edtMeaning = dialog.findViewById(R.id.edtMeaning);
//        Button btnSave = dialog.findViewById(R.id.saveVocabulary);
//        Button btnCancel = dialog.findViewById(R.id.cancelVocabulary);
//
//        // Hiển thị dữ liệu hiện tại
//        edtWord.setText(word.getWord());
//        edtMeaning.setText(word.getMeaning());
//
////        // Lưu khi bấm vào nút Save
////        btnSave.setOnClickListener(v -> {
////            String newWord = edtWord.getText().toString().trim();
////            String newMeaning = edtMeaning.getText().toString().trim();
////
////            // Cập nhật từ vựng trong cơ sở dữ liệu
////            word.setWord(newWord);
////            word.setMeaning(newMeaning);
////            wordDAO.updateWord(word);
////
////            // Cập nhật lại danh sách và giao diện
////            words.set(position, word);
////            notifyDataSetChanged();
////
////            Toast.makeText(context, "Chỉnh sửa thành công!", Toast.LENGTH_SHORT).show();
////            dialog.dismiss();
////
////        });
//        btnSave.setOnClickListener(v -> {
//                    String newWord = edtWord.getText().toString().trim();
//                    String newMeaning = edtMeaning.getText().toString().trim();
//
//                    word.setWord(newWord);
//                    word.setMeaning(newMeaning);
//                    boolean isUpdated = wordDAO.updateWord(word);
//                    // Cập nhật topic trong database
//
//                    if (isUpdated) {
//                        notifyDataSetChanged(); // Cập nhật giao diện
//                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
//                    }
//                    dialog.dismiss();
//        });
//        // Hủy khi bấm vào nút Cancel
//        btnCancel.setOnClickListener(v -> dialog.dismiss());
//
//        dialog.show();
//    }
}
