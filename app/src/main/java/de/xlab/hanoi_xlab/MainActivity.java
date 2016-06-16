package de.xlab.hanoi_xlab;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    int moveCount = 0;
    TextView moveCountTextView;
    LinearLayout stick_1, stick_2, stick_3;
    ImageView storeView, disc_big, disc_middle, disc_small;
    View.OnClickListener takeListener, putListener;

    public void init(int anzScheiben){

        stick_1 = (LinearLayout) findViewById(R.id.stick_1);
        stick_2 = (LinearLayout) findViewById(R.id.stick_2);
        stick_3 = (LinearLayout) findViewById(R.id.stick_3);
        moveCountTextView = (TextView) findViewById(R.id.moveCountTextView);
        moveCount = 0;
        moveCountTextView.setText("Anzahl der Züge: " + moveCount);
        moveCountTextView.setTextSize(16);

        Button restart = (Button) findViewById(R.id.restart);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        };
        restart.setOnClickListener(clickListener);

        for(int i = 1; i < anzScheiben + 1; i++){
            ImageView disc = createDisc((i*100),(100), color(i));
            stick_1.addView(disc);
        }

        takeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layout = (LinearLayout) v;
                take(layout);
            }
        };

        putListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layout = (LinearLayout) v;
                put(layout);
            }
        };

        stick_1.setOnClickListener(takeListener);
        stick_2.setOnClickListener(takeListener);
        stick_3.setOnClickListener(takeListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init(5);
    }

    void take(LinearLayout layout) {
        if (layout.getChildCount() > 0) {
            storeView = (ImageView) layout.getChildAt(0);
            layout.removeViewAt(0);
            stick_1.setOnClickListener(putListener);
            stick_2.setOnClickListener(putListener);
            stick_3.setOnClickListener(putListener);
        }
    }

    void put(LinearLayout layout) {
        if (is_valid_move(layout)) {
            layout.addView(storeView, 0);
            stick_1.setOnClickListener(takeListener);
            stick_2.setOnClickListener(takeListener);
            stick_3.setOnClickListener(takeListener);
            moveCount++;
            moveCountTextView.setText("Anzahl der Züge: " + moveCount);
            if(win()) {
                Toast.makeText(this, "Gewonnen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    boolean is_valid_move(LinearLayout ll) {
        if(ll.getChildCount() == 0)
            return true;
        return (int) ll.getChildAt(0).getTag() > (int) storeView.getTag();
    }

    boolean win(){
        if(stick_1.getChildCount() == 0 && stick_2.getChildCount() == 0) {
            return true;
        } else {
            return false;
        }
    }

    void reset(){
        moveCount = 0;
        moveCountTextView.setText("Anzahl der Züge: " + moveCount);
        stick_1.removeAllViews();
        stick_2.removeAllViews();
        stick_3.removeAllViews();
        init(5);
    }

    ImageView createDisc(int breite, int hoehe, int farbe){
        ImageView disc = new ImageView(MainActivity.this);
        GradientDrawable discShape = new GradientDrawable();

        discShape.setSize(breite,hoehe);
        discShape.setColor(farbe);
        discShape.setCornerRadius(10);

        disc.setImageDrawable(discShape);
        disc.setTag(breite);
        return disc;
    }

    // Funktion zur Auswahl der Farbe der Spielsteine

    int color(int i) {
        switch(i) {
            case 1: return Color.BLUE;
            case 2: return Color.CYAN;
            case 3: return Color.DKGRAY;
            case 4: return Color.GRAY;
            case 5: return Color.GREEN;
            case 6: return Color.LTGRAY;
            case 7: return Color.MAGENTA;
            case 8: return Color.RED;
            case 9: return Color.YELLOW;
            default:return Color.BLACK;
        }
    }
}