package com.level1.pkg.shout_it_out;

        import java.util.ArrayList;
        import java.util.Random;

        import android.content.Context;
        import android.os.Handler;
        import android.speech.RecognitionListener;
        import android.speech.RecognizerIntent;
        import android.speech.SpeechRecognizer;
        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.ProgressBar;
        import android.widget.TextView;

        import android.media.MediaPlayer;


public class VoiceRecognitionActivity extends Activity implements
        RecognitionListener {

    MediaPlayer bgMusic;
    MediaPlayer fxMusic;


    final Random rnd = new Random();
    int randomNum = 0;

    private TextView returnedText;
    //private ToggleButton toggleButton;
    private ProgressBar progressBar;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        //Drawable Animation one frame at a time works!
        //https://developer.android.com/reference/android/graphics/drawable/AnimationDrawable.html
        // Load the ImageView that will host the animation and
        // set its background to our AnimationDrawable XML resource.
        // also add a rocket_thrust.xml to you drawable folder.
//        ImageView img = (ImageView)findViewById(R.id.my_animation);
//        img.setBackgroundResource(R.drawable.rocket_thrust);
//
//        // Get the background, which has been compiled to an AnimationDrawable object.
//        AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
//
//        // Start the animation (looped playback by default).
//        frameAnimation.start();




        //new! random
        final ImageView img = (ImageView) findViewById(R.id.imgRandom);
        // I have images named img_0 to img_2, so...

        randomNum = rnd.nextInt(8);
        final String str = "img_" + randomNum;

        img.setImageDrawable
                (
                        getResources().getDrawable(getResourceID(str, "drawable",
                                getApplicationContext()))

                );

        returnedText = (TextView) findViewById(R.id.textView1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        //toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);

        progressBar.setVisibility(View.INVISIBLE);
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);

        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        speech.startListening(recognizerIntent);
        //toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {


        //background music
//        bgMusic = MediaPlayer.create(this, R.raw.background);
//        bgMusic.setLooping(false);
//        bgMusic.start();
        //bgMusic.stop();

    }


//new!! image getter
    protected final static int getResourceID
            (final String resName, final String resType, final Context ctx) {
        final int ResourceID =
                ctx.getResources().getIdentifier(resName, resType,
                        ctx.getApplicationInfo().packageName);
        if (ResourceID == 0) {
            throw new IllegalArgumentException
                    (
                            "No resource string found with name " + resName
                    );
        } else {
            return ResourceID;
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        //onRestart();
        //recreate();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (speech != null) {
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
            //onDestroy();
            //recreate();
        }
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
        progressBar.setIndeterminate(false);
        progressBar.setMax(10);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
        progressBar.setIndeterminate(true);
        //toggleButton.setChecked(false);
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
        returnedText.setText(errorMessage);
        //toggleButton.setChecked(false);
        recreate();
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }


//  One of the most important methods to build the Android speech Recognition App
//  Without Pop Up, is onResults(Bundle results) method. Here in this method speech
//  recognition result is passed as an argument in the form of an ArrayList. After
//  this one can process the result, as required. In my case I just displayed it in a TextView.

//Delays recreate activity so the message stays on screen for a little while.
    public void delay(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                recreate();
            }//length of delay in milliseconds till next image
        }, 2700);
    }


    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");

        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        //Prints results to the tablet
        for (String result : matches)
            text += result + "\n";


        String letter = "";

        switch (randomNum){
            case 0: letter = "zebra";
                break;
            case 1: letter = "Apple";
                break;
            case 2: letter = "bat";
                break;
            case 3: letter = "cat";
                break;
            case 4: letter = "whale";
                break;
            case 5: letter = "bird";
                break;
            case 6: letter = "fish";
                break;
            case 7: letter = "frog";
                break;
        }

//      String yes = "You Got it :D " + randomNum;
        String yes = "You Got it \n\t\t\t:D ";
        String no = "Try Again \n\t\t\t:/ ";

        if(matches.contains(letter)){
            System.out.println("You Got it :D");
            returnedText.setText(yes);
            fxMusic = MediaPlayer.create(VoiceRecognitionActivity.this, R.raw.yes);
            fxMusic.setLooping(false);
            fxMusic.start();
            delay();
        }else{
            System.out.println("Wrong Input \n:/");
            returnedText.setText(no);
            fxMusic = MediaPlayer.create(VoiceRecognitionActivity.this, R.raw.no);
            fxMusic.setLooping(false);
            fxMusic.start();
            delay();
        }
        //Original. This puts all interpreted words to the screen
        //returnedText.setText(text);
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
        progressBar.setProgress((int) rmsdB);
    }

    //just killed
    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

}
