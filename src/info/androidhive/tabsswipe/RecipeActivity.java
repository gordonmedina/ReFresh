package info.androidhive.tabsswipe;

import info.androidhive.tabsswipe.adapter.ConnectionChecker;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecipeActivity extends Activity {
	
	TextView recipename, recipetype, recipeingredient, recipeprocedure, recipevideo;
	ImageView recipeimage;
	
	int recipeid;
	
	Recipe recipe;
	
	Food_DAO dao;
	
	ConnectionChecker check;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe);
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			recipeid = extras.getInt("id");
		}
		
		check = new ConnectionChecker(this);
		
		dao = new Food_DAO(this);
		
		recipe = dao.getRecipe(recipeid);
		
		recipeimage = (ImageView) findViewById(R.id.recipe_page_image);
		recipeimage.setImageResource(recipe.getImageid());
		
		recipename = (TextView) findViewById(R.id.recipe_page_name);
		recipetype = (TextView) findViewById(R.id.recipe_page_type);
		recipeingredient = (TextView) findViewById(R.id.recipe_page_ingredient);
		recipeprocedure = (TextView) findViewById(R.id.recipe_page_procedure);
		recipevideo = (TextView) findViewById(R.id.recipe_page_video);
		
		recipename.setText(recipe.getName());
		recipetype.setText(recipe.getType());
		recipeingredient.setText(recipe.getIngredient());
		recipeprocedure.setText(recipe.getProcedure());
		
		recipevideo.setText(recipe.getVideo());
		recipevideo.setOnClickListener(new OnClickListener() { 
			public void onClick(View arg0) {
				if(check.isConnectingToInternet())
				{
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(recipe.getVideo())));
				}
				else
					Toast.makeText(RecipeActivity.this, "You are not connected to the internet.", Toast.LENGTH_SHORT).show();
			}
		});
		
	}

}
