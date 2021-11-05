package com.example.karina2048;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.widget.TextViewCompat;

public class MainActivity extends AppCompatActivity {

    TextView[][] tablero;
    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tablero = generarTablero();
        generarRandomInicial();

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
    }

    public TextView[][] generarTablero() {
        TextView[][] matriz = new TextView[4][4];

        final int N = 4;

        LinearLayout rLayout = findViewById(R.id.tabla);

        LinearLayout.LayoutParams style = new LinearLayout.LayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
        style.setMargins(17, 17, 17, 17);
        style.height = 200;
        style.width = 200;

        for (int i = 0; i < N; i++) {
            LinearLayout lLayout = new LinearLayout(this);
            lLayout.setHorizontalGravity(1);

            for (int j = 0; j < N; j++) {
                TextView txtView = new TextView(this);
                txtView.setLayoutParams(style);
                TextViewCompat.setAutoSizeTextTypeWithDefaults(txtView, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                txtView.setGravity(Gravity.CENTER);
                txtView.setPadding(15, 15, 15, 15);
                txtView.setTextColor(Color.parseColor("#ffffff"));
                txtView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                txtView.setTypeface(null, Typeface.BOLD_ITALIC);
                txtView.setId(Integer.parseInt("" + i + j));

                lLayout.addView(txtView);
                matriz[i][j] = txtView;
            }
            rLayout.addView(lLayout);
        }

        return matriz;
    }

    private void generarRandomInicial() {

        int min = 0;
        int max = 3;

        int x = (int) Math.floor(Math.random() * (max - min + 1) + min);
        int y = (int) Math.floor(Math.random() * (max - min + 1) + min);
        int x2 = (int) Math.floor(Math.random() * (max - min + 1) + min);
        int y2 = (int) Math.floor(Math.random() * (max - min + 1) + min);
        while (x == x2 && y == y2) {
            x2 = (int) Math.floor(Math.random() * (max - min + 1) + min);
            y2 = (int) Math.floor(Math.random() * (max - min + 1) + min);
        }
        tablero[x][y].setText("2");
        tablero[x2][y2].setText("2");
        actualizarFichas();
    }

    private void nuevaRonda() {

        boolean[][] libre = new boolean[4][4];
        boolean completa = true;
        boolean win = false;

        for (int i = 0; i < tablero.length && !win; i++) {
            for (int j = 0; j < tablero[i].length && !win; j++) {
                if (tablero[i][j].getText().toString().equals("")) {
                    libre[i][j] = true;
                    completa = false;
                } else if (tablero[i][j].getText().toString().equals("2048")) {
                    win = true;
                    Toast.makeText(this, "Has ganado!", Toast.LENGTH_LONG).show();
                    reiniciarTablero();
                } else {
                    libre[i][j] = false;
                }
            }
        }

        if (!completa && !win) {
            int min = 0;
            int max = 3;
            int x = (int) Math.floor(Math.random() * (max - min + 1) + min);
            int y = (int) Math.floor(Math.random() * (max - min + 1) + min);

            while (!libre[x][y]) {
                x = (int) Math.floor(Math.random() * (max - min + 1) + min);
                y = (int) Math.floor(Math.random() * (max - min + 1) + min);
            }

            String valor = "2";
            int rand = (int) Math.floor(Math.random() * (100 + 1) + 0);
            if (rand > 75) {
                valor = "4";
            }

            tablero[x][y].setText(valor);
        }
        actualizarFichas();
    }

    private void reiniciarTablero() {
        for (TextView[] textViews : tablero) {
            for (int j = 0; j < tablero.length; j++) {
                textViews[j].setText("");
            }
        }
        generarRandomInicial();
    }

    /**
     * Actualiza la forma en la que se ve la tabla
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private void actualizarFichas() {
        for (TextView[] textViews : tablero) {
            for (int j = 0; j < tablero.length; j++) {
                TextView tile = textViews[j];
                switch (tile.getText().toString()) {
                    case "2":
                        tile.setBackground(getDrawable(R.drawable.tile_0002));
                        break;
                    case "4":
                        tile.setBackground(getDrawable(R.drawable.tile_0004));
                        break;
                    case "8":
                        tile.setBackground(getDrawable(R.drawable.tile_0008));
                        break;
                    case "16":
                        tile.setBackground(getDrawable(R.drawable.tile_0016));
                        break;
                    case "32":
                        tile.setBackground(getDrawable(R.drawable.tile_0032));
                        break;
                    case "64":
                        tile.setBackground(getDrawable(R.drawable.tile_0064));
                        break;
                    case "128":
                        tile.setBackground(getDrawable(R.drawable.tile_0128));
                        break;
                    case "256":
                        tile.setBackground(getDrawable(R.drawable.tile_0256));
                        break;
                    case "512":
                        tile.setBackground(getDrawable(R.drawable.tile_0512));
                        break;
                    case "1024":
                        tile.setBackground(getDrawable(R.drawable.tile_1024));
                        break;
                    case "2048":
                        tile.setBackground(getDrawable(R.drawable.tile_2048));
                        break;
                    default:
                        tile.setBackground(getDrawable(R.drawable.tile_0000));
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private void moverDerecha() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 3; j >= 0; j--) {

                int ficha = tablero[i][j].getId();
                int fichaValor = getFichaValor(tablero[i][j]);
                //Log.i("Ficha", ficha + " Valor actual : " + fichaValor);
                System.out.print(fichaValor + ", ");
                if (fichaValor != 0) {
                    for (int k = 3; k >= j; k--) {
                        if (comprobarMovimiento(i, k, ficha, fichaValor, tablero[i][j])) break;
                    }
                }
            }
            System.out.println();
        }
        nuevaRonda();
    }

    private void moverIzquierda() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {

                int ficha = tablero[i][j].getId();
                int fichaValor = getFichaValor(tablero[i][j]);
                //Log.i("Ficha", ficha + " Valor actual : " + fichaValor);
                System.out.print(fichaValor + ", ");

                if (fichaValor != 0) {
                    for (int k = 0; k <= j; k++) {
                        if (comprobarMovimiento(i, k, ficha, fichaValor, tablero[i][j])) break;
                    }
                }
            }
            System.out.println();
        }
        nuevaRonda();
    }

    private void moverAbajo() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {

                int ficha = tablero[j][i].getId();
                int fichaValor = getFichaValor(tablero[j][i]);
                //Log.i("Ficha", ficha + " Valor actual : " + fichaValor);
                System.out.print(fichaValor + ", ");

                if (fichaValor != 0) {
                    for (int k = 0; k <= j; k++) {
                        if (comprobarMovimiento(k, i, ficha, fichaValor, tablero[j][i])) break;
                    }
                }
            }
            System.out.println();
        }
        nuevaRonda();
    }

    private void moverArriba() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 3; j >= 0; j--) {

                int ficha = tablero[j][i].getId();
                int fichaValor = getFichaValor(tablero[j][i]);
                //Log.i("Ficha", ficha + " Valor actual : " + fichaValor);
                System.out.print(fichaValor + ", ");

                if (fichaValor != 0) {
                    for (int k = 3; k >= j; k--) {
                        if (comprobarMovimiento(k, i, ficha, fichaValor, tablero[j][i])) break;
                    }
                }
            }
            System.out.println();
        }
        nuevaRonda();
    }

    @SuppressLint("SetTextI18n")
    private boolean comprobarMovimiento(int x, int y, int fichaId, int fichaValor, TextView ficha) {
        int fichaObjetivo = tablero[x][y].getId();
        if (fichaId != fichaObjetivo) {

            int fichaObjetivoValor = getFichaValor(tablero[x][y]);

            if (fichaObjetivoValor == 0) {
                tablero[x][y].setText(ficha.getText());
                ficha.setText("");
                return true;
            } else if (fichaValor == fichaObjetivoValor) {
                tablero[x][y].setText(Integer.toString(fichaValor * 2));
                ficha.setText("");
                return true;
            }
        }
        return false;
    }

    private int getFichaValor(TextView textView) {
        int fichaValor;
        try {
            fichaValor = Integer.parseInt((String) textView.getText());
        } catch (Exception e) {
            fichaValor = 0;
        }
        return fichaValor;
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestos";

        @Override
        public boolean onDown(MotionEvent event) {
            //Log.d(DEBUG_TAG, "onDown: " + event.toString());
            Log.d(DEBUG_TAG, "x: " + event.getX() + " y: " + event.getY());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            //Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
            Log.d(DEBUG_TAG, "x: " + event1.getX() + " y: " + event1.getY());
            Log.d(DEBUG_TAG, "x: " + event2.getX() + " y: " + event2.getY());


            float DifX = Math.abs(event1.getX() - event2.getX());
            float DifY = Math.abs(event1.getY() - event2.getY());
            Log.d(DEBUG_TAG, DifX + " " + DifY);
            final int MIN_DIST = 250;

            if (DifX > MIN_DIST && DifY > MIN_DIST || DifX < MIN_DIST && DifY < MIN_DIST) {
                if (DifX < MIN_DIST && DifY < MIN_DIST) {
                    Log.i(DEBUG_TAG, String.valueOf(R.string.too_short));
                    Toast.makeText(getApplicationContext(), R.string.too_short, Toast.LENGTH_SHORT).show();
                } else {
                    Log.i(DEBUG_TAG, getString(R.string.skipped_diagonal));
                    Toast.makeText(getApplicationContext(), R.string.skipped_diagonal, Toast.LENGTH_SHORT).show();
                }
            } else {
                if (DifX > MIN_DIST) {
                    if (event1.getX() < event2.getX()) {
                        Log.i(DEBUG_TAG, "DERECHA");
                        moverDerecha();
                    } else {
                        Log.i(DEBUG_TAG, "IZQUIERDA");
                        moverIzquierda();
                    }
                } else {
                    if (event1.getY() < event2.getY()) {
                        Log.i(DEBUG_TAG, "ABAJO");
                        moverArriba();
                    } else {
                        Log.i(DEBUG_TAG, "ARRIBA");
                        moverAbajo();
                    }
                }
            }


            return true;
        }
    }
}