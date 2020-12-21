package developer.unknowns.mghs.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class CustomAdapter extends BaseAdapter {
    Context context;
    Boolean english;
    String[] nameList;
    String[] position;
    String[] numbers;

    CustomAdapter(Context context,Boolean english, String[] nameList, String[] position,String[] numbers){
        this.context=context;
        this.english = english;
        this.nameList = nameList;
        this.numbers = numbers;
        this.position = position;
    }


    @Override
    public int getCount() {
        return nameList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_view_phone,viewGroup,false);

        TextView nameBangla = view.findViewById(R.id.nameTextBangla);
        TextView nameEnglish = view.findViewById(R.id.nameTextEnglish);
        TextView positionBangla = view.findViewById(R.id.positionTextBangla);
        TextView positionEnglish = view.findViewById(R.id.positionTextEnglish);
        TextView phoneNumber = view.findViewById(R.id.phoneNumber);

        if(english) {
            nameBangla.setVisibility(View.GONE);
            nameEnglish.setVisibility(View.VISIBLE);
            nameEnglish.setText(nameList[i]);

            positionBangla.setVisibility(View.GONE);
            positionEnglish.setVisibility(View.VISIBLE);
            positionEnglish.setText(position[i]);

            phoneNumber.setText(numbers[i]);
        }else{
            nameBangla.setVisibility(View.VISIBLE);
            nameEnglish.setVisibility(View.GONE);
            nameBangla.setText(nameList[i]);

            positionBangla.setVisibility(View.VISIBLE);
            positionEnglish.setVisibility(View.GONE);
            positionBangla.setText(position[i]);

            phoneNumber.setText(numbers[i]);
        }


        return view;
    }
}
