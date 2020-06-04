package com.example.textaudio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    private TextView textView;
    private EditText editText;
    private SeekBar seekBarVolume;
    private SeekBar seekBarSpeed;
    private Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button = findViewById(R.id.button);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.ENGLISH);
                    if (status == TextToSpeech.LANG_MISSING_DATA || status == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("Text to Speech", "Language not supported");

                    } else {
                        button.setEnabled(true);
                    }
                }
            }
        });


        editText = findViewById(R.id.editText);
        seekBarSpeed = findViewById(R.id.seekBarSpeed);
        seekBarVolume = findViewById(R.id.seekBarVolume);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }
    private void speak(){

        String text=editText.getText().toString();
        float volume=(float) seekBarVolume.getProgress()/60;
        if(volume<0.1) volume=0.1f;
        float speed=(float) seekBarSpeed.getProgress()/60;
        if(speed <0.1) speed=0.1f;

        textToSpeech.setPitch(volume);
        textToSpeech.setSpeechRate(speed);

        textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);



    }

    @Override
    protected void onDestroy() {
        if(textToSpeech!= null)
       textToSpeech.stop();
        textToSpeech.shutdown();
        super.onDestroy();
    }
}

