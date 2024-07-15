package com.example.juttela.FrontPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.juttela.R;
import com.example.juttela.databinding.ActivityDetailsBinding;

public class Details_Activity extends AppCompatActivity {

    ActivityDetailsBinding binding;
    String[] items = {"Male", "Female", "Others"};
    String[] country = {"Afrikaans", "Albanian", "Amharic", "Arabic", "Armenian", "Azerbaijani",
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
            "Vietnamese", "Welsh", "Xhosa", "Yiddish", "Yoruba", "Zulu"};
    String selectedItem;
    String selectedCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
   binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedItem = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, country);
        binding.countrySpinner.setAdapter(adapter2);

        binding.countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedCountry = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String name = getIntent().getStringExtra("name");

        binding.aboutContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String age = binding.age.getText().toString();
                String country = binding.country.getText().toString();

                Intent intent = new Intent(Details_Activity.this, SelectLearningLanguage.class);
                intent.putExtra("age",age);
                intent.putExtra("country",country);
                intent.putExtra("selectedItem",selectedItem);
                intent.putExtra("selectedCountry",selectedCountry);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });




    }
}