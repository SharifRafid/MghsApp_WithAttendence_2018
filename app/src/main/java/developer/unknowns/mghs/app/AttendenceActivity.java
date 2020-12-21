package developer.unknowns.mghs.app;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AttendenceActivity extends AppCompatActivity {

    private CardView attendence_report, saved_attendence, offline_attendence_submit,student_wise_report,onlineSubmit;
    private Animation animation;
    private int x;
    private String pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);

        animation = AnimationUtils.loadAnimation(this,R.anim.fade);

        attendence_report = findViewById(R.id.attendenceReportTextView);
        saved_attendence = findViewById(R.id.savedAttendenceTextView);
        offline_attendence_submit = findViewById(R.id.submitAttendenceOffline);
        student_wise_report = findViewById(R.id.studentWiseAttendence);
        onlineSubmit = findViewById(R.id.onlineSubmission);

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("TPConsole");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                pass = dataSnapshot.child("pwRD").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        attendence_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attendence_report.startAnimation(animation);

                Intent page_changer = new Intent();
                page_changer.setClass(AttendenceActivity.this,OnlineData.class);
                startActivity(page_changer);
            }
        });
        saved_attendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saved_attendence.startAnimation(animation);

                Intent page_changer = new Intent();
                page_changer.setClass(AttendenceActivity.this,AttendenceReport.class);
                page_changer.putExtra("VERSION","OFFLINE");
                startActivity(page_changer);
            }
        });
        offline_attendence_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offline_attendence_submit.startAnimation(animation);

                Intent page_changer = new Intent();
                page_changer.setClass(AttendenceActivity.this,SubmitAttendence.class);
                page_changer.putExtra("VERSION","OFFLINE");
                startActivity(page_changer);
            }
        });
        student_wise_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                student_wise_report.startAnimation(animation);

                Intent page_changer = new Intent();
                page_changer.setClass(AttendenceActivity.this,StudentWiseAttendence.class);
                startActivity(page_changer);
            }
        });
        onlineSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onlineSubmit.startAnimation(animation);
                x++;
                if(x >= 10){
                    x = 0;
                    final AlertDialog alert = new AlertDialog.Builder(AttendenceActivity.this).create();
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = inflater.inflate(R.layout.dialog_editext,null);
                    final EditText editText = v.findViewById(R.id.edit);
                    Button button = v.findViewById(R.id.goButton);
                    editText.setHint("Enter Password For Submission");
                    button.setText("Submit Password");

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!editText.getText().toString().trim().isEmpty()){
                                if(editText.getText().toString().trim().equals(pass)){
                                    Intent page_changer = new Intent();
                                    page_changer.setClass(AttendenceActivity.this,OnlineSubmission.class);
                                    startActivity(page_changer);
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
