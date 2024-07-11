package com.example.juttela.FrontPage;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.juttela.R;

import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {

    private List<Language> languages;
    private int selectedCount = 0;
    private static final int MAX_SELECTIONS = 3;
    private OnLanguageSelectionChangeListener listener;

    public interface OnLanguageSelectionChangeListener {
        void onSelectionChanged(int selectedCount);
    }

    public LanguageAdapter(List<Language> languages, OnLanguageSelectionChangeListener listener) {
        this.languages = languages;
        this.listener = listener;
        for (Language language : languages) {
            if (language.isSelected()) {
                selectedCount++;
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.language_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Language language = languages.get(position);
        holder.checkBox.setText(language.getName());
        holder.checkBox.setChecked(language.isSelected());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                if (checkBox.isChecked()) {
                    if (selectedCount >= MAX_SELECTIONS) {
                        checkBox.setChecked(false);
                        Toast.makeText(v.getContext(), "You can only select up to 3 languages", Toast.LENGTH_SHORT).show();
                    } else {
                        language.setSelected(true);
                        selectedCount++;
                        if (listener != null) {
                            listener.onSelectionChanged(selectedCount);
                        }
                    }
                } else {
                    language.setSelected(false);
                    selectedCount--;
                    if (listener != null) {
                        listener.onSelectionChanged(selectedCount);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.language_checkbox);
        }
    }
}