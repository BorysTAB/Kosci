package com.example.kosci;

import androidx.annotation.RequiresApi;

import android.graphics.Color;
import android.graphics.fonts.FontStyle;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView kostka1, kostka2, kostka3, kostka4, kostka5;
    private TextView sumaOczekTekst, sumaOgolnaTekst, liczbaProbTekst, wylosowaneLiczbyTekst;
    private final int kostkaZnakZapytania = R.drawable.znak_zapytania;
    int sumaOgolna = 0;
    int sumaOczek = 0;
    private  int liczbaProb = 0;
    private ToneGenerator generatorTonow;
    private MediaPlayer odtwarzaczMuzyki;
    private final int[] obrazKostki = {
            R.drawable.kostka1,
            R.drawable.kostka2,
            R.drawable.kostka3,
            R.drawable.kostka4,
            R.drawable.kostka5,
            R.drawable.kostka6
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        kostka1 = findViewById(R.id.kostka1);
        kostka2 = findViewById(R.id.kostka2);
        kostka3 = findViewById(R.id.kostka3);
        kostka4 = findViewById(R.id.kostka4);
        kostka5 = findViewById(R.id.kostka5);

        wylosowaneLiczbyTekst = findViewById(R.id.wylosowane_liczby_tekst);
        sumaOczekTekst = findViewById(R.id.suma_oczek_tekst);
        sumaOgolnaTekst = findViewById(R.id.suma_ogolna_tekst);
        liczbaProbTekst = findViewById(R.id.ilosc_prob_tekst);
        Button przyciskLosowania = findViewById(R.id.przycisk_losowanie);
        Button przyciskResetu = findViewById(R.id.przycisk_reset);

        resetujKostki();

        generatorTonow = new ToneGenerator(AudioManager.STREAM_MUSIC, 80);
        kostka1.setImageResource(kostkaZnakZapytania);
        kostka2.setImageResource(kostkaZnakZapytania);
        kostka3.setImageResource(kostkaZnakZapytania);
        kostka4.setImageResource(kostkaZnakZapytania);
        kostka5.setImageResource(kostkaZnakZapytania);

        przyciskLosowania.setOnClickListener(v -> {
            if(liczbaProb >= 5) {
                resetujKostki();
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                rzucKoscmi();
            }
            if(liczbaProb <= 4) {
                odtworzBeep();
            }
            else {
                odtworzFanfary();
                sumaOgolnaToast();
            }
        });

        przyciskResetu.setOnClickListener(v -> resetujKostki());
    }

    private void rzucKoscmi() {
        Random losowanie = new Random();
        sumaOczek = 0;
        int wynikLosowaniaKostka1 = losowanie.nextInt(6);
        int wynikLosowaniaKostka2 = losowanie.nextInt(6);
        int wynikLosowaniaKostka3 = losowanie.nextInt(6);
        int wynikLosowaniaKostka4 = losowanie.nextInt(6);
        int wynikLosowaniaKostka5 = losowanie.nextInt(6);
        int[] wylosowaneOczka = {wynikLosowaniaKostka1 + 1, wynikLosowaniaKostka2 + 1, wynikLosowaniaKostka3 + 1,
                wynikLosowaniaKostka4 + 1, wynikLosowaniaKostka5 + 1};

        for(int kolejnaKostka : wylosowaneOczka) {
            sumaOczek += kolejnaKostka;
        }

        kostka1.setImageResource(obrazKostki[wynikLosowaniaKostka1]);
        kostka2.setImageResource(obrazKostki[wynikLosowaniaKostka2]);
        kostka3.setImageResource(obrazKostki[wynikLosowaniaKostka3]);
        kostka4.setImageResource(obrazKostki[wynikLosowaniaKostka4]);
        kostka5.setImageResource(obrazKostki[wynikLosowaniaKostka5]);

        wylosowaneLiczbyTekst.setText("Wylosowane liczby: " + wylosowaneOczka[0] + "," +
                wylosowaneOczka[1] + "," + wylosowaneOczka[2] + "," + wylosowaneOczka[3] + "," +
                wylosowaneOczka[4] + ".");

        sumaOczekTekst.setText("Suma oczek: " + sumaOczek + ".");

        liczbaProb++;
        liczbaProbTekst.setText("Ilość prób: " + liczbaProb + ".");

        sumaOgolna += sumaOczek;

        String sumaOgolnaString = Integer.toString(sumaOgolna);
        String tekstSumaOgolna = "Suma ogólna: " + sumaOgolnaString + ".";
        SpannableString sformatowanaSumaOgolna = new SpannableString(tekstSumaOgolna);

        int start = tekstSumaOgolna.indexOf(sumaOgolnaString);
        int end = start + sumaOgolnaString.length();

        sformatowanaSumaOgolna.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        sformatowanaSumaOgolna.setSpan(new RelativeSizeSpan(1.5f), start, end,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        sformatowanaSumaOgolna.setSpan(FontStyle.FONT_WEIGHT_MAX, start, end,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        sumaOgolnaTekst.setText(sformatowanaSumaOgolna);
    }

    private void resetujKostki() {
        kostka1.setImageResource(kostkaZnakZapytania);
        kostka2.setImageResource(kostkaZnakZapytania);
        kostka3.setImageResource(kostkaZnakZapytania);
        kostka4.setImageResource(kostkaZnakZapytania);
        kostka5.setImageResource(kostkaZnakZapytania);
        wylosowaneLiczbyTekst.setText("Wylosowane liczby: 0, 0, 0, 0, 0.");
        sumaOczekTekst.setText("Suma oczek: 0.");
        sumaOgolna = 0;
        sumaOgolnaTekst.setText("Suma ogólna: 0.");
        liczbaProb = 0;
        liczbaProbTekst.setText("Ilość prób: 0.");
    }
    private void odtworzBeep() {
        generatorTonow.startTone(ToneGenerator.TONE_PROP_BEEP, 200);
    }
    private void odtworzFanfary() {
        if(odtwarzaczMuzyki == null) {
            odtwarzaczMuzyki = MediaPlayer.create(this, R.raw.goodresult);
        }
        odtwarzaczMuzyki.setVolume(0.1f, 0.1f);
        odtwarzaczMuzyki.start();
    }

    private void sumaOgolnaToast() {
        Toast.makeText(this, "Koniec gry! Zdobyte punkty: " + sumaOgolna + ".",
                Toast.LENGTH_LONG).show();
    }
}