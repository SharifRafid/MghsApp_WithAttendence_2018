package developer.unknowns.mghs.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class OnlineSubmission extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private ArrayList<String> keys;
    private DatabaseReference databaseReference;
    private ListView listView;
    private ArrayList<String> keysList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_submission);

        if(netCheck()){
            keys = fetchData();

            databaseReference = FirebaseDatabase.getInstance().getReference("main/attendences");
            listView = findViewById(R.id.list1);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    keysList.clear();
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        keysList.add(dataSnapshot1.getKey().replace("?","/"));
                    }
                    for(String key: keysList){
                        if(keys.contains(key)){
                            keys.remove(key);
                        }
                    }
                    showDataOnListView();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }



    }

    private ArrayList<String> fetchData() {
        sharedPreferences = getSharedPreferences("ATTENDENCE",MODE_PRIVATE);
        final ArrayList<String> arrayList = new ArrayList<>();
        Map<String,?> allValues = sharedPreferences.getAll();
        for(Map.Entry<String,?> mapObject: allValues.entrySet()){
            arrayList.add(mapObject.getKey());
        }
        return arrayList;
    }

    private Boolean netCheck() {
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
            return false;
        }else{
            return true;
        }
    }

    private void showDataOnListView(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,keys);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("Shared Value",sharedPreferences.getString(keys.get(i),null));
                databaseReference.child(keys.get(i).replace("/","?")).setValue(sharedPreferences.getString(keys.get(i),null)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Succesfull",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }




}
