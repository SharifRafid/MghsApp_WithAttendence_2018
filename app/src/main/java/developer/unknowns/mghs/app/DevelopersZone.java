package developer.unknowns.mghs.app;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DevelopersZone extends AppCompatActivity {

    private EditText title, name, address, classu, body;
    private Button button;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers_zone);

        title = findViewById(R.id.title);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        classu =  findViewById(R.id.classu);
        body = findViewById(R.id.body);
        button = findViewById(R.id.button);

        databaseReference = FirebaseDatabase.getInstance().getReference("studentPosts");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = databaseReference.push().getKey();
                databaseReference.child(key).child("title").setValue(title.getText().toString().trim());
                databaseReference.child(key).child("name").setValue(name.getText().toString().trim());
                databaseReference.child(key).child("class").setValue(classu.getText().toString().trim());
                databaseReference.child(key).child("address").setValue(address.getText().toString().trim());
                databaseReference.child(key).child("body").setValue(body.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Successfull",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
