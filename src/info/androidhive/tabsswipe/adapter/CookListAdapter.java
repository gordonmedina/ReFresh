package info.androidhive.tabsswipe.adapter;

import info.androidhive.tabsswipe.R;
import info.androidhive.tabsswipe.Recipe;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

public class CookListAdapter extends BaseAdapter {
	
	Context context;
	List<Recipe> origList;
	List<Recipe> newList;
	
	public CookListAdapter(Context context, List<Recipe> recipeList){
		this.context = context;
		this.origList = recipeList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return origList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return origList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return origList.get(arg0).getId();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View row = arg1;
		ViewHolder view = null;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.fragment_cook_one_list, null);

			view = new ViewHolder();
			view.image = (ImageView) row.findViewById(R.id.recipe_image);
			view.recipename = (TextView) row.findViewById(R.id.recipe_name);
			view.recipetype = (TextView) row.findViewById(R.id.recipe_type);

			row.setTag(view);
		} else {
			view = (ViewHolder) row.getTag();
		}
		Recipe onerecipe = origList.get(arg0);
		view.image.setImageResource(onerecipe.getImageid());
		view.recipename.setText(onerecipe.getName().toString());
		view.recipetype.setText("Recipe Type: " + onerecipe.getType().toString());
		
		return row;
	}
	
	static class ViewHolder {
		ImageView image;
		TextView recipename;
		TextView recipetype;
	}
	
	public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                origList = (ArrayList<Recipe>) results.values;
                if (results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (newList == null)
                    newList = new ArrayList<Recipe>(origList);
                ArrayList<Recipe> result;
                FilterResults r = new FilterResults();
                if (constraint == null || constraint.length() <= 0)
                    result = new ArrayList<Recipe>(newList);
                else {
                    result = new ArrayList<Recipe>();
                    for (int i = 0; i < newList.size(); i++)
                        if (constraint.length() > 0 && ((((Recipe)newList.get(i)).getName().toLowerCase().contains(constraint.toString())) || (((Recipe)newList.get(i)).getType().toLowerCase().contains(constraint.toString()))))
                            result.add(newList.get(i));
                }
                r.values = result;
                r.count = result.size();
                return r;
            }
        };
    }

}
