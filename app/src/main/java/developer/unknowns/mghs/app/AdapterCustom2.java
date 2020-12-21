package developer.unknowns.mghs.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterCustom2 extends BaseAdapter{
    ArrayList<String> arrayList;
    Context context;

    AdapterCustom2(ArrayList<String> arrayList,Context context){
        this.arrayList = arrayList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return arrayList.size();
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_view_phone,viewGroup,false);

        TextView nameBangla = view.findViewById(R.id.nameTextBangla);
        TextView nameEnglish = view.findViewById(R.id.nameTextEnglish);
        TextView positionBangla = view.findViewById(R.id.positionTextBangla);
        TextView positionEnglish = view.findViewById(R.id.positionTextEnglish);
        TextView phoneNumber = view.findViewById(R.id.phoneNumber);

        nameEnglish.setVisibility(View.VISIBLE);
        nameBangla.setVisibility(View.GONE);
        positionBangla.setVisibility(View.GONE);
        positionEnglish.setVisibility(View.GONE);
        phoneNumber.setVisibility(View.GONE);

        nameEnglish.setText(arrayList.get(i));

        return view;
    }
}
