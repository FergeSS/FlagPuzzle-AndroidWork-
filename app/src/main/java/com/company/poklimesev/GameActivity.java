package com.company.poklimesev;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.company.poklimesev.databinding.ActivityGameBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameActivity extends Property {
    private ActivityGameBinding binding;
    public static boolean active = false;
    int level = -1;
    int color = 0;
    int colorFill = 0;
    int[] currentAnswers = new int[2];
    long colors = 0;
    boolean but1 = false;
    boolean but2 = false;
    int correctAnswersCounter = 0;
    int incorrectAnswersCounter = 0;
    int countOfLives = 3;
    Dialog errorDialog;
    Dialog resultDialog;
    AnimationDrawable animation;

    int[][] correctAnswers = {{R.color.yellow, R.color.green}, {R.color.dark_blue, R.color.red}, {R.color.green, R.color.blue}, {R.color.gray, R.color.yellow},
            {R.color.yellow, R.color.red}, {R.color.yellow, R.color.green}, {R.color.green, R.color.red}, {R.color.yellow, R.color.red}, {R.color.red, R.color.gray},
            {R.color.green, R.color.red}, {R.color.dark_blue, R.color.red}, {R.color.yellow, R.color.green}, {R.color.yellow, R.color.red}, {R.color.red, R.color.green},
            {R.color.red, R.color.green}, {R.color.red, R.color.green}, {R.color.green, R.color.orange}, {R.color.green, R.color.orange}, {R.color.green, R.color.red},
            {R.color.yellow, R.color.green}, {R.color.red, R.color.green}, {R.color.red, R.color.green}, {R.color.yellow, R.color.red}, {R.color.blue, R.color.red}, {R.color.red, R.color.green},
            {R.color.red, R.color.green}, {R.color.red, R.color.green}, {R.color.dark_blue, R.color.red}, {R.color.red, R.color.dark_blue}, {R.color.yellow, R.color.green},
            {R.color.yellow, R.color.dark_blue}, {R.color.green, R.color.red}, {R.color.red, R.color.yellow}, {R.color.red, R.color.green}, {R.color.yellow, R.color.red},
            {R.color.yellow, R.color.green}, {R.color.red, R.color.green}};
    int[] flags2 = {R.drawable.solomon_islands_2, R.drawable.andorra_2, R.drawable.azerbaijan_2, R.drawable.bahamas_2, R.drawable.belgium_2, R.drawable.bolivia_2, R.drawable.cameroon_2, R.drawable.colombia_2, R.drawable.egypt_2, R.drawable.ethiopia_2,
            R.drawable.france_2, R.drawable.gabon_2, R.drawable.germany_2, R.drawable.ghana_2, R.drawable.guinea_2, R.drawable.hungary_2, R.drawable.india_2, R.drawable.ireland_2, R.drawable.italy_2, R.drawable.jamaica_2,
            R.drawable.jordan_2, R.drawable.libya_2, R.drawable.lithuania_2, R.drawable.luxembourg_2, R.drawable.mali_2, R.drawable.mauritius_2, R.drawable.namibia_2, R.drawable.netherlands_2, R.drawable.romania_2, R.drawable.seychelles_2,
            R.drawable.tanzania_2, R.drawable.vanuatu_2, R.drawable.venezuela_2, R.drawable.portugal_2, R.drawable.sicily_2, R.drawable.rwanda_2, R.drawable.senegal_2};
    int[] flags1 = {R.drawable.solomon_islands_1, R.drawable.andorra_1, R.drawable.azerbaijan_1, R.drawable.bahamas_1, R.drawable.belgium_1, R.drawable.bolivia_1, R.drawable.cameroon_1, R.drawable.colombia_1, R.drawable.egypt_1, R.drawable.ethiopia_1,
            R.drawable.france_1, R.drawable.gabon_1, R.drawable.germany_1, R.drawable.ghana_1, R.drawable.guinea_1, R.drawable.hungary_1, R.drawable.india_1, R.drawable.ireland_1, R.drawable.italy_1, R.drawable.jamaica_1,
            R.drawable.jordan_1, R.drawable.libya_1, R.drawable.lithuania_1, R.drawable.luxembourg_1, R.drawable.mali_1, R.drawable.mauritius_1, R.drawable.namibia_1, R.drawable.netherlands_1, R.drawable.romania_1, R.drawable.seychelles_1,
            R.drawable.tanzania_1, R.drawable.vanuatu_1, R.drawable.venezuela_1, R.drawable.portugal_1, R.drawable.sicily_1, R.drawable.rwanda_1, R.drawable.senegal_1};
    int[] flagsBg = {R.drawable.solomon_islands_start, R.drawable.andorra, R.drawable.azerbaijan, R.drawable.bahamas, R.drawable.belgium_full, R.drawable.bolivia, R.drawable.cameroon, R.drawable.colombia, R.drawable.egypt, R.drawable.ethiopia,
            R.drawable.france, R.drawable.gabon, R.drawable.germany, R.drawable.ghana, R.drawable.guinea, R.drawable.hungary, R.drawable.india, R.drawable.ireland, R.drawable.italy, R.drawable.jamaica,
            R.drawable.jordan, R.drawable.libya, R.drawable.lithuania, R.drawable.luxembourg, R.drawable.mali, R.drawable.mauritius, R.drawable.namibia, R.drawable.netherlands, R.drawable.romania, R.drawable.seychelles,
            R.drawable.tanzania, R.drawable.vanuatu, R.drawable.venezuela, R.drawable.portugal, R.drawable.sicily, R.drawable.rwanda, R.drawable.senegal};
    String[] countries = {"Solomon Islands", "Andorra", "Azerbaijan", "Bahamas", "Belgium", "Bolivia", "Cameroon", "Colombia", "Egypt", "Ethiopia",
            "France", "Gabon", "Germany", "Ghana", "Guinea", "Hungary", "India", "Ireland", "Italy", "Jamaica",
            "Jordan", "Libya", "Lithuania", "Luxembourg", "Mali", "Mauritius", "Namibia", "Netherlands", "Romania", "Seychelles",
            "Tanzania", "Vanuatu", "Venezuela", "Portugal", "Sicily", "Rwanda", "Senegal"};
    List<Integer> indexes = new ArrayList<>();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        active = true;

        for (int i = 0; i <= 36; i++) {
            indexes.add(i);
        }
        Collections.shuffle(indexes);

        setNewLevel(false);

        errorDialogSettings();
        resultDialogSettigs();

        binding.confiti.setBackgroundResource(R.drawable.animation);
        animation = (AnimationDrawable)binding.confiti.getBackground();

        binding.linearLayout2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (binding.yellow.isChecked()) {
                    colorFill = getResources().getColor(R.color.yellow, getTheme());
                    color = R.color.yellow;
                }
                if (binding.orange.isChecked()) {
                    colorFill = getResources().getColor(R.color.orange, getTheme());
                    color = R.color.orange;
                }
                if (binding.red.isChecked()) {
                    colorFill = getResources().getColor(R.color.red, getTheme());
                    color = R.color.red;
                }
                if (binding.green.isChecked()) {
                    colorFill = getResources().getColor(R.color.green, getTheme());
                    color = R.color.green;
                }
                if (binding.blue.isChecked()) {
                    colorFill = getResources().getColor(R.color.blue, getTheme());
                    color = R.color.blue;
                }
                if (binding.darkBlue.isChecked()) {
                    colorFill = getResources().getColor(R.color.dark_blue, getTheme());
                    color = R.color.dark_blue;
                }
                if (binding.gray.isChecked()) {
                    colorFill = getResources().getColor(R.color.gray, getTheme());
                    color = R.color.gray;
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.color2.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (but2) {
                            return false;
                        }
                        // Координаты касания
                        float touchX = event.getX();
                        float touchY = event.getY();

                        Bitmap bitmap = drawableToBitmap(binding.color2.getBackground());

                        // Конвертируем координаты в систему координат картинки
                        int imageX = (int) (touchX * bitmap.getWidth() / v.getWidth());
                        int imageY = (int) (touchY * bitmap.getHeight() / v.getHeight());

                        // Проверяем, если координаты попадают в битмап
                        if (imageX >= 0 && imageX < bitmap.getWidth() && imageY >= 0 && imageY < bitmap.getHeight()) {
                            // Получаем цвет пикселя
                            int pixel = bitmap.getPixel(imageX, imageY);


                            // Проверяем альфа-канал (если 0, пиксель прозрачный)
                            if (Color.alpha(pixel) > 0) {

                                // Пиксель непрозрачен — можно выполнять действия

                                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                    // Здесь выполняем действия, например:
                                    Drawable drawable = binding.color2.getBackground();

                                    // Применяем цветовой фильтр к drawable, чтобы покрасить его в определенный цвет
                                    if (drawable != null && color != 0) {
                                        but2 = true;
                                        drawable.setColorFilter(new PorterDuffColorFilter(colorFill, PorterDuff.Mode.SRC_IN));
                                        currentAnswers[1] = color;
                                        ++colors;
                                        if (colors == 2) {
                                            checkAnswers();
                                        }
                                    }
                                }

                                return true;  // Сообщаем, что касание обработано
                            }
                        }
                        return false; // Прозрачная область — касание не обработано
                    }
                });

                binding.color1.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (but1) {
                            return binding.color2.dispatchTouchEvent(event);
                        }
                        // Координаты касания
                        float touchX = event.getX();
                        float touchY = event.getY();

                        Bitmap bitmap = drawableToBitmap(binding.color1.getBackground());

                        // Конвертируем координаты в систему координат картинки
                        int imageX = (int) (touchX * bitmap.getWidth() / v.getWidth());
                        int imageY = (int) (touchY * bitmap.getHeight() / v.getHeight());

                        // Проверяем, если координаты попадают в битмап
                        if (imageX >= 0 && imageX < bitmap.getWidth() && imageY >= 0 && imageY < bitmap.getHeight()) {
                            // Получаем цвет пикселя
                            int pixel = bitmap.getPixel(imageX, imageY);


                            // Проверяем альфа-канал (если 0, пиксель прозрачный)
                            if (Color.alpha(pixel) > 0) {

                                // Пиксель непрозрачен — можно выполнять действия

                                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                                    // Здесь выполняем действия, например:
                                    Drawable drawable = binding.color1.getBackground();

                                    // Применяем цветовой фильтр к drawable, чтобы покрасить его в определенный цвет
                                    if (drawable != null && color != 0) {
                                        but1 = true;
                                        drawable.setColorFilter(new PorterDuffColorFilter(colorFill, PorterDuff.Mode.SRC_IN));
                                        currentAnswers[0] = color;
                                        ++colors;
                                        if (colors == 2) {
                                            checkAnswers();
                                        }
                                    }
                                }
                                return true;  // Сообщаем, что касание обработано
                            }
                        }
                        return binding.color2.dispatchTouchEvent(event);
                    }
                });
            }
        }, 100);
    }

    private void errorDialogSettings() {
        errorDialog = new Dialog(GameActivity.this);

        errorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        errorDialog.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        errorDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        errorDialog.setCanceledOnTouchOutside(false);
        errorDialog.setCancelable(false);
        WindowManager.LayoutParams wlp = errorDialog.getWindow().getAttributes();
        wlp.dimAmount = 0.7f;
        errorDialog.getWindow().setAttributes(wlp);
        errorDialog.setContentView(R.layout.error_dialog);
    }

    public void resultDialogSettigs() {
        resultDialog = new Dialog(GameActivity.this);

        resultDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        resultDialog.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        resultDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        resultDialog.setCanceledOnTouchOutside(false);
        resultDialog.setCancelable(false);
        WindowManager.LayoutParams wlp = resultDialog.getWindow().getAttributes();
        wlp.dimAmount = 0.7f;
        resultDialog.getWindow().setAttributes(wlp);
        resultDialog.setContentView(R.layout.result_dialog);
    }

    private void checkAnswers() {
        colors = 0;
        if (currentAnswers[0] == correctAnswers[indexes.get(level)][0] && currentAnswers[1] == correctAnswers[indexes.get(level)][1]) {
            correctAnswersCounter++;
            animation.start();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setNewLevel(false);
                    animation.stop();
                }
            }, 2000);
        }
        else {
            --countOfLives;
            incorrectAnswersCounter++;
            if (countOfLives == 2) {
                binding.life3.setAlpha(0.0f);
                errorDialog.show();
            }
            else if (countOfLives == 1) {
                binding.life2.setAlpha(0.0f);
                errorDialog.show();
            }
            else if (countOfLives == 0) {
                binding.life1.setAlpha(0.0f);
                ((TextView)(resultDialog.findViewById(R.id.correct))).setText(String.valueOf(correctAnswersCounter));
                ((TextView)(resultDialog.findViewById(R.id.incorrect))).setText(String.valueOf(incorrectAnswersCounter));
                resultDialog.show();
            }
        }
        but1 = false;
        but2 = false;
    }
    private void setNewLevel(boolean isSkip) {
        binding.helpFlag.setAlpha(0.0f);
        binding.color1.getBackground().clearColorFilter();
        binding.color2.getBackground().clearColorFilter();

        ++level;
        if (level >= 20) {
            ((TextView)(resultDialog.findViewById(R.id.correct))).setText(String.valueOf(correctAnswersCounter));
            ((TextView)(resultDialog.findViewById(R.id.incorrect))).setText(String.valueOf(incorrectAnswersCounter));
            resultDialog.show();
            return;
        }
        binding.counter.setText((level + 1) + "/20");
        binding.flag.setImageResource(flagsBg[indexes.get(level)]);
        binding.color1.setBackgroundDrawable(getDrawable(flags1[indexes.get(level)]));
        binding.color2.setBackgroundDrawable(getDrawable(flags2[indexes.get(level)]));
        binding.country.setText(countries[indexes.get(level)]);
        if (indexes.get(level) == 5) {
            binding.helpFlag.setAlpha(1.0f);
            binding.helpFlag.setImageResource(R.drawable.bolivia_3);
        }
        if (indexes.get(level) == 6) {
            binding.helpFlag.setAlpha(1.0f);
            binding.helpFlag.setImageResource(R.drawable.cameroon_3);
        }
        if (indexes.get(level) == 9) {
            binding.helpFlag.setAlpha(1.0f);
            binding.helpFlag.setImageResource(R.drawable.ethiopia_3);
        }
        if (indexes.get(level) == 20) {
            binding.helpFlag.setAlpha(1.0f);
            binding.helpFlag.setImageResource(R.drawable.jordan_3);
        }
        if (indexes.get(level) == 30) {
            binding.helpFlag.setAlpha(1.0f);
            binding.helpFlag.setImageResource(R.drawable.tanzania_3);
        }
        if (indexes.get(level) == 33) {
            binding.helpFlag.setAlpha(1.0f);
            binding.helpFlag.setImageResource(R.drawable.portugal_3);
        }
        if (indexes.get(level) == 34) {
            binding.helpFlag.setAlpha(1.0f);
            binding.helpFlag.setImageResource(R.drawable.sicily_3);
        }

    }

    public void skip(View v) {
        Settings.action(GameActivity.this);
        colors = 0;
        setNewLevel(true);
    }

    public void home(View v) {
        Settings.action(GameActivity.this);
        finish();
    }

    public void replay(View v) {
        Settings.action(GameActivity.this);
        resultDialog.dismiss();
        level = -1;
        countOfLives = 3;
        binding.life1.setAlpha(1.0f);
        binding.life2.setAlpha(1.0f);
        binding.life3.setAlpha(1.0f);
        correctAnswersCounter = 0;
        incorrectAnswersCounter = 0;
        Collections.shuffle(indexes);
        setNewLevel(false);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        active = false;
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        // Создаем Bitmap с размерами Drawable
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public void closeError(View v) {
        binding.color1.getBackground().clearColorFilter();
        binding.color2.getBackground().clearColorFilter();
        errorDialog.dismiss();
    }

}
