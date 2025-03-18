package com.example.learnvocabulary.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.learnvocabulary.R;
import com.example.learnvocabulary.sqlite.TopicDAO;
import com.example.learnvocabulary.model.Topic;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class TopicAdapter extends ArrayAdapter<Topic> {
    private Context context;
    private List<Topic> topics;
    private TopicDAO topicDAO; // Data Access Object để thao tác với database

    public TopicAdapter(Context context, List<Topic> topics) {
        super(context, R.layout.item_topics, topics);
        this.context = context;
        this.topics = topics;
        this.topicDAO = new TopicDAO(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_topics, parent, false);
        }

        Topic topic = topics.get(position);

        TextView tvName = convertView.findViewById(R.id.tvTopicName);
        TextView tvDescription = convertView.findViewById(R.id.tvTopicDescription);
        ImageView imgEdit = convertView.findViewById(R.id.imageSua);
        ImageView imgDelete = convertView.findViewById(R.id.imageXoa);

        tvName.setText(topic.getName());
        tvDescription.setText(topic.getDescription());

        // Sự kiện sửa topic
        imgEdit.setOnClickListener(v -> showEditDialog(topic));

        // Sự kiện xóa topic
        imgDelete.setOnClickListener(v -> showDeleteDialog(topic));

        return convertView;
    }

    // Hiển thị hộp thoại sửa topic
    private void showEditDialog(Topic topic) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.edittopic_activity);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);

        TextInputEditText edtName = dialog.findViewById(R.id.NameTopic);
        TextInputEditText edtDescription = dialog.findViewById(R.id.DesTopic);
        Button btnSave = dialog.findViewById(R.id.saveTopic);
        Button btnCancel = dialog.findViewById(R.id.cancelTopic);

        // Hiển thị dữ liệu hiện tại của topic
        edtName.setText(topic.getName());
        edtDescription.setText(topic.getDescription());

        btnSave.setOnClickListener(v -> {
            String newName = edtName.getText().toString().trim();
            String newDescription = edtDescription.getText().toString().trim();

            // Cập nhật topic trong database
            topic.setName(newName);
            topic.setDescription(newDescription);
            boolean isUpdated = topicDAO.updateTopic(topic);

            if (isUpdated) {
                notifyDataSetChanged(); // Cập nhật giao diện
                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }

            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    // Hiển thị hộp thoại xác nhận xóa topic
    private void showDeleteDialog(Topic topic) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xóa Chủ Đề");
        builder.setMessage("Bạn có chắc chắn muốn xóa chủ đề này không?");
        builder.setCancelable(false);

        builder.setPositiveButton("Có", (dialog, which) -> {
            // Xóa khỏi database
            boolean isDeleted = topicDAO.deleteTopic(topic.getId());

            if (isDeleted) {
                topics.remove(topic); // Xóa khỏi danh sách hiển thị
                notifyDataSetChanged(); // Cập nhật giao diện
                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
