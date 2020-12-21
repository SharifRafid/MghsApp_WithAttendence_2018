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
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class OnlineData extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private ListView listView;
    private Spinner classSpinner, shiftSpinner, groupSpinner,sectionSpinner, datespinner;
    private TextView textView;
    private ArrayList<String> classList = new ArrayList<>();
    private ArrayList<String> groupList = new ArrayList<>();
    private ArrayList<String> shiftList = new ArrayList<>();
    private ArrayList<String> sectionsList = new ArrayList<>();

    private ArrayList<String> keysList = new ArrayList<>();
    private ArrayList<String> datalist = new ArrayList<>();

    private ArrayList<String> finalKeys = new ArrayList<>();
    private ArrayList<String> finalDataList = new ArrayList<>();

    private ArrayList<String> studentList= new ArrayList<>();

    private String Shift,Class,Group,Section;




    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_data);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Data...");
        progressDialog.setMessage("Loading Reports Please Wait....");
        progressDialog.setMax(100);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        objectsDeclaration();
        
        if(netCheck()){
            progressDialog.cancel();
            fetchData();
            theRest();
        }
        shiftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                theRest();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                theRest();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                theRest();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                theRest();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        datespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                lastDataPutPart(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void theRest() {
        studentList.clear();

        studentList.addAll(Arrays.asList(getResources().getStringArray(listFinding())));

        if(Integer.valueOf(Class.replace("Class ",""))>8){
            groupSpinner.setEnabled(true);
            groupSpinner.setSelection(0);
        }else{
            groupSpinner.setEnabled(false);
            groupSpinner.setSelection(0);
        }

        finalKeys.clear();
        finalDataList.clear();

        for(int x = 0; x < keysList.size();x++){
            if(Integer.valueOf(Class.replace("Class ",""))>8){
                if(keysList.get(x).contains(Section)&
                        keysList.get(x).contains(Shift)&
                        keysList.get(x).contains(Group)&
                        keysList.get(x).contains(Class)){
                    finalKeys.add(keysList.get(x).replace(" "+shiftSpinner.getSelectedItem()+" "+classSpinner.getSelectedItem()+" "+sectionSpinner.getSelectedItem()+" "+groupSpinner.getSelectedItem(),""));
                    finalDataList.add(datalist.get(x));
                }
            }else{
                if(keysList.get(x).contains(Section)&
                        keysList.get(x).contains(Shift)&
                        keysList.get(x).contains(Class)){
                    finalKeys.add(keysList.get(x).replace(" "+shiftSpinner.getSelectedItem()+" "+classSpinner.getSelectedItem()+" "+sectionSpinner.getSelectedItem(),""));
                    finalDataList.add(datalist.get(x));
                }
            }
        }

        ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(OnlineData.this,R.layout.support_simple_spinner_dropdown_item,finalKeys);
        datespinner.setAdapter(dateAdapter);

        if(finalKeys.size()!=0){
            lastDataPutPart(0);
            listView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }else{
            listView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }

    }

    private void lastDataPutPart(int index) {
        String json = finalDataList.get(index);
        Gson gson = new Gson();
        Type type = new TypeToken<SparseBooleanArray>() {}.getType();
        SparseBooleanArray sparseBooleanArray = gson.fromJson(json, type);

        AdapterCustom3 adapterCustom3 = new AdapterCustom3(OnlineData.this,studentList,sparseBooleanArray);
        listView.setAdapter(adapterCustom3);
    }

    public int listFinding() {
        Shift = shiftSpinner.getSelectedItem().toString();
        Class = classSpinner.getSelectedItem().toString();
        Group = groupSpinner.getSelectedItem().toString();
        Section = sectionSpinner.getSelectedItem().toString();

        String id = "students_name_"+Class.replace("Class ","")+"s"+Shift.toCharArray()[0]+"s"+Section+"g"+Group.toCharArray()[0];

        int finalId = getResources().getIdentifier(id,"array",getPackageName());

        return finalId;
    }

    private void fetchData() {
        keysList.clear();
        datalist.clear();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Log.e("VALue KEY",dataSnapshot1.getKey());
                    Log.e("VALue DATA",dataSnapshot1.getValue().toString());
                    keysList.add(dataSnapshot1.getKey().replace("?","/"));
                    datalist.add(dataSnapshot1.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
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

    private void objectsDeclaration() {
        classSpinner = findViewById(R.id.class_spinner);
        datespinner = findViewById(R.id.dateSpinner);
        shiftSpinner = findViewById(R.id.shift_spinner);
        groupSpinner = findViewById(R.id.groupSpinner);
        sectionSpinner = findViewById(R.id.sectionSpinner);
        listView = findViewById(R.id.list1);
        textView = findViewById(R.id.texViewNoData);

        databaseReference = FirebaseDatabase.getInstance().getReference("main/attendences");

        classList.add("Class 6");
        classList.add("Class 7");
        classList.add("Class 8");
        classList.add("Class 9");
        classList.add("Class 10");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,classList);
        classSpinner.setAdapter(spinnerAdapter);
        classSpinner.setSelection(0);

        groupList.add("Science");
        groupList.add("Arts");
        groupList.add("Commerce");
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,groupList);
        groupSpinner.setAdapter(spinnerAdapter2);
        groupSpinner.setSelection(0);

        shiftList.add("Morning");
        shiftList.add("Day");
        ArrayAdapter<String> spinnerAdapter3 = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,shiftList);
        shiftSpinner.setAdapter(spinnerAdapter3);
        shiftSpinner.setSelection(0);

        sectionsList.add("A");
        sectionsList.add("B");
        ArrayAdapter<String> spinnerAdapter4 = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,sectionsList);
        sectionSpinner.setAdapter(spinnerAdapter4);
        sectionSpinner.setSelection(0);

    }

}
