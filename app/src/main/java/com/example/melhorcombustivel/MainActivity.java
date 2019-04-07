package com.example.melhorcombustivel;

import android.graphics.drawable.Drawable;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private double percent = 1000.00;
    private TextView gasolinaTextView;
    private TextView valorGasolinaTextView;
    private TextView alcoolTextView;
    private TextView valorAlcoolTextView;
    private TextView melhorCombustivelTextView;
    private ImageView imagem;
    private double gasolina = 0.0;
    private double alcool = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gasolinaTextView = (TextView) findViewById(R.id.gasolinaTextView);
        valorGasolinaTextView = (TextView) findViewById(R.id.valorGasolinaTextView);

        alcoolTextView = (TextView) findViewById(R.id.alcoolTextView);
        valorAlcoolTextView = (TextView) findViewById(R.id.valorAlcoolTextView);

        melhorCombustivelTextView = (TextView) findViewById(R.id.melhorCombustivelTextView);

        SeekBar gasolinaSeekBar = (SeekBar) findViewById(R.id.gasolinaSeekBar);
        gasolinaSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        SeekBar alcoolSeekBar = (SeekBar) findViewById(R.id.alcoolSeekBar);
        alcoolSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        imagem = (ImageView) findViewById(R.id.melhorCombustivelImageView);

        //Inicializando os campos de mostrar valores
        valorAlcoolTextView.setText(currencyFormat.format(alcool));
        valorGasolinaTextView.setText(currencyFormat.format(gasolina));

    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            float novoValor = ((float)progress /1000);

            String falcool = valorAlcoolTextView.getText().toString();
            String fgasolina= valorGasolinaTextView.getText().toString();

            traduzirValores(fgasolina, falcool);

            Drawable drawableGasolina = getResources().getDrawable(R.drawable.gasolina);
            Drawable drawableAlcool = getResources().getDrawable(R.drawable.alcool);
            String stringGasolina = getResources().getString(R.string.gasolina_txt);
            String stringAlcool = getResources().getString(R.string.alcool_txt);

            calcularValores(stringGasolina, drawableGasolina, stringAlcool, drawableAlcool);

            preencherValores(novoValor, seekBar);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void preencherValores (float novoValor, SeekBar seekBar){
        if (seekBar == findViewById(R.id.gasolinaSeekBar)){
            valorGasolinaTextView.setText(currencyFormat.format(novoValor));

        }
        if (seekBar == findViewById(R.id.alcoolSeekBar)){
            valorAlcoolTextView.setText(currencyFormat.format(novoValor));
        }
    }

    private void traduzirValores (String valor1, String valor2){
        try {
//            Método "set" para gasolina e álcool
            gasolina = Double.parseDouble(currencyFormat.parse(valor1).toString());
            alcool = Double.parseDouble(currencyFormat.parse(valor2).toString());
        }
        catch (ParseException ignored){}
    }

    private void calcularValores(String stringGasolina, Drawable drawableGasolina, String stringAlcool, Drawable drawableAlcool){
        if (((alcool/ gasolina)>=0.7)){
            melhorCombustivelTextView.setText(stringGasolina);
            imagem.setImageDrawable(drawableGasolina);
        }
        else {
            melhorCombustivelTextView.setText(stringAlcool);
            imagem.setImageDrawable(drawableAlcool);
        }
    }
}
