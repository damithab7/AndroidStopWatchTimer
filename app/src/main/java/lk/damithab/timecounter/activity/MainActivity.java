package lk.damithab.timecounter.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import lk.damithab.timecounter.R;

public class MainActivity extends AppCompatActivity {
    private boolean loopRun = true;
    private int count = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        System.out.println("Abc");
        Objects.requireNonNull(getSupportActionBar()).hide();

        Button btn = findViewById(R.id.startButton);
        Button btnStop = findViewById(R.id.btnStop);

        TextView txt = findViewById(R.id.timerText);
        Log.d("MainActivity", "This is a debug message");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loopRun = true;
                new Thread("TimerThread") {
                    @Override
                    public void run() {
                        try {

                            while(loopRun){
                                count++;

                                String time = String.format("%02d:%02d",
                                        TimeUnit.SECONDS.toMinutes(count),
                                        count- TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(
                                                count))
                                );
//                                char text = Character.forDigit(count, 10);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        txt.setText(time);
                                    }
                                });

                                Thread.sleep(1000);
                            }
                        }catch (InterruptedException e){
                            Log.d("exception",e.toString());
                        }
                    }
                }.start();

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loopRun = false;
            }
        });
    }
}
