package developer.unknowns.mghs.app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Developers extends AppCompatActivity {

    private Button mail,like;
    private TextView title;
    private DatabaseReference databaseReference;
    private String pass = null, pass2 = null;
    private int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);

        mail = findViewById(R.id.sendMail);
        title = findViewById(R.id.textViewTitle2);
        like = findViewById(R.id.likeUs);

        final Animation animation = AnimationUtils.loadAnimation(this,R.anim.fade);

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail.startAnimation(animation);
                Intent mail = new Intent();
                mail.setAction(Intent.ACTION_VIEW);
                mail.setData(Uri.parse("mailto:sharifrafidurrahman@gmail.com"));
                mail.putExtra("subject","MGHS APP TOPIC");
                startActivity(mail);
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                like.startAnimation(animation);
                Intent like = new Intent();
                like.setAction(Intent.ACTION_VIEW);
                like.setData(Uri.parse("https://www.facebook.com/Developer.Unknown"));
                startActivity(like);
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pass = dataSnapshot.child("password").getValue(String.class);
                pass2 = dataSnapshot.child("password2").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x++;
                if(x >= 10){
                    x = 0;
                    final AlertDialog alert = new AlertDialog.Builder(Developers.this).create();
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = inflater.inflate(R.layout.dialog_editext,null);
                    final EditText editText = v.findViewById(R.id.edit);
                    Button button = v.findViewById(R.id.goButton);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!editText.getText().toString().trim().isEmpty()){
                                if(editText.getText().toString().trim().equals(pass)){
                                    Intent page_changer = new Intent();
                                    page_changer.setClass(Developers.this,DevelopersZone.class);
                                    startActivity(page_changer);
                                    finish();
                                }else if (editText.getText().toString().trim().equals(pass2)){
                                    Intent page_changer = new Intent();
                                    page_changer.setClass(Developers.this,BackEnd.class);
                                    startActivity(page_changer);
                                    finish();
                                }
                            }
                        }
                    });

                    alert.setView(v);
                    alert.show();
                }
            }
        });
    }
}
