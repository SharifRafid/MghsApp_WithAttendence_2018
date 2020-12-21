package developer.unknowns.mghs.app;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainMenu extends AppCompatActivity {

    private final String TAG = "MAIN MENU";
    private final int code_permission = 1;
    private Boolean permission;

    private CardView
            cardViewAbout,cardViewPhotos,cardViewTeachers,
            cardViewPhone,cardViewshareApp,cardViewlogIn,
            cardViewDevelopers,cardViewPosts,cardViewAttendence;
    private Animation animation;
    private AlertDialog.Builder alertDialogue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        if (ContextCompat.checkSelfPermission(MainMenu.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
        {
            Log.e(TAG, "Permission Granted");
            permission = true;
        }else{
            requestPermission();
        }

        alertDialogue = new AlertDialog.Builder(MainMenu.this);

        animation = AnimationUtils.loadAnimation(MainMenu.this,R.anim.fade);

        cardViewAbout = findViewById(R.id.cardViewAbout);
        cardViewPhotos = findViewById(R.id.cardViewPhotos);
        cardViewTeachers = findViewById(R.id.cardViewTeachers);
        cardViewPhone = findViewById(R.id.cardViewPhone);
        cardViewshareApp = findViewById(R.id.cardViewShareApp);
        cardViewlogIn = findViewById(R.id.cardViewlogIn);
        cardViewDevelopers = findViewById(R.id.cardViewDevelopers);
        cardViewPosts = findViewById(R.id.cardViewPosts);
        cardViewAttendence = findViewById(R.id.cardViewAttendence);

        cardViewAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardViewAbout.startAnimation(animation);
                Intent page_changer = new Intent();
                page_changer.setClass(MainMenu.this,AboutSchool.class);
                startActivity(page_changer);
            }
        });

        cardViewPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardViewPhotos.startAnimation(animation);
                Intent page_changer = new Intent();
                page_changer.setClass(MainMenu.this,PhotosActivity.class);
                startActivity(page_changer);
            }
        });

        cardViewTeachers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardViewTeachers.startAnimation(animation);
                if(permission){
                    alertDialogue.setTitle("Do You Want To Call The School ?");
                    alertDialogue.setPositiveButton("Yes, Call", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent calling_intent = new Intent();
                            calling_intent.setAction(Intent.ACTION_CALL);
                            calling_intent.setData(Uri.parse("tel:0488-62424"));
                            startActivity(calling_intent);
                        }
                    });
                    alertDialogue.setNegativeButton("No,Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDialogue.create().show();
                }else{
                    requestPermission();
                    Toast.makeText(getApplicationContext(),"Failed To Call Please Grant Permission",Toast.LENGTH_SHORT).show();
                }
            }
        });

        cardViewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardViewPhone.startAnimation(animation);
                Intent page_changer = new Intent();
                page_changer.setClass(MainMenu.this,PhoneNumber.class);
                startActivity(page_changer);
            }
        });

        cardViewshareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardViewshareApp.startAnimation(animation);
                Intent share_intent = new Intent(Intent.ACTION_SEND);
                share_intent.setType("text/plain");
                share_intent.putExtra(Intent.EXTRA_SUBJECT,"Try Out MGHS APP On Google Play");
                share_intent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=developer.unknowns.mghs.app");
                startActivity(Intent.createChooser(share_intent,"Share On :"));
            }
        });

        cardViewlogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardViewlogIn.startAnimation(animation);
                Intent page_changer = new Intent();
                page_changer.setClass(MainMenu.this,StudentResults.class);
                startActivity(page_changer);
            }
        });
        cardViewDevelopers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardViewDevelopers.startAnimation(animation);
                Intent page_changer = new Intent();
                page_changer.setClass(MainMenu.this,Developers.class);
                startActivity(page_changer);
            }
        });

        cardViewPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardViewPosts.startAnimation(animation);
                Intent page_changer = new Intent();
                page_changer.setClass(MainMenu.this,StudentPosts.class);
                startActivity(page_changer);
            }
        });
        cardViewAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardViewAttendence.startAnimation(animation);
                Intent page_changer = new Intent();
                page_changer.setClass(MainMenu.this,AttendenceActivity.class);
                startActivity(page_changer);
            }
        });
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
            new AlertDialog.Builder(this)
                    .setTitle("পারমিশন প্র্যোযন")
                    .setMessage("অ্যাপ থেকে ডাইরেক্ট কল করার জন্য এই পারমিশঅন টা প্রয়োজন । তাই পরবর্তী মেসেজে অ্যালাও করে দিন।")
                    .setPositiveButton("ঠিক আছে", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainMenu.this,
                                    new String[] {Manifest.permission.CALL_PHONE}, code_permission);
                        }
                    })
                    .setNegativeButton("না, থাক ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CALL_PHONE}, code_permission);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == code_permission)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG,"Permission Granted");
                permission = true;
            } else {
                permission = false;
                Log.e(TAG,"Permission Denied");
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
