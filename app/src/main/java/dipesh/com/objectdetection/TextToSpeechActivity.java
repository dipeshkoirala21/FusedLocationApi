package com.tensorflow.online_shopping_demo;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;

//import org.tensorflow.yolo.model.Recognition;

import java.util.List;
import java.util.Locale;

//import static org.tensorflow.yolo.Config.LOGGING_TAG;


public abstract class TextToSpeechActivity extends CameraActivity implements TextToSpeech.OnInitListener {
    private TextToSpeech textToSpeech;
    private String lastRecognizedClass = "";

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
           // int result = textToSpeech.setLanguage(new Locale("tr","TR"));
           int result = textToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                //Log.e(Tag,"Text to speech error: This Language is not supported");
            }
        } else {
            Log.e("message","Text to speech: Initilization Failed!");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textToSpeech = new TextToSpeech(this, this);
    }

    protected void speak(List<Classifier.Recognition> results) {
        if (!(results.isEmpty() || lastRecognizedClass.equals(results.get(0).getTitle()))) {
            lastRecognizedClass = results.get(0).getTitle();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textToSpeech.speak(lastRecognizedClass, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                textToSpeech.speak(lastRecognizedClass, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

}
