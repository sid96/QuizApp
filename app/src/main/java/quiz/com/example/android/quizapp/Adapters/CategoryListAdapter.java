package quiz.com.example.android.quizapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import quiz.com.example.android.quizapp.Data.CategoryDatum;
import quiz.com.example.android.quizapp.R;

public class CategoryListAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Context context;
    private ArrayList<CategoryDatum> data;

    public CategoryListAdapter(Context context, ArrayList<CategoryDatum> data) {
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
            view = inflater.inflate(R.layout.categorylistitem, null);
        TextView name = view.findViewById(R.id.categoryname);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        LinearLayout linearLayout = view.findViewById(R.id.linearlayout);
        CategoryDatum item = data.get(i);
        name.setText(item.getName());
        imageView.setImageDrawable(context.getResources().getDrawable(item.getImageId()));
        linearLayout.setBackgroundColor(context.getResources().getColor(item.getColorId()));
        return view;
    }
}

