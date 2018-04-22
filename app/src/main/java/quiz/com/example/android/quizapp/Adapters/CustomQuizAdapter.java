package quiz.com.example.android.quizapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import quiz.com.example.android.quizapp.Data.CustomCategoryDatum;
import quiz.com.example.android.quizapp.R;

public class CustomQuizAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Context context;
    private ArrayList<CustomCategoryDatum> data;

    public CustomQuizAdapter(Context context, ArrayList<CustomCategoryDatum> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.i("CategoryAdapter", "getView: " + i);
        if (view == null)
            view = inflater.inflate(R.layout.createquizlistitem, null);
        TextView name = view.findViewById(R.id.customcategoryname);
        CustomCategoryDatum item = data.get(i);
        name.setText(item.getName());
        return view;
    }
}

