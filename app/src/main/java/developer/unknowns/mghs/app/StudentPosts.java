package developer.unknowns.mghs.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentPosts extends AppCompatActivity {


    private DatabaseReference databaseReference;
    private ArrayList<String> arrayListKeys = new ArrayList<>();
    private ArrayList<String> arrayListTitles = new ArrayList<>();
    private ArrayList<String> arrayListNames = new ArrayList<>();
    private ArrayList<String> arrayListAddress = new ArrayList<>();
    private ArrayList<String> arrayListPosts = new ArrayList<>();
    private ArrayList<String> arrayListClasses = new ArrayList<>();
    private ProgressDialog progressDialog;

    private ListView list1;
    private AlertDialog.Builder alertDialog;

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_posts);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Data...");
        progressDialog.setMessage("Loading Posts Please Wait....");
        progressDialog.setMax(100);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        alertDialog = new AlertDialog.Builder(this);

        list1 = findViewById(R.id.postsList);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netcheck = connectivityManager.getActiveNetworkInfo();
        if(netcheck==null){
            new android.support.v7.app.AlertDialog.Builder(this)
                    .setTitle("Not Connected")
                    .setCancelable(false)
                    .setMessage("We have detected no internet connection available....Please turn on data or wifi")
                    .setPositiveButton("Ok, Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .create().show();
            progressDialog.cancel();
        }else{
            badbakisob();
        }
    }

    private void badbakisob() {
        databaseReference = FirebaseDatabase.getInstance().getReference("studentPosts");

        Log.e("STATE","REFRENCE ADDED");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("DATA",dataSnapshot.toString());

                arrayListKeys.clear();
                arrayListTitles.clear();
                arrayListNames.clear();
                arrayListClasses.clear();
                arrayListAddress.clear();
                arrayListPosts.clear();


                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    arrayListKeys.add(dataSnapshot1.getKey());
                    arrayListTitles.add(dataSnapshot1.child("title").getValue(String.class));
                    arrayListNames.add(dataSnapshot1.child("name").getValue(String.class));
                    arrayListClasses.add(dataSnapshot1.child("class").getValue(String.class));
                    arrayListAddress.add(dataSnapshot1.child("address").getValue(String.class));
                    arrayListPosts.add(dataSnapshot1.child("body").getValue(String.class));
                }

                AdapterCustom2 adapterCustom2 = new AdapterCustom2(arrayListTitles,StudentPosts.this);
                list1.setAdapter(adapterCustom2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String postTitle, posterName, posterClass, posterAddress, posterStory;

                postTitle = arrayListTitles.get(i);
                posterAddress = arrayListAddress.get(i);
                posterClass = arrayListClasses.get(i);
                posterName = arrayListNames.get(i);
                posterStory = arrayListPosts.get(i);

                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_student_posts,null);

                TextView titleText = dialogView.findViewById(R.id.postTitle);
                TextView posterNameText = dialogView.findViewById(R.id.postName);
                TextView posterClassText = dialogView.findViewById(R.id.postClass);
                TextView posterAddressText = dialogView.findViewById(R.id.postAddress);
                TextView posterBodyText = dialogView.findViewById(R.id.postBody);

                titleText.setText(postTitle);
                posterNameText.setText("Posted By : "+posterName);
                posterAddressText.setText("Address : "+posterAddress);
                posterClassText.setText("Class : "+posterClass);
                posterBodyText.setText(posterStory);

                alertDialog.setView(dialogView);

                alertDialog.create().show();
            }
        });

        progressDialog.cancel();
    }
}
