package developer.unknowns.mghs.app;

import android.content.ClipData;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class AttendenceReport extends AppCompatActivity {

    private Spinner datespinner;
    private ListView listView;
    private Spinner classSpinner, shiftSpinner, groupSpinner,sectionSpinner;
    private TextView textView;
    private ArrayList<String> classList = new ArrayList<>();
    private ArrayList<String> groupList = new ArrayList<>();
    private ArrayList<String> shiftList = new ArrayList<>();
    private ArrayList<String> sectionsList = new ArrayList<>();
    private SparseBooleanArray data;
    private ArrayList<String> finalList = new ArrayList<>();
    private ArrayList<String> arrayListKeys = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<String> finalKEys = new ArrayList<>();

    private int Shift,Class,Group,Section;

    private String[] StudentNamesListScience;
    private String[] StudentNamesListArts;
    private String[] StudentNamesListCommerce;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_report);

        objectsDeclaration();

        arrayListKeys = fetchData();

        datesandfinal();

        shiftWiseList();

        shiftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                datesandfinal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                datesandfinal();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                datesandfinal();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                datesandfinal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        datespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String key = finalKEys.get(i);
                Gson gson = new Gson();
                String json = sharedPreferences.getString(key,null);
                Type type = new TypeToken<SparseBooleanArray>() {}.getType();
                data = gson.fromJson(json, type);

                shiftWiseList();

                finalList.clear();

                if(Group==0){
                    int RollCount = 1;
                    for(String name : StudentNamesListScience){
                        finalList.add(RollCount+" "+name);
                        RollCount++;
                    }
                }else if(Group ==1){
                    int RollCount = 1;
                    for(String name : StudentNamesListArts) {
                        finalList.add(RollCount + " " + name);
                        RollCount ++;
                    }
                }else if(Group == 2){
                    int RollCount = 1;
                    for(String name : StudentNamesListCommerce){
                        finalList.add(RollCount+" "+name);
                        RollCount ++;
                    }
                }

                AdapterCustom3 arrayAdapter = new AdapterCustom3(AttendenceReport.this,finalList,data);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void datesandfinal() {
        Shift = shiftSpinner.getSelectedItemPosition();
        Class = classSpinner.getSelectedItemPosition();
        Group = groupSpinner.getSelectedItemPosition();
        Section = sectionSpinner.getSelectedItemPosition();

        dates.clear();
        finalKEys.clear();
        if(classSpinner.getSelectedItemPosition()<=2){
            groupSpinner.setEnabled(false);
            groupSpinner.setSelection(0);
            for(String ley:arrayListKeys){
                if(ley.contains(shiftSpinner.getSelectedItem().toString())&
                        ley.contains(classSpinner.getSelectedItem().toString())&
                        ley.contains(sectionSpinner.getSelectedItem().toString())){
                    dates.add(ley.replace(" "+shiftSpinner.getSelectedItem()+" "+classSpinner.getSelectedItem()+" "+sectionSpinner.getSelectedItem(),""));
                    finalKEys.add(ley);
                }
            }
        }else{
            groupSpinner.setEnabled(true);
            for(String ley:arrayListKeys){
                if(ley.contains(shiftSpinner.getSelectedItem().toString())&
                        ley.contains(classSpinner.getSelectedItem().toString())&
                        ley.contains(sectionSpinner.getSelectedItem().toString())&
                        ley.contains(groupSpinner.getSelectedItem().toString())){
                    dates.add(ley.replace(" "+shiftSpinner.getSelectedItem()+" "+classSpinner.getSelectedItem()+" "+sectionSpinner.getSelectedItem()+" "+groupSpinner.getSelectedItem(),""));
                    finalKEys.add(ley);
                }
            }
        }
        if(dates.size()==0){
            listView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }else{
            listView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,dates);
            datespinner.setAdapter(arrayAdapter);
            datespinner.setSelection(0);
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

    private void objectsDeclaration() {
        classSpinner = findViewById(R.id.class_spinner);
        shiftSpinner = findViewById(R.id.shift_spinner);
        groupSpinner = findViewById(R.id.groupSpinner);
        sectionSpinner = findViewById(R.id.sectionSpinner);
        datespinner = findViewById(R.id.dateSpinner);
        listView = findViewById(R.id.list1);
        textView = findViewById(R.id.texViewNoData);


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

    private void shiftWiseList(){
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

}
