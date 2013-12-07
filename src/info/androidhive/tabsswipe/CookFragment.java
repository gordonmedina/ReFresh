package info.androidhive.tabsswipe;

import info.androidhive.tabsswipe.adapter.CookListAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

public class CookFragment extends Fragment {

	SearchView searchBar;
	ListView list;
	
	CookListAdapter adapter;
	
	Food_DAO dao;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_cook, container, false);
		
		dao = new Food_DAO(getActivity());
		
		searchBar = (SearchView) rootView.findViewById(R.id.cookSearch);
    	
    	list = (ListView) rootView.findViewById(R.id.cookList);
    	adapter = new CookListAdapter(getActivity().getBaseContext(), dao.getAllRecipe());
    	list.setAdapter(adapter);
    	list.setTextFilterEnabled(true);
    	list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Intent a = new Intent(getActivity(), RecipeActivity.class);
				a.putExtra("id", ((Recipe) arg0.getItemAtPosition(arg2)).getId());
				startActivity(a);
			}
		});
    	
    	setupSearchView();
    	
		return rootView;
	}
	
	private void setupSearchView() {
        searchBar.setIconifiedByDefault(false);
        searchBar.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
            	adapter.getFilter().filter(newText);
            	list.setAdapter(adapter);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                // Do something
                return true;
            }
        });
        searchBar.setSubmitButtonEnabled(false);
    }
    
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
        	list.clearTextFilter();
        } else {
        	list.setFilterText(newText.toString());
        }
        return true;
    }
 
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

}
