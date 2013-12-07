package info.androidhive.tabsswipe;

import info.androidhive.tabsswipe.adapter.StorageListAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class StorageFragment extends Fragment {

	ListView list;

	Food_DAO dao;
	
	StorageListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_storage, container, false);

		dao = new Food_DAO(getActivity());
		adapter = new StorageListAdapter(getActivity(), dao.getAllFood());
		
		list = (ListView) rootView.findViewById(R.id.storageList);
		list.setAdapter(adapter);
		list.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
				alertDialogBuilder
						.setTitle("Delete Food")
						.setMessage("Are you done with this food?")
						.setCancelable(false)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,	int id) {
									dao.deleteFood(dao.getAllFood().get(arg2));
									adapter.updateFood(dao.getAllFood());
									dialog.cancel();
								}
							})
						.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.cancel();
								}
							});

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
				return true;
			}
		});

		return rootView;
	}

}
