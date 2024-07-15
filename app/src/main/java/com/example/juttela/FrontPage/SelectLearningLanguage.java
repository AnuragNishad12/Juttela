package com.example.juttela.FrontPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.juttela.R;
import com.example.juttela.databinding.ActivitySelectLearningLanguageBinding;

import java.util.ArrayList;
import java.util.List;

public class SelectLearningLanguage extends AppCompatActivity {

    ActivitySelectLearningLanguageBinding binding;
    private List<Language> languages;
    private LanguageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySelectLearningLanguageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.languageRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        languages = generateLanguageList();
        adapter = new LanguageAdapter(languages, new LanguageAdapter.OnLanguageSelectionChangeListener() {
            @Override
            public void onSelectionChanged(int selectedCount) {
                updateSubmitButton(selectedCount);
            }
        });
        binding.languageRecyclerView.setAdapter(adapter);
        String name = getIntent().getStringExtra("name");
        String age = getIntent().getStringExtra("age");
        String country = getIntent().getStringExtra("country");
        String selectedItem = getIntent().getStringExtra("selectedItem");
        String selectedCountry = getIntent().getStringExtra("selectedCountry");
        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> selectedLanguages = getSelectedLanguages();
                Intent intent = new Intent(SelectLearningLanguage.this, Avtar_Setting.class); // Replace NextActivity with the name of your target activity
                intent.putStringArrayListExtra("selectedLanguages", new ArrayList<>(selectedLanguages));
                intent.putExtra("name",name);
                intent.putExtra("age",age);
                intent.putExtra("country",country);
                intent.putExtra("selectedItem",selectedItem);
                intent.putExtra("selectedCountry",selectedCountry);
                startActivity(intent);
            }
        });

        updateSubmitButton(0);
    }


    private List<Language> generateLanguageList() {
        List<Language> languageList = new ArrayList<>();
        String[] languageNames = {
                "Afrikaans", "Albanian", "Amharic", "Arabic", "Armenian", "Azerbaijani",
                "Basque", "Belarusian", "Bengali", "Bosnian", "Bulgarian", "Burmese",
                "Catalan", "Cebuano", "Chichewa", "Chinese (Simplified)", "Chinese (Traditional)",
                "Corsican", "Croatian", "Czech", "Danish", "Dutch", "English", "Esperanto",
                "Estonian", "Filipino", "Finnish", "French", "Frisian", "Galician", "Georgian",
                "German", "Greek", "Gujarati", "Haitian Creole", "Hausa", "Hawaiian", "Hebrew",
                "Hindi", "Hmong", "Hungarian", "Icelandic", "Igbo", "Indonesian", "Irish",
                "Italian", "Japanese", "Javanese", "Kannada", "Kazakh", "Khmer", "Korean",
                "Kurdish", "Kyrgyz", "Lao", "Latin", "Latvian", "Lithuanian", "Luxembourgish",
                "Macedonian", "Malagasy", "Malay", "Malayalam", "Maltese", "Maori", "Marathi",
                "Mongolian", "Nepali", "Norwegian", "Odia", "Pashto", "Persian", "Polish",
                "Portuguese", "Punjabi", "Romanian", "Russian", "Samoan", "Scots Gaelic",
                "Serbian", "Sesotho", "Shona", "Sindhi", "Sinhala", "Slovak", "Slovenian",
                "Somali", "Spanish", "Sundanese", "Swahili", "Swedish", "Tajik", "Tamil",
                "Telugu", "Thai", "Turkish", "Ukrainian", "Urdu", "Uyghur", "Uzbek",
                "Vietnamese", "Welsh", "Xhosa", "Yiddish", "Yoruba", "Zulu"
        };

        for (String name : languageNames) {
            languageList.add(new Language(name, false));
        }
        return languageList;
    }

    private List<String> getSelectedLanguages() {
        List<String> selectedLanguages = new ArrayList<>();
        for (Language language : languages) {
            if (language.isSelected()) {
                selectedLanguages.add(language.getName());
            }
        }
        return selectedLanguages;
    }

    private void updateSubmitButton(int selectedCount) {
        binding.submitButton.setText("Submit (" + selectedCount + "/3)");
        binding.submitButton.setEnabled(selectedCount > 0);

    }


}