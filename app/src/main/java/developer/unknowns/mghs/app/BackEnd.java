package developer.unknowns.mghs.app;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BackEnd extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private String URL;
    private EditText editText;
    private Button button;
    private ListView listView;

    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<String> arrayList2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_end);

        editText = findViewById(R.id.website);
        button = findViewById(R.id.submit);
        listView = findViewById(R.id.list1);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                URL = dataSnapshot.child("ResultUrl").getValue(String.class);

                arrayList2.clear();
                arrayList.clear();

                for(DataSnapshot dataSnapshot1 : dataSnapshot.child("studentPosts").getChildren()){
                    arrayList.add(dataSnapshot1.getKey());
                    arrayList2.add(dataSnapshot1.child("title").getValue(String.class));
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter(BackEnd.this,R.layout.support_simple_spinner_dropdown_item,arrayList2);
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(URL!=null){
            editText.setText(URL);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.setValue(editText.getText().toString().trim());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                databaseReference.child("studentPosts").child(arrayList.get(i)).removeValue();
            }
        });

    }
}
