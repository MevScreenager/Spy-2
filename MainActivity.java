package com.example.spy;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    @ColorInt
    private final int colorYellow = 0xFFDA9305;
    @ColorInt
    private final int colorTheme = 0xFFFDD007;
    @ColorInt
    private int cities = colorYellow;
    @ColorInt
    private int places = colorYellow;
    @ColorInt
    private int animals = colorYellow;
    @ColorInt
    private int eat = colorYellow;
    @ColorInt
    private int clothe = colorYellow;
    @ColorInt
    private int myWords = 0xFF000000;

    private int count;
    private int player;
    private int spy;
    private int countRoles;
    private int time;
    private boolean secondClick = false;
    private boolean mIsRunning = false;
    private boolean setsChecked = true;
    private boolean wordChecked = false;
    private Time pTimer;
    private ArrayList<String> wordInSetsList;
    private String textFromActivity_word;
    private Typeface tf;
    private Integer index[];
    private String roles[];
    private CheckBox sets;
    private CheckBox word;
    private TextView roleT;
    private TextView playerT;
    private TextView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        renewel();
    }

    private void renewel(){
        setContentView(R.layout.activity_main);

        sets = findViewById(R.id.act_mainCheckBoxSets);
        word = findViewById(R.id.act_mainCheckBoxWord);

        if (!secondClick){
            sets.setChecked(true);
            word.setChecked(false);
            secondClick = true;
        }
        else {
            sets.setChecked(setsChecked);
            word.setChecked(wordChecked);
        }

        this.sets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sets.isChecked()) offWord();
                if (!sets.isChecked()) onSats();
                setsChecked = true;
                wordChecked = false;
            }
        });
        this.word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (word.isChecked()) offSets();
                if (!word.isChecked()) onWord();
                setsChecked = false;
                wordChecked = true;
            }
        });

    }

    public void onClickStartSets(View v) {      // Код который будет выполняться при нажатии на "наборы"
        setContentView(R.layout.activity_sets);

        TextView cities = findViewById(R.id.City);
        TextView places = findViewById(R.id.Places);
        TextView animals = findViewById(R.id.Animals);
        TextView eat = findViewById(R.id.Eat);
        TextView clothe = findViewById(R.id.Clothes);
        TextView myWords = findViewById(R.id.MyWords);

        cities.setTextColor(this.cities);
        places.setTextColor(this.places);
        animals.setTextColor(this.animals);
        eat.setTextColor(this.eat);
        clothe.setTextColor(this.clothe);
        myWords.setTextColor(this.myWords);
    }

    public void onClickStartWord(View v) {      // Код который будет выполняться при нажатии на "слово"
        setContentView(R.layout.activity_word);
    }

    public void onClickRules(View v){
        setContentView(R.layout.activity_rules);
        TextView tv = findViewById(R.id.rules);
        tv.setText(
                "1. В игре участвуют местные и шпион. Передавайте телефон, чтобы узнать какая роль вам выпала. Все игроки, кроме Шпиона, будут знать локацию.\n" +
                "2. Ваша задача обмениваться вопросами о данной локации. Вопросы и ответы должны быть не прямыми, поскольку шпион, который не знает локацию может её угадать и выиграть. Если игроки находят шпиона, то они побеждают. Прислушивайтесь к ответам других игроков.\n" +
                "3. Если подозреваешь кого-то, скажи - Я знаю кто шпион. Остальные игроки должны указать на того, кто по их мнению шпион.\n" +
                "4. Если все игроки сошлись на одном человеке - игрок должен раскрыть свою роль. Если это шпион, то местные победили. Если местный, тогда шпион выиграл. Если указали разных людей - играйте дальше.\n" +
                "5. Если шпион угадал что за локация, то он может её назвать. Если отгадал, победа его. Если ошибся - местные выиграли.");
    }

    public void onClickStartBack(View v) {      // Код который будет выполняться при нажатии на ImageButton "назад"
        if (!mIsRunning) renewel();
        else{
            pTimer.stop();
            mIsRunning = false;
            renewel();
        }
    }

    public void onClickStartBack_2(View v) {      // Код который будет выполняться при нажатии на ImageButton "назад" в activity_word
        EditText editTextInActivity_Word = findViewById(R.id.editTextInActivity_Word);
        textFromActivity_word = editTextInActivity_Word.getText().toString();
        renewel();
    }

    public void selected(View v){       // Меняет цвет текста в наборах
        TextView textView = findViewById(v.getId());
        if (textView.getCurrentTextColor() == colorTheme)      // не выбран
            textView.setTextColor(colorYellow);        // выбран
        else if (textView.getCurrentTextColor() != 0xFF000000)
            textView.setTextColor(colorTheme);
        switch (textView.getId()){
            case R.id.City: this.cities = textView.getCurrentTextColor(); break;
            case R.id.Places: this.places = textView.getCurrentTextColor(); break;
            case R.id.Animals: this.animals = textView.getCurrentTextColor(); break;
            case R.id.Eat: this.eat = textView.getCurrentTextColor(); break;
            case R.id.Clothes: this.clothe = textView.getCurrentTextColor(); break;
//            case R.id.MyWords: this.myWords = textView.getCurrentTextColor(); break;
        }
    }

    public void offWord(){
        this.word.setChecked(false);
    }

    public void offSets(){
        this.sets.setChecked(false);
    }

    public void onWord(){
        this.word.setChecked(true);
    }

    public void onSats(){
        this.sets.setChecked(true);
    }

    public void play(View v){       // При нажатии на кнопку играть
        EditText player = findViewById(R.id.act_mainEditTextPlayer);
        EditText spy = findViewById(R.id.act_mainEditTextSpy);
        EditText time = findViewById(R.id.act_mainEditTextTimer);

        setContentView(R.layout.activity_play);

        this.player = Integer.parseInt(player.getText().toString());
        this.spy = Integer.parseInt(spy.getText().toString());
        this.time = Integer.parseInt(time.getText().toString());
        this.playerT = findViewById(R.id.act_PlayPlayer);
        this.roleT = findViewById(R.id.act_playRole);
        this.roles = new String[this.player];
        this.index = new Integer[this.player];
        this.countRoles = 2;
        this.count = 1;

        checkingFields();
        addWordInSetsList();
        collectRoles();
    }

    private void checkingFields(){
        if (wordChecked)
            if (textFromActivity_word == null || textFromActivity_word.equals("")){
                    setContentView(R.layout.activity_word);
                    Toast toast = Toast.makeText(getApplicationContext(),"Вы не написали слово. ", Toast.LENGTH_SHORT);
                    toast.show();
                }
        if (setsChecked)
            if (cities == colorTheme && places == colorTheme &&
                    animals == colorTheme && eat == colorTheme && clothe == colorTheme && myWords == colorTheme){
                cities = colorYellow;
                places = colorYellow;
                animals = colorYellow;
                eat = colorYellow;
                clothe = colorYellow;
            }
    }

    private void addWordInSetsList(){
        Sets sets = new Sets();
        wordInSetsList = new ArrayList<>();
        int[] colorArray = {cities, places, animals, eat, clothe, myWords};
        for (int i = 0; i < colorArray.length; i++)
            if (colorArray[i] == colorYellow) {
                switch (i){
                    case 0:
                        String[] c = sets.getCities();
                        wordInSetsList.addAll(Arrays.asList(c));
                        break;
                    case 1:
                        String[] p = sets.getPlaces();
                        wordInSetsList.addAll(Arrays.asList(p));
                        break;
                    case 2:
                        String[] a = sets.getAnimals();
                        wordInSetsList.addAll(Arrays.asList(a));
                        break;
                    case 3:
                        String[] e = sets.getEat();
                        wordInSetsList.addAll(Arrays.asList(e));
                        break;
                    case 4:
                        String[] cl = sets.getClothe();
                        wordInSetsList.addAll(Arrays.asList(cl));
                        break;
                    case 5:
                        String[] m = sets.getMyWords();
                        wordInSetsList.addAll(Arrays.asList(m));
                        break;
                }
            }
    }

    public void next(View v){       // При нажатии на роль
        if (count < player + 1) {
            playerT.setText("Игрок " + count);
            if (countRoles % 2 != 0) {
                countRoles++;
                roleT.setText("Нажмите, чтобы узнать свою роль");
            } else {
                roleT.setText(roles[index[count - 1]]);
                countRoles++;
                count++;
            }
        } else {
            setContentView(R.layout.activity_timer);
            timer = findViewById(R.id.act_timerTimer);
            tf = Typeface.createFromAsset(getAssets(), "Ds-Digital.ttf");
            timer.setTypeface(tf);
            pTimer = new Time(timer, time, this);
            pTimer.onStartButtonClick();
            mIsRunning = pTimer.getMIsRunning();
        }
    }



    private void collectRoles(){        // Роли
        String secretWord;
        if (setsChecked) secretWord = randomWordInSets();
        else secretWord = textFromActivity_word;
        for (int i = 0; i < player; i++){
            if (i < spy) roles[i] = "Ты шпион";
            else roles[i] = secretWord;
        }
        rndInteger();
    }

    private String randomWordInSets(){
        int n = (int) (Math.floor(Math.random() * wordInSetsList.size()));
        return wordInSetsList.get(n);
    }

    private void rndInteger(){
        int min = 0;
        int max = player;
        int count = 0;
        boolean flag = true;
        int n;
        while (flag) {
            if (count == 0) {
                n = (int) (Math.floor(Math.random() * (max - min)) + min);
                index[count] = n;
                count ++;
            } else {
                n = (int) (Math.floor(Math.random() * (max - min)) + min);
                if (!nInIndex(index, n)) {
                    index[count] = n;
                    count++;
                }
                if (count == player) flag = false;
            }
        }
    }

    private boolean nInIndex(Integer[] index, int n){
        for (Integer integer : index)
            if (integer != null)
                if (integer == n) return true;
        return false;
    }

//    public PendingIntent getPendingIntent(){
//        Intent intent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//                intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        return pendingIntent;
//    }

    @Override
    public void onBackPressed() {
        if (mIsRunning) {
            pTimer.stop();
            mIsRunning = false;
        }
        new AlertDialog.Builder(this)
                .setMessage("Вы действительно хотите выйти из игры?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                }).setNegativeButton("Нет", null).show();
    }
}


