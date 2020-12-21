package developer.unknowns.mghs.app;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterCustom3 extends BaseAdapter {

    ArrayList<String> names;
    SparseBooleanArray sparseBooleanArray;
    Context context;

    AdapterCustom3(Context context, ArrayList<String> names, SparseBooleanArray sparseBooleanArray){
        this.context = context;
        this.names = names;
        this.sparseBooleanArray = sparseBooleanArray;
    }

    @Override
    public int getCount() {
        return names.size();
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
        if(viewGroup!=null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_list_layout,viewGroup,false);
        }

        TextView textView = view.findViewById(R.id.textViewName);
        TextView presence = view.findViewById(R.id.textViewPresence);
        LinearLayout linearLayout = view.findViewById(R.id.linearBack);

        textView.setText(names.get(i));
        if(sparseBooleanArray.get(i)){
            presence.setText("Present");
            linearLayout.setBackgroundColor(0xFFFFFFFF);
        }else {
            presence.setText("Absent");
            linearLayout.setBackgroundColor(0xFFE57373);
        }


        return view;
    }
}
