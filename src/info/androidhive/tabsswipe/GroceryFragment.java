package info.androidhive.tabsswipe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class GroceryFragment extends Fragment implements TextWatcher {

	ImageView selectimage, captureimage;
	Button submit;
	DatePicker datePicker, datePicker2;
	ImageView image;
	EditText foodname, quantity;
	AutoCompleteTextView foodtype;
	
	String dateString, dateString2;
	Date date1, date2;
	
	Food_DAO dao;
	
	Calendar c;
	
	private static final int REQUEST_CODE = 1;
	private static final int CAMERA_REQUEST = 1888;

	Bitmap bm = null;
	String selectedImagePath = null;
	Uri mCapturedImageURI;
	
	String item[] = {"beef", "pork", "chicken", "vegetable", "fruit", "fish", "meat"};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_grocery, container, false);
		
		dao = new Food_DAO(getActivity());
		c = Calendar.getInstance();
		
		image = (ImageView) rootView.findViewById(R.id.foodimage);
		selectimage = (ImageView) rootView.findViewById(R.id.selectimage);
		selectimage.setOnClickListener(new OnClickListener() { 
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, REQUEST_CODE);
			}
		});
		
		captureimage = (ImageView) rootView.findViewById(R.id.captureimage);
		captureimage.setOnClickListener(new OnClickListener() { 
			public void onClick(View arg0) {
				try {
					String fileName = c.get(Calendar.YEAR) + c.get(Calendar.MONTH) + c.get(Calendar.DAY_OF_MONTH) + c.get(Calendar.HOUR) + c.get(Calendar.MINUTE) + c.get(Calendar.SECOND) + ".jpg";
					ContentValues values = new ContentValues();
					values.put(MediaStore.Images.Media.TITLE, fileName);
					mCapturedImageURI = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
					startActivityForResult(intent, CAMERA_REQUEST);
				} catch (Exception e) {
				}
			}
		});
		
		foodname = (EditText) rootView.findViewById(R.id.foodname);
		
		foodtype = (AutoCompleteTextView) rootView.findViewById(R.id.foodtype);
		foodtype.addTextChangedListener(this);
		foodtype.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, item));
		
		quantity = (EditText) rootView.findViewById(R.id.quantity);
		
		datePicker = (DatePicker) rootView.findViewById(R.id.purchasedate);
		datePicker.setCalendarViewShown(false);
		
		datePicker2 = (DatePicker) rootView.findViewById(R.id.expirydate);
		datePicker2.setCalendarViewShown(false);
		
		submit = (Button) rootView.findViewById(R.id.foodsubmit);
		submit.setOnClickListener(new OnClickListener() { 
			public void onClick(View arg0) {
				int day = datePicker.getDayOfMonth();
			    int month = datePicker.getMonth();
			    int year =  datePicker.getYear() - 1900;
			    
			    int day2 = datePicker2.getDayOfMonth();
			    int month2 = datePicker2.getMonth();
			    int year2 =  datePicker2.getYear() - 1900;
			    
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			    
			    date1 = new Date(year, month, day);
			    date2 = new Date(year2, month2, day2);
			    
			    dateString = sdf.format(new Date(year, month, day));
			    dateString2 = sdf.format(new Date(year2, month2, day2));
			    
			    if(bm != null)
			    	if(date1.after(date2))
			    		Toast.makeText(getActivity(), "The expiry date is earlier than the purchase date.", Toast.LENGTH_SHORT).show();
			    	else
			    		check();
				else
					Toast.makeText(getActivity(), "You have not entered a photo for this food.", Toast.LENGTH_SHORT).show();
			}
		});

		return rootView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == getActivity().RESULT_OK) {
			if (requestCode == REQUEST_CODE) {
				mCapturedImageURI = data.getData();
			}
			selectedImagePath = getPath(mCapturedImageURI);
			bm = reduceImageSize(selectedImagePath);
			if(bm != null)
				image.setImageBitmap(bm);
		}
	}
	
	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	public Bitmap reduceImageSize(String mSelectedImagePath){

		Bitmap m = null;
		try {
			File f = new File(mSelectedImagePath);

			//Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			m = BitmapFactory.decodeStream(new FileInputStream(f),null,o);

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
			Toast.makeText(getActivity(), "Image File not found in your phone. Please select another image.", Toast.LENGTH_LONG).show();
		}
		return  m;
	}
	
	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
	}
	
	public void check(){
		String mName = foodname.getText().toString();
		String mType = foodtype.getText().toString();
		String mQuantity = quantity.getText().toString();
		
		boolean cancel = false;
		View focusView = null;
		
		if(TextUtils.isEmpty(mName)){
			foodname.setError("This field is missing.");
			focusView = foodname;
			cancel = true;
		}
		if(TextUtils.isEmpty(mType)){
			foodtype.setError("This field is missing.");
			focusView = foodtype;
			cancel = true;
		}
		if(TextUtils.isEmpty(mQuantity)){
			quantity.setError("This field is missing.");
			focusView = quantity;
			cancel = true;
		}
		if(cancel){
			focusView.requestFocus();
		}
		else{
			Food f = new Food();
			
			f.setImage(selectedImagePath);
			f.setFoodname(foodname.getText().toString());
			f.setFoodtype(foodtype.getText().toString());
			f.setQuantity(Integer.parseInt(quantity.getText().toString()));
			f.setPdate(dateString);
			f.setEdate(dateString2);
			
			selectedImagePath = null;
			image.setImageBitmap(null);
			foodname.setText("");
			foodtype.setText("");
			quantity.setText("");
			
			datePicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
			datePicker2.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
			
			dao.addFood(f);
		}
	}

}
