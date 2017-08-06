package com.example.win10.phonecalllistener;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.support.annotation.IntDef;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

public class MyService extends Service implements TextToSpeech.OnInitListener {
    private static TextToSpeech mtts;
    private static String savedNumber;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyLog", "Service started successfully!");
        savedNumber = intent.getStringExtra("savedNumber");
        if (isNumber(savedNumber))
            savedNumber = savedNumber.replace("", "-").trim();
        mtts = new TextToSpeech(getApplicationContext(), this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (mtts != null) {
            mtts.stop();
            mtts.shutdown();
        }

    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mtts.setLanguage(Locale.getDefault());
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("MyLog", "This Language is not supported");
            } else {
                speakOut();
            }

        } else {
            Log.e("MyLog", "Initilization Failed!");
        }
    }

    private void speakOut() {
        Log.d("MyLog", "speakOut! savedNumber = " + savedNumber);
        mtts.speak(savedNumber, TextToSpeech.QUEUE_FLUSH, null);
    }

    private boolean isNumber(String word) {
        boolean isNumber = false;
        try {
            Integer.parseInt(word);
            isNumber = true;
        } catch (NumberFormatException e) {
            isNumber = false;
        }
        return isNumber;
    }
}
