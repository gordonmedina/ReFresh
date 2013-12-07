package info.androidhive.tabsswipe.adapter;

import info.androidhive.tabsswipe.Food;
import info.androidhive.tabsswipe.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StorageListAdapter extends BaseAdapter {

	Context context;
	List<Food> foodList;

	public StorageListAdapter(Context context, List<Food> foodList) {
		this.context = context;
		this.foodList = foodList;
	}
	
	public void updateFood(List<Food> foodList) {
        this.foodList = foodList;
        //Triggers the list update
        notifyDataSetChanged();
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return foodList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return foodList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return foodList.get(arg0).getId();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View row = arg1;
		ViewHolder view = null;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.fragment_storage_one_list, null);

			view = new ViewHolder();
			view.image = (ImageView) row.findViewById(R.id.food_image);
			view.foodname = (TextView) row.findViewById(R.id.food_name);
			view.foodtype = (TextView) row.findViewById(R.id.food_type);
			view.quantity = (TextView) row.findViewById(R.id.food_quantity);
			view.edate = (TextView) row.findViewById(R.id.expiry_date);
			view.pdate = (TextView) row.findViewById(R.id.purchase_date);
			view.progress = (TextProgressBar) row.findViewById(R.id.progressbar);

			row.setTag(view);
		} else {
			view = (ViewHolder) row.getTag();
		}
		Food onefood = foodList.get(arg0);
		view.image.setImageBitmap(getImage(onefood.getImage()));
		view.foodname.setText(onefood.getFoodname().toString());
		view.foodtype.setText("Food Type: " + onefood.getFoodtype().toString());
		view.quantity.setText("Quantity: " + onefood.getQuantity());
		view.edate.setText("Expiry Date: " + onefood.getEdate().toString());
		view.pdate.setText("Purchase Date: " + onefood.getPdate().toString());
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date temp = sdf.parse(onefood.getEdate().toString());
			Date temp2 = sdf.parse(onefood.getPdate().toString());
			
			Calendar c = Calendar.getInstance();
			String formattedDate = sdf.format(c.getTime());
			
			Date temp3 = sdf.parse(formattedDate);

			long date1 = temp.getTime() / (1000 * 60 * 60 * 24);
			long date2 = temp2.getTime() / (1000 * 60 * 60 * 24);
			long date3 = temp3.getTime() / (1000 * 60 * 60 * 24);

			float a1 = date1 - date3;
			float a2 = date1 - date2;
			
			float a3 = (a1 / a2) * 100;
			if(a3 <= 0)
				a3 = 0;
			
			view.progress.setProgress((int)a3);
			
			DecimalFormat df = new DecimalFormat("###.##");
			view.progress.setText(df.format(a3) + " / 100 % freshness from date bought");
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return row;
	}

	static class ViewHolder {
		ImageView image;
		TextView foodname;
		TextView foodtype;
		TextView quantity;
		TextView edate;
		TextView pdate;
		TextProgressBar progress;
	}
	
	public Bitmap getImage(String mSelectedImagePath){

		Bitmap m = null;
		try {
			File f = new File(mSelectedImagePath);

			//Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f),null,o);

			//The new size we want to scale to
			final int REQUIRED_SIZE=150;

			//Find the correct scale value. It should be the power of 2.
			int width_tmp=o.outWidth, height_tmp=o.outHeight;
			int scale=1;
			while(true){
				if(width_tmp/2 < REQUIRED_SIZE || height_tmp/2 < REQUIRED_SIZE)
					break;
				width_tmp/=2;
				height_tmp/=2;
				scale*=2;
			}

			//Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize=scale;
			m = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
			Toast.makeText(context, "Image File not found in your phone.", Toast.LENGTH_LONG).show();
		}
		return  m;
	}

}
