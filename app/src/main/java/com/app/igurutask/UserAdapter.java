package com.app.igurutask;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class UserAdapter extends ListAdapter<User, UserAdapter.UserViewHolder> {

    private final UserClickListener userClickListener;

    protected UserAdapter(UserClickListener userClickListener) {
        super(DIFF_CALLBACK);
        this.userClickListener = userClickListener;
    }

    public interface UserClickListener {
        void onImageUploadClick(User user);
    }

    private static final DiffUtil.ItemCallback<User> DIFF_CALLBACK = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = getItem(position);
        holder.bind(user);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private final ImageView userAvatar;
        private final TextView userName;
        private final TextView userEmail;
        private final ImageView uploadImage;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.user_avatar);
            userName = itemView.findViewById(R.id.user_name);
            userEmail = itemView.findViewById(R.id.user_email);
            uploadImage = itemView.findViewById(R.id.upload_image);

            uploadImage.setOnClickListener(v -> {
                User user = getItem(getAdapterPosition());
                if (userClickListener != null) {
                    userClickListener.onImageUploadClick(user);
                }
            });
        }

        public void bind(User user) {
            userName.setText(user.getFirstName() + " " + user.getLastName());
            userEmail.setText(user.getEmail());

            if (user.getUploadedImageUri() != null ) {
                Glide.with(userAvatar.getContext())
                        .load(user.getUploadedImageUri())
                        .into(userAvatar);
            } else {
                Glide.with(userAvatar.getContext())
                        .load(user.getAvatar())
                        .into(userAvatar);
            }
        }
    }
}
