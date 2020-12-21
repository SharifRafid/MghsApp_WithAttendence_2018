package developer.unknowns.mghs.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class SubmitAttendence extends AppCompatActivity {

    private TextView headingText, dateText;
    private Spinner classSpinner, shiftSpinner, groupSpinner,sectionSpinner;
    private ArrayList<String> classList = new ArrayList<>();
    private ArrayList<String> groupList = new ArrayList<>();
    private ArrayList<String> shiftList = new ArrayList<>();
    private ArrayList<String> sectionsList = new ArrayList<>();
    private ListView studentsAttendenceList;
    private Button save_all;
    private String VERSION, KEY_ID;
    private String[] StudentNamesListScience;
    private String[] StudentNamesListArts;
    private String[] StudentNamesListCommerce;

    private ArrayList<String> StudentNamesListArray = new ArrayList<String>();
    private SparseBooleanArray sparseBooleanArray, sparseBooleanArray2;
    private Calendar calendar = Calendar.getInstance();
    private String DATE;
    private int Shift,Class,Group,Section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_attendence);

        objectsDeclaration();

        shiftSpinner.setEnabled(false);

        listFinding();

        showListItems();

        addItemsToList();          //If overadding or readding

        shiftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                listFinding();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                listFinding();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                listFinding();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                listFinding();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        save_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAll();
            }
        });


    }

    private void addItemsToList() {
        ArrayList<String> keysa = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("ATTENDENCE",MODE_PRIVATE);
        Map<String,?> allValues = sharedPreferences.getAll();
        for(Map.Entry<String,?> mapObject: allValues.entrySet()){
            keysa.add(mapObject.getKey());
        }

        final String KEY_ID2;

        if(Class<=2){
            KEY_ID2 = DATE+" "+shiftSpinner.getSelectedItem()+" "+classSpinner.getSelectedItem()+" "+sectionSpinner.getSelectedItem();
        }else{
            KEY_ID2 = DATE+" "+shiftSpinner.getSelectedItem()+" "+classSpinner.getSelectedItem()+" "+sectionSpinner.getSelectedItem()+" "+groupSpinner.getSelectedItem();
        }

        if(keysa.contains(KEY_ID2)){
            Gson gson = new Gson();
            String json = sharedPreferences.getString(KEY_ID2,null);
            Type type = new TypeToken<SparseBooleanArray>() {}.getType();
            sparseBooleanArray2 = gson.fromJson(json, type);

            for(int x=0;x<sparseBooleanArray2.size();x++){
                if(sparseBooleanArray2.get(x)) {
                    studentsAttendenceList.setItemChecked(x,true);
                }else{
                    studentsAttendenceList.setItemChecked(x,false);
                }
            }
        }
    }

    private void listFinding() {
        Shift = shiftSpinner.getSelectedItemPosition();
        Class = classSpinner.getSelectedItemPosition();
        Group = groupSpinner.getSelectedItemPosition();
        Section = sectionSpinner.getSelectedItemPosition();

        shiftWiseList();

        StudentNamesListArray.clear();

        if(Group==0){
            int index = 0;
            for(String string: StudentNamesListScience){
                index ++;
                StudentNamesListArray.add(String.valueOf(index)+" : "+string);
            }
        }else if(Group==1){
            int index = 0;
            for(String string: StudentNamesListArts){
                index ++;
                StudentNamesListArray.add(String.valueOf(index)+" : "+string);
            }
        }else if(Group==2){
            int index = 0;
            for(String string: StudentNamesListCommerce){
                index ++;
                StudentNamesListArray.add(String.valueOf(index)+" : "+string);
            }
        }

        showListItems();
    }

    private void shiftWiseList() {
        if(Shift==0){//MOrning Shift
            if(Class==0){
                groupSpinner.setEnabled(false);
                groupSpinner.setSelection(0);
                if(Section==0) {
                    StudentNamesListScience = getResources().getStringArray(R.array.students_name_6sMsAgS);
                }else{
                    StudentNamesListScience = getResources().getStringArray(R.array.students_name_6sMsBgS);
                }
            }else if(Class==1){
                groupSpinner.setSelection(0);
                groupSpinner.setEnabled(false);
                if(Section==0) {
                    StudentNamesListScience = getResources().getStringArray(R.array.students_name_7sMsAgS);
                }else{
                    StudentNamesListScience = getResources().getStringArray(R.array.students_name_7sMsBgS);
                }
            }
            else if(Class==2){
                groupSpinner.setSelection(0);
                groupSpinner.setEnabled(false);
                if(Section==0) {
                    StudentNamesListScience = getResources().getStringArray(R.array.students_name_8sMsAgS);
                }else{
                    StudentNamesListScience = getResources().getStringArray(R.array.students_name_8sMsBgS);
                }
            }else if(Class==3){
                groupSpinner.setEnabled(true);
                if(Section==0) {
                    StudentNamesListScience = getResources().getStringArray(R.array.students_name_9sMsAgS);
                    StudentNamesListArts = getResources().getStringArray(R.array.students_name_9sMsAgA);
                    StudentNamesListCommerce = getResources().getStringArray(R.array.students_name_9sMsAgC);
                }else{
                    StudentNamesListScience = getResources().getStringArray(R.array.students_name_9sMsBgS);
                    StudentNamesListArts = getResources().getStringArray(R.array.students_name_9sMsBgA);
                    StudentNamesListCommerce = getResources().getStringArray(R.array.students_name_9sMsBgC);
                }
            }else if(Class==4){
                groupSpinner.setEnabled(true);
                if(Section==0) {
                    StudentNamesListScience = getResources().getStringArray(R.array.students_name_10sMsAgS);
                    StudentNamesListArts = getResources().getStringArray(R.array.students_name_10sMsAgA);
                    StudentNamesListCommerce = getResources().getStringArray(R.array.students_name_10sMsAgC);
                }else{
                    StudentNamesListScience = getResources().getStringArray(R.array.students_name_10sMsBgS);
                    StudentNamesListArts = getResources().getStringArray(R.array.students_name_10sMsBgA);
                    StudentNamesListCommerce = getResources().getStringArray(R.array.students_name_10sMsBgC);
                }
            }
        }else{//Day Shift
            if(Class==0){
                groupSpinner.setSelection(0);
                groupSpinner.setEnabled(false);
                if(Section==0) {
                    StudentNamesListScience = getResources().getStringArray(R.array.students_name_6sDsAgS);
                }else{
                    StudentNamesListScience = getResources().getStringArray(R.array.students_name_6sDsBgS);
                }
            }else if(Class==1){
                groupSpinner.setSelection(0);
                groupSpinner.setEnabled(false);
                if(Section==0) {
                    StudentNamesListScience = getResources().getStringArray(R.array.students_name_7sDsAgS);
                }else{
                    StudentNamesListScience = getResources().getStringArray(R.array.students_name_7sDsBgS);
                }
            }
            else if(Class==2){
                groupSpinner.setSelection(0);
                groupSpinner.setEnabled(false);
                if(Section==0) {
                    StudentNamesListScience = getResources().getStringArray(R.array.students_name_8sDsAgS);
                }else{
                    StudentNamesListScience = getResources().getStringArray(R.array.students_name_8sDsBgS);
                }
            }else if(Class==3){
                groupSpinner.setEnabled(true);
                if(Section==0) {
                    StudentNamesListScience = getResources().getStringArray(R.array.students_name_9sDsAgS);
                    StudentNamesListArts = getResources().getStringArray(R.array.students_name_9sDsAgA);
                    StudentNamesListCommerce = getResources().getStringArray(R.array.students_name_9sDsAgC);
                }else{
                    StudentNamesListScience = getResources().getStringArray(R.array.students_name_9sDsBgS);
                    StudentNamesListArts = getResources().getStringArray(R.array.students_name_9sDsBgA);
                    StudentNamesListCommerce = getResources().getStringArray(R.array.students_name_9sDsBgC);
                }
            }else if(Class==4){
                groupSpinner.setEnabled(true);
                if(Section==0) {
                    StudentNamesListScience = getResources().getStringArray(R.array.students_name_10sDsAgS);
                    StudentNamesListArts = getResources().getStringArray(R.array.students_name_10sDsAgA);
                    StudentNamesListCommerce = getResources().getStringArray(R.array.students_name_10sDsAgC);
                }else{
                    StudentNamesListScience = getResources().getStringArray(R.array.students_name_10sDsBgS);
                    StudentNamesListArts = getResources().getStringArray(R.array.students_name_10sDsBgA);
                    StudentNamesListCommerce = getResources().getStringArray(R.array.students_name_10sDsBgC);
                }
            }
        }
    }

    private void showListItems() {
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, R.layout.list_design_student_attendence_submit,R.id.checkBox,StudentNamesListArray);
        studentsAttendenceList.setAdapter(listAdapter);

        addItemsToList();
    }

    private void objectsDeclaration() {
        headingText = findViewById(R.id.heading_text);
        dateText = findViewById(R.id.date_textView);
        classSpinner = findViewById(R.id.class_spinner);
        shiftSpinner = findViewById(R.id.shift_spinner);
        groupSpinner = findViewById(R.id.groupSpinner);
        sectionSpinner = findViewById(R.id.sectionSpinner);
        studentsAttendenceList = findViewById(R.id.listViewAttendence);
        save_all = findViewById(R.id.button_save);

        headingText.setText(getResources().getString(R.string.saved_attendence_text));

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

        DATE = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());
        dateText.setText(DATE);
    }

    private void saveAll() {
        sparseBooleanArray = studentsAttendenceList.getCheckedItemPositions();


        final AlertDialog.Builder alert = new AlertDialog.Builder(SubmitAttendence.this);
        alert.setTitle("All the values should be confirmed");
        alert.setMessage("Please make sure that "+classSpinner.getSelectedItem()+" rolls are called , If you're sure only then click next else click re-add");
        alert.setPositiveButton("Next, Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(Class<=2){
                    KEY_ID = DATE+" "+shiftSpinner.getSelectedItem()+" "+classSpinner.getSelectedItem()+" "+sectionSpinner.getSelectedItem();
                }else{
                    KEY_ID = DATE+" "+shiftSpinner.getSelectedItem()+" "+classSpinner.getSelectedItem()+" "+sectionSpinner.getSelectedItem()+" "+groupSpinner.getSelectedItem();
                }

                SharedPreferences sharedPreferences = getSharedPreferences("ATTENDENCE",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(sparseBooleanArray);
                editor.putString(KEY_ID,json);
                editor.apply();
                Toast.makeText(getApplicationContext(),"Saved Succesfully :)",Toast.LENGTH_SHORT).show();

            }
        });
        alert.setNegativeButton("Re-Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }

}
