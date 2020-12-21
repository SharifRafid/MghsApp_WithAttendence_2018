package developer.unknowns.mghs.app;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PhoneNumber extends AppCompatActivity {
    private final String TAG = "Phone Number";
    private int code_permission = 1;
    private Boolean permission= false;

    private TextView titleEnglish,titleBangla,headSirEnglish,headSirBangla;
    private ImageView translate;
    private ListView listView;
    private Boolean english = false;

    private String[] name, position,phone;

    private AlertDialog.Builder alertDialogue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        if (ContextCompat.checkSelfPermission(PhoneNumber.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
        {
            Log.e(TAG, "Permission Granted");
            permission = true;
        }else{
            requestPermission();
        }

        titleBangla = findViewById(R.id.titleBangla);
        titleEnglish = findViewById(R.id.titleEnglish);
        listView = findViewById(R.id.listViewPhoneNumbers);
        translate = findViewById(R.id.translateImage);

        headSirBangla = findViewById(R.id.headSirBangla);
        headSirEnglish = findViewById(R.id.headSirEnglish);

        alertDialogue = new AlertDialog.Builder(PhoneNumber.this);

        if(english){
            name = getResources().getStringArray(R.array.teachersNameEnglish);
            position = getResources().getStringArray(R.array.positionEnglish);
            phone = getResources().getStringArray(R.array.phonesTeachers);
            CustomAdapter customAdapter = new CustomAdapter(PhoneNumber.this,true, name, position,phone);
            listView.setAdapter(customAdapter);

            titleEnglish.setVisibility(View.VISIBLE);
            titleBangla.setVisibility(View.GONE);
            headSirEnglish.setVisibility(View.VISIBLE);
            headSirBangla.setVisibility(View.GONE);

        }else{
            name = getResources().getStringArray(R.array.teachersNameBangla);
            position = getResources().getStringArray(R.array.positionBangla);
            phone = getResources().getStringArray(R.array.phonesTeachers);

            CustomAdapter customAdapter = new CustomAdapter(PhoneNumber.this,false, name, position,phone);
            listView.setAdapter(customAdapter);

            titleEnglish.setVisibility(View.GONE);
            titleBangla.setVisibility(View.VISIBLE);
            headSirEnglish.setVisibility(View.GONE);
            headSirBangla.setVisibility(View.VISIBLE);
        }

        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(english){
                    titleEnglish.setVisibility(View.GONE);
                    titleBangla.setVisibility(View.VISIBLE);
                    english = false;

                    name = getResources().getStringArray(R.array.teachersNameBangla);
                    position = getResources().getStringArray(R.array.positionBangla);
                    phone = getResources().getStringArray(R.array.phonesTeachers);

                    headSirEnglish.setVisibility(View.GONE);
                    headSirBangla.setVisibility(View.VISIBLE);

                    CustomAdapter customAdapter = new CustomAdapter(PhoneNumber.this,false, name, position,phone);
                    listView.setAdapter(customAdapter);
                }else{
                    titleEnglish.setVisibility(View.VISIBLE);
                    titleBangla.setVisibility(View.GONE);
                    english = true;

                    name = getResources().getStringArray(R.array.teachersNameEnglish);
                    position = getResources().getStringArray(R.array.positionEnglish);
                    phone = getResources().getStringArray(R.array.phonesTeachers);

                    headSirEnglish.setVisibility(View.VISIBLE);
                    headSirBangla.setVisibility(View.GONE);

                    CustomAdapter customAdapter = new CustomAdapter(PhoneNumber.this,true, name, position,phone);
                    listView.setAdapter(customAdapter);
                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,final int index, long l) {
                Log.e(TAG,"Item Clicked On ListView .Item No : "+index);
                alertDialogue.setTitle("Do You Want To Call "+name[index]+" ?");
                alertDialogue.setPositiveButton("Yes, Call", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(permission){
                            Intent caller_intent = new Intent();
                            caller_intent.setAction(Intent.ACTION_CALL);
                            caller_intent.setData(Uri.parse("tel:"+phone[index]));
                            startActivity(caller_intent);
                            Toast.makeText(getApplicationContext(),"Calling",Toast.LENGTH_SHORT).show();
                        }else{
                            requestPermission();
                            Toast.makeText(getApplicationContext(),"Failed To Call Please Grant Permission",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                alertDialogue.setNegativeButton("No,Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialogue.create().show();
            }
        });

    }

    public void requestPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
            new AlertDialog.Builder(this)
                    .setTitle("পারমিশন প্র্যোযন")
                    .setMessage("অ্যাপ থেকে ডাইরেক্ট কল করার জন্য এই পারমিশঅন টা প্রয়োজন । তাই পরবর্তী মেসেজে অ্যালাও করে দিন।")
                    .setPositiveButton("ঠিক আছে", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(PhoneNumber.this,
                                    new String[] {Manifest.permission.CALL_PHONE}, code_permission);
                        }
                    })
                    .setNegativeButton("না, থাক ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CALL_PHONE}, code_permission);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == code_permission)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG,"Permission Granted");
                permission = true;
            } else {
                permission = false;
                Log.e(TAG,"Permission Denied");
            }
        }
    }
}
