package developer.unknowns.mghs.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Timer timer = new Timer();
    private TimerTask timerTask;
    private ImageView bgapp, clover;
    private LinearLayout textsplash, texthome, menus;
    private Animation frombottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frombottom = AnimationUtils.loadAnimation(MainActivity.this, R.anim.frombottom);

        bgapp = findViewById(R.id.bgapp);
        clover = findViewById(R.id.clover);
        textsplash = findViewById(R.id.textsplash);
        texthome = findViewById(R.id.texthome);
        menus = findViewById(R.id.menus);

        bgapp.animate().translationY(-1900).setDuration(3000).setStartDelay(1500);
        clover.animate().alpha(0).setDuration(3000).setStartDelay(2500);
        textsplash.animate().translationY(140).alpha(0).setDuration(3000).setStartDelay(1500);

        texthome.startAnimation(frombottom);
        menus.startAnimation(frombottom);

        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent page_changer = new Intent();
                        page_changer.setClass(MainActivity.this,MainMenu.class);
                        page_changer.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(page_changer);
                    }
                });
            }
        };

        timer.schedule(timerTask,(int)(4000));
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
