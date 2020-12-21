package developer.unknowns.mghs.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

public class StudentWiseAttendence extends AppCompatActivity {

    private String[] students_name_list;
    private Spinner datespinner;
    private ListView listView;
    private Spinner classSpinner, shiftSpinner, groupSpinner,sectionSpinner;
    private TextView textView;
    private String Shift,Class,Group,Section;
    private ArrayList<String> classList = new ArrayList<>();
    private ArrayList<String> groupList = new ArrayList<>();
    private ArrayList<String> shiftList = new ArrayList<>();
    private ArrayList<String> sectionsList = new ArrayList<>();
    private TextView headingText, dateText;
    private ListView studentsAttendenceList;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_wise_attendence);

        objectsDeclaration();

        showListItems();

        shiftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                showListItems();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                showListItems();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                showListItems();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                showListItems();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        studentsAttendenceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getApplicationContext(),"Loading Report",Toast.LENGTH_SHORT).show();

                showdialog(i);
            }
        });


    }

    private void showdialog(int i) {
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();
        final SparseBooleanArray sparseBooleanArray2 = new SparseBooleanArray();
        Integer present = 0, absent, allWorkingDay = 0;
        float percentage = 0;

        SharedPreferences sharedPreferences = getSharedPreferences("ATTENDENCE",MODE_PRIVATE);


        Map<String, ?> map = sharedPreferences.getAll();
        for(Map.Entry<String,?> m : map.entrySet()){
            String ck = m.getKey();

            if(Integer.valueOf(Class)>8){
                if(ck.contains(Shift)&
                        m.getKey().contains("Class "+Class)&
                        m.getKey().contains(Group)&
                        m.getKey().contains(Section)){
                    keys.add(ck);
                    dates.add(ck.replace(" "+shiftSpinner.getSelectedItem()+" Class "+classSpinner.getSelectedItem()+" "+sectionSpinner.getSelectedItem()+" "+groupSpinner.getSelectedItem(),""));
                }
            }else{
                if(m.getKey().contains(Shift)&
                        m.getKey().contains("Class "+Class)&
                        m.getKey().contains(Section)){
                    keys.add(ck);
                    dates.add(ck.replace(" "+shiftSpinner.getSelectedItem()+" Class "+classSpinner.getSelectedItem()+" "+sectionSpinner.getSelectedItem(),""));

                }
            }

        }

        for(String key : keys){
            Gson gson = new Gson();
            String json = sharedPreferences.getString(key,null);
            Type type = new TypeToken<SparseBooleanArray>() {}.getType();
            SparseBooleanArray sparseBooleanArray = gson.fromJson(json,type);
            if (sparseBooleanArray != null) {
                sparseBooleanArray2.append(keys.indexOf(key),sparseBooleanArray.get(i));
                if(sparseBooleanArray.get(i)==true){
                    present++;
                }
            }else {
                sparseBooleanArray2.append(keys.indexOf(key),false);
            }
        }


        absent = keys.size()-present;
        allWorkingDay = keys.size();
        if(keys.size()!=0){
            percentage = (((float)present)/allWorkingDay)*100;
        }

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(StudentWiseAttendence.this);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View dialogView = inflater.inflate(R.layout.student_wise_attendence,null);

        TextView name = dialogView.findViewById(R.id.students_name);
        TextView presence = dialogView.findViewById(R.id.present_days);
        TextView absence = dialogView.findViewById(R.id.absent_days);
        TextView percentage_text = dialogView.findViewById(R.id.percentage);
        ListView listView = dialogView.findViewById(R.id.list1);

        name.setText(students_name_list[i]);
        presence.setText("Presence : "+Integer.toString(present));
        absence.setText("Absence : "+Integer.toString(absent));
        percentage_text.setText("Activity : "+String.format("%2.02f",percentage)+" %");

        AdapterCustom3 adapterCustom3 = new AdapterCustom3(StudentWiseAttendence.this,dates,sparseBooleanArray2);
        listView.setAdapter(adapterCustom3);

        alertDialog.setView(dialogView);

        alertDialog.create().show();
    }

    private void objectsDeclaration() {
        headingText = findViewById(R.id.heading_text);
        dateText = findViewById(R.id.date_textView);
        classSpinner = findViewById(R.id.class_spinner);
        shiftSpinner = findViewById(R.id.shift_spinner);
        groupSpinner = findViewById(R.id.groupSpinner);
        sectionSpinner = findViewById(R.id.sectionSpinner);
        studentsAttendenceList = findViewById(R.id.list1);

        headingText.setText(getResources().getString(R.string.saved_attendence_text));

        classList.add("6");
        classList.add("7");
        classList.add("8");
        classList.add("9");
        classList.add("10");
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
    public int listFinding() {
        Shift = shiftSpinner.getSelectedItem().toString();
        Class = classSpinner.getSelectedItem().toString();
        Group = groupSpinner.getSelectedItem().toString();
        Section = sectionSpinner.getSelectedItem().toString();

        String id = "students_name_"+Class+"s"+Shift.toCharArray()[0]+"s"+Section+"g"+Group.toCharArray()[0];
        Log.e("String ID", id);

        int finalId = getResources().getIdentifier(id,"array",getPackageName());

        Log.e("Final Id", String.valueOf(finalId));

        return finalId;
    }

    private void showListItems() {
        Log.e("ID", String.valueOf(listFinding()));

        if(Integer.valueOf(Class)>8){
            groupSpinner.setEnabled(true);
        }else{
            groupSpinner.setSelection(0);
            groupSpinner.setEnabled(false);
        }

        students_name_list = getResources().getStringArray(listFinding());


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(StudentWiseAttendence.this,R.layout.support_simple_spinner_dropdown_item,students_name_list);
        studentsAttendenceList.setAdapter(arrayAdapter);
    }


}
