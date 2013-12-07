package info.androidhive.tabsswipe;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Food_DAO extends SQLiteOpenHelper {

	final static String DATABASE_NAME = "food.db";
	final static int DATABASE_VERSION = 1;

	public static final String TABLE_NAME = "food";
	public static final String COLUMN_IMAGE = "image";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "foodname";
	public static final String COLUMN_TYPE = "foodtype";
	public static final String COLUMN_QUANTITY = "quantity";
	public static final String COLUMN_PDATE = "pdate";
	public static final String COLUMN_EDATE = "edate";

	public static final String syntax = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_IMAGE
			+ " TEXT NOT NULL, " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME
			+ " TEXT NOT NULL, " + COLUMN_TYPE + " TEXT NOT NULL, "
			+ COLUMN_QUANTITY + " INTEGER, " + COLUMN_PDATE
			+ " TEXT NOT NULL, "  + COLUMN_EDATE
			+ " TEXT NOT NULL );";
	
//	public static final String TABLE_NAME2 = "recipe";
//	public static final String COLUMN_IMAGE2 = "image";
//	public static final String COLUMN_ID2 = "id";
//	public static final String COLUMN_NAME2 = "recipename";
//	public static final String COLUMN_TYPE2 = "recipetype";
//	public static final String COLUMN_INGREDIENT2 = "recipeingredient";
//	public static final String COLUMN_PROCEDURE2 = "recipeprocedure";
//
//	public static final String syntax2 = "CREATE TABLE " + TABLE_NAME2 + "(" + COLUMN_IMAGE2
//			+ " INTEGER NOT NULL, " + COLUMN_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME2
//			+ " TEXT NOT NULL, " + COLUMN_TYPE2 + " TEXT NOT NULL, "
//			+ COLUMN_INGREDIENT2 + " INTEGER, " + COLUMN_PROCEDURE2
//			+ " TEXT NOT NULL);";

	public Food_DAO(Context c) {
		super(c, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(syntax);
//		db.execSQL(syntax2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
		onCreate(db);
	}

	public void addFood(Food f) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put(COLUMN_IMAGE, f.getImage());
		cv.put(COLUMN_NAME, f.getFoodname());
		cv.put(COLUMN_TYPE, f.getFoodtype());
		cv.put(COLUMN_QUANTITY, f.getQuantity());
		cv.put(COLUMN_PDATE, f.getPdate());
		cv.put(COLUMN_EDATE, f.getEdate());

		db.insert(TABLE_NAME, null, cv);

		db.close();
	}
	
	public List<Food> getAllFood() {
		List<Food> sl = new ArrayList<Food>();
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_EDATE, null);

		c.moveToFirst();
		while (!c.isAfterLast()) {
			sl.add(cursorToFood(c));
			c.moveToNext();
		}

		db.close();

		return sl;

	}
	
	public Food getFood(int id) {
		Food f = new Food();
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id = " + id, null);

		c.moveToFirst();
		f = cursorToFood(c);

		db.close();

		return f;
	}

	public void deleteFood(Food f) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { String.valueOf(f.getId()) });
        
        db.close();
    }

	private Food cursorToFood(Cursor c) {
		// TODO Auto-generated method stub
		Food s = new Food();

		s.setImage(c.getString(0));
		s.setId(c.getInt(1));
		s.setFoodname(c.getString(2));
		s.setFoodtype(c.getString(3));
		s.setQuantity(c.getInt(4));
		s.setPdate(c.getString(5));
		s.setEdate(c.getString(6));

		return s;
	}

	
//	public void addRecipe(Recipe f) {
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		ContentValues cv = new ContentValues();
//		cv.put(COLUMN_IMAGE2, f.getImageid());
//		cv.put(COLUMN_NAME2, f.getName());
//		cv.put(COLUMN_TYPE2, f.getType());
//		cv.put(COLUMN_INGREDIENT2, f.getIngredient());
//		cv.put(COLUMN_PROCEDURE2, f.getProcedure());
//
//		db.insert(TABLE_NAME2, null, cv);
//
//		db.close();
//	}
//	
//	public List<Recipe> getAllRecipe() {
//		List<Recipe> sl = new ArrayList<Recipe>();
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME2, null);
//
//		c.moveToFirst();
//		while (!c.isAfterLast()) {
//			sl.add(cursorToRecipe(c));
//			c.moveToNext();
//		}
//
//		db.close();
//
//		return sl;
//
//	}
//	
//	public Recipe getRecipe(int id) {
//		Recipe f = new Recipe();
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME2 + " WHERE id = " + id, null);
//
//		c.moveToFirst();
//		f = cursorToRecipe(c);
//
//		db.close();
//
//		return f;
//	}
//
//	public void deleteRecipe(Recipe f) {
//        SQLiteDatabase db = this.getWritableDatabase();
// 
//        db.delete(TABLE_NAME2, COLUMN_ID2 + " = ?", new String[] { String.valueOf(f.getId()) });
//        
//        db.close();
//    }
//
//	private Recipe cursorToRecipe(Cursor c) {
//		// TODO Auto-generated method stub
//		Recipe s = new Recipe();
//
//		s.setImageid(c.getInt(0));
//		s.setId(c.getInt(1));
//		s.setName(c.getString(2));
//		s.setType(c.getString(3));
//		s.setIngredient(c.getString(4));
//		s.setProcedure(c.getString(5));
//
//		return s;
//	}
	
	public Recipe getRecipe(int id){
		List<Recipe> recipeList = getAllRecipe();
		return recipeList.get(id);
	}
	
	public List<Recipe> getAllRecipe(){
		List<Recipe> recipeList = new ArrayList<Recipe>();
		
		Recipe onerecipe = new Recipe();
		onerecipe.setImageid(R.drawable.food1);
		onerecipe.setId(0);
		onerecipe.setName("Adobo");
		onerecipe.setType("Meat");
		onerecipe.setIngredient("4-5 lbs. chicken thighs\n\n1/2 cup white vinegar\n\n1/2 cup soy sauce\n\n4 cloves garlic, crushed\n\n1 tsp. black peppercorns\n\n3 bay leaves");
		onerecipe.setProcedure("Combine all ingredients in a large pot. Cover and marinate chicken for 1-3 hours. Bring to boil, then lower heat. Cover and let simmer for 30 minutes, stirring occasionally. Uncover and simmer until sauce is reduced and thickened, and chicken is tender, about 20 more minutes. Serve with steamed rice.");
    	onerecipe.setVideo("http://www.youtube.com/watch?v=KiZfxq-ZyWI");
		recipeList.add(onerecipe);
		
    	onerecipe = new Recipe();
		onerecipe.setImageid(R.drawable.food2);
		onerecipe.setId(1);
		onerecipe.setName("Kare-kare");
		onerecipe.setType("Meat");
		onerecipe.setIngredient("3 lbs oxtail (cut in 2 inch slices) you an also use tripe or beef slices\n\n1 small banana flower bud (sliced)\n\n1 bundle of pechay or bok choy\n\n1 bundle of string beans (cut into 2 inch slices)\n\n4 pcs eggplants (sliced)\n\n1 cup ground peanuts\n\n1/2 cup peanut butter\n\n1/2 cup shrimp paste\n\n34 Ounces water (about 1 Liter)\n\n1/2 cup annatto seeds (soaked in a cup of water)\n\n1/2 cup toasted ground rice\n\n1 tbsp garlic, minced\n\n1 large onion, chopped\n\nsalt and pepper");
		onerecipe.setProcedure("In a large pot, bring the water to a boil.\n\nPut in the oxtail followed by the onions and simmer for 2.5 to 3 hrs or until tender (35 minutes if using a pressure cooker).\n\nOnce the meat is tender, add the ground peanuts, peanut butter, and coloring (water from the annatto seed mixture) and simmer for 5 to 7 minutes.\n\nAdd the toasted ground rice and simmer for 5 minutes.\n\nOn a separate pan, saute the garlic then add the banana flower, eggplant, and string beans and cook for 5 minutes.\n\nTransfer the cooked vegetables to the large pot (where the rest of the ingredients are).\n\nAdd salt and pepper to taste.\n\nServe hot with shrimp paste.");
		onerecipe.setVideo("http://www.youtube.com/watch?v=KiZfxq-ZyWI");
		recipeList.add(onerecipe);
    	
    	onerecipe = new Recipe();
		onerecipe.setImageid(R.drawable.food3);
		onerecipe.setId(2);
		onerecipe.setName("Sinigang na Baboy");
		onerecipe.setType("Meat / Soup");
		onerecipe.setIngredient("2 Lbs of Pork Belly cut in 2 in. cubes (Liempo)\n\n1 Small radish cut in 2 in. small pieces (Labanos/Daikon)\n\n1 Medium Onion sliced thinly\n\n1 Small tomato diced\n\n1 Eggplant cut diagonally\n\n1 Cup of Sitaw cut in 2 in. length (String Beans)\n\n3 Cups of Kangkong (Water Spinach)\n\n2 Pcs. Of Siling Mahaba (Finger Peppers)\n\n1½ Packet of Knorr Sinigang Mix\n\n1 Tsp. MSG (Vetsin)\n\nSalt to taste\n\n1½ Quarts of water");
		onerecipe.setProcedure("In a large pot add water and bring to a boil. Next add pork, cover, and let it simmer for 3 minutes.\n\nRemove scum from the broth and then add onions, tomatoes, siling mahaba, (finger peppers) msg, (vetsin) and the sinigang mix and let cook for 10 minutes.\n\nThen add the Labanos, (radish) cover, and let it simmer for another 10 minutes.\n\nAdd eggplant and cook it for an additional 10 minutes after adding the eggplant wait five minutes then add the string beans (sitaw).\n\nFinally add the water spinach (Kangkong) and add salt if needed. Let it cook for three more minutes and it’s ready.\n\nServe with rice.");
		onerecipe.setVideo("http://www.youtube.com/watch?v=KiZfxq-ZyWI");
		recipeList.add(onerecipe);
		
    	onerecipe = new Recipe();
		onerecipe.setImageid(R.drawable.food4);
		onerecipe.setId(3);
		onerecipe.setName("Tinolang Manok");
		onerecipe.setType("Meat / Soup");
		onerecipe.setIngredient("2 lbs chicken (cut into serving pieces)\n\n1 thumb size ginger root cut into strips\n\n3 cloves garlic (minced)\n\n1 onion (sliced)\n\n2 small to medium sayote/chayote or green papaya (cut into wedges)\n\n1/2 cup pepper leaves\n\n1 Tbsp Fish Sauce (Patis)\n\n3 cups of water\n\n1 chicken cube\n\n2 tbsp coking oil");
		onerecipe.setProcedure("In a saucepan, saute the garlic, onion & ginger in cooking oil.\n\nAdd the chicken and cook for 5 minutes or until the chicken colors lightly.\n\nAdd the fish sauce.\n\nPour-in water and bring to a boil. Simmer until chicken is done about 30 minutes.\n\nAdd the chicken cube and sayote/chayote. Continue simmering until sayote/chayote is tender.\n\nAdd pepper leaves and salt to taste.\n\nRemove from heat.\n\nServe hot. Enjoy!");
		onerecipe.setVideo("http://www.youtube.com/watch?v=KiZfxq-ZyWI");
		recipeList.add(onerecipe);
    	
    	onerecipe = new Recipe();
		onerecipe.setImageid(R.drawable.food5);
		onerecipe.setId(4);
		onerecipe.setName("Sisig");
		onerecipe.setType("Meat");
		onerecipe.setIngredient("2 lbs pig ears\n\n¼ lb pork belly\n\n1 cup onion, minced\n\n3 tbsp soy sauce\n\n1 tsp ground black pepper\n\n1 knob ginger, minced\n\n3 tbsp chili\n\n1 piece lemon (or 3 to 5 pieces calamansi)\n\n½ cup butter (or margarine)\n\n¼ lb chicken liver\n\n34 ounces water\n\n3 tbsp mayonnaise\n\n1 tsp salt");
		onerecipe.setProcedure("Pour the water in a pan and bring to a boil.\n\nAdd salt and pepper.\n\nPut-in the pig’s ears and pork belly then simmer for 40 minutes to 1 hour (or until tender).\n\nRemove the boiled ingredients from the pot then drain excess water.\n\nGrill the boiled pig ears and pork belly until done.\n\nChop the pig ears and pork belly into fine pieces.\n\nIn a pan, melt the butter then add the onions. Cook until onions are soft.\n\nPut-in the ginger and cook for a few minutes.\n\nAdd the chicken liver and cook until well done.\n\nCrush the chicken liver while being cooked in the pan.\n\nAdd the chopped pig ears and pork belly then cook for 10 minutes.\n\nPut-in the soy sauce and chili then mix well.\n\nAdd salt and pepper to taste.\n\nPut-in the mayonnaise and mix with the other ingredients.\n\nServe hot.");
		onerecipe.setVideo("http://www.youtube.com/watch?v=KiZfxq-ZyWI");
		recipeList.add(onerecipe);
		
    	onerecipe = new Recipe();
		onerecipe.setImageid(R.drawable.food6);
		onerecipe.setId(5);
		onerecipe.setName("Nilagang Baka");
		onerecipe.setType("Meat / Soup");
		onerecipe.setIngredient("1 kilo of beef meat (brisket, shank,short ribs), cut into serving pieces\n\n3 medium-sized potatoes, cut into quarter\n\n5 pieces cardava banana(saging na saba)\n\n3 Japanese corn cobs, cut into 3-4 pieces\n\n1 small bundle green beans(baguio beans), trimmed\n\n1 bundle bok choy (Pechay) , cut the 2 pieces\n\n1 onion, chopped\n\n1 tablespoon peppercorn\n\nfish sauce(patis) or salt to taste\n\n1 liter of water\n\n1/2 small cabbage, cut into quarter(optional)\n\n");
		onerecipe.setProcedure("In a large casserole, place the meat and add water. Set over medium heat and bring to a boil and simmer for about 1 hour or until meat is tender. Remove the scum as it rises. Add more water if necessary. Pressure cooker only takes 30 minutes.\n\nAdd corn and onion. Season with fish sauce and sprinkle some salt. Simmer for another 10 minutes or until the corn is tender.\n\nPut potatoes and continue cooking until potatoes are cooked.\n\nThen add cardava banana and all the vegetables. Do not cover and simmer for about 5 minutes or until vegetables are done. Do not overcooked the vegetables.\n\n Adjust seasoning with fish sauce and salt according to taste.\n\nServe hot with fish sauce and birds eye chili (siling labuyo) for condiments.");
		onerecipe.setVideo("http://www.youtube.com/watch?v=KiZfxq-ZyWI");
		recipeList.add(onerecipe);
    	
    	onerecipe = new Recipe();
		onerecipe.setImageid(R.drawable.food7);
		onerecipe.setId(6);
		onerecipe.setName("Bistek Tagalog");
		onerecipe.setType("Meat");
		onerecipe.setIngredient("1 lb beef sirloin, thinly sliced\n\n1/4 cup soy sauce\n\n1 piece lemon or 3 pieces calamansi\n\n1/2 tsp ground black pepper\n\n3 cloves garlic, crushed\n\n1 large onion, sliced into rings\n\n3 tbsp cooking oil\n\nsalt to taste");
		onerecipe.setProcedure("Marinade beef in soy sauce, lemon (or calamansi), and ground black pepper for at least 1 hour.\n\nHeat the cooking oil in a pan then stir fry the onion rings until the texture becomes soft. Set aside.\n\nIn the same pan where the onions were fried, fry the marinated beef (without the marinade) until color turns brown. Set aside.\n\nPut-in the garlic then saute for a few minutes.\n\nPour the marinade and bring to a boil.\n\nPut in the fried beef and simmer for 15 to 20 minutes or until meat is tender. Add water as needed.\n\nAdd the stir-fried onions and some salt to taste.");
		onerecipe.setVideo("http://www.youtube.com/watch?v=KiZfxq-ZyWI");
		recipeList.add(onerecipe);
		
    	onerecipe = new Recipe();
		onerecipe.setImageid(R.drawable.food8);
		onerecipe.setId(7);
		onerecipe.setName("Bulalo");
		onerecipe.setType("Meat / Soup");
		onerecipe.setIngredient("2 lbs beef shank\n\n½ pc small cabbage, whole leaf individually detached\n\n1 small bundle Pechay\n\n3 pcs Corn, each cut into 3 parts\n\n2 tbsp Whole pepper corn\n\n1/2 cup Green onions\n\n1 medium sized onion\n\n34 ounces water\n\n2 tbsp fish sauce (optional)\n\n");
		onerecipe.setProcedure("In a big cooking pot, pour in water and bring to a boil.\n\nPut-in the beef shank followed by the onion and whole pepper corn then simmer for 1.5 hours (30 mins if using a pressure cooker) or until meat is tender.\n\nAdd the corn and simmer for another 10 minutes.\n\nAdd the fish sauce, cabbage, pechay, and green onion (onion leeks).\n\n");
		onerecipe.setVideo("http://www.youtube.com/watch?v=KiZfxq-ZyWI");
		recipeList.add(onerecipe);
    	
    	onerecipe = new Recipe();
		onerecipe.setImageid(R.drawable.food9);
		onerecipe.setId(8);
		onerecipe.setName("Pinakbet");
		onerecipe.setType("Vegetables");
		onerecipe.setIngredient("1 lb Pork Belly, cubed\n\nWater, for boiling\n\n1/4 of a Yellow Onion, sliced\n\n2 Garlic Cloves, finely minced\n\n1 Roma Tomato, diced\n\n2-3 tbsps Filipino Salted Shrimp Fry (Bagoong Alamang)\n\n1 large Bittermelon, seeds removed & sliced\n\n2 large Japanese Eggplant, quartered lengthwise & chopped into segments\n\n1/2 Calabasa Squash, seeds removed & diced\n\n1 bunch Long Beans, ends removed & chopped into segments\n\n8-10 Fushimi Peppers\n\n1/2 lb Okra\n\n2 bundles Squash Blossoms, stems trimmed & leaves/pistils discarded\n\n");
		onerecipe.setProcedure("Place the pork belly in a small pot, then add just enough water to cover the meat. Bring to a boil, cover, and cook for 20-25 minutes. Once the meat is tender, remove it from the pot and set aside. Save the cooking liquid.\n\nIn a separate large pot or wok, heat 1 tbsp of vegetable oil. Add the onion, garlic, and tomato, and saute for 1 minute. Then, add the pork belly and the shrimp paste. Saute for another minute. Next, add the bittermelon, eggplant, squash, and beans. Saute this mixture for 1 more minute, and stir lightly just to combine all of the vegetables. Add the liquid used to cook the pork belly, then cover and cook for 10-15 minutes. For the last 5 minutes, place the peppers, okra, and blossoms on top, return the lid, and cook until everything is tender.");
		onerecipe.setVideo("http://www.youtube.com/watch?v=KiZfxq-ZyWI");
		recipeList.add(onerecipe);
		
    	onerecipe = new Recipe();
		onerecipe.setImageid(R.drawable.food10);
		onerecipe.setId(9);
		onerecipe.setName("Menudo");
		onerecipe.setType("Meat");
		onerecipe.setIngredient("2 lbs pork\n\n1/2 lb pig’s liver\n\n1 cup carrots, diced\n\n1 cup potatoes, diced\n\n1/2 cup soy sauce\n\n1/2 piece lemon\n\n1 small onion, chopped\n\n3 cloves garlic, minced\n\n1 teaspoon sugar\n\n3/4 cup tomato sauce\n\n1 cup water\n\n4 pieces hotdogs, sliced diagonally\n\n2 tablespoons cooking oil\n\n2 to 3 pieces dried bay leaves\n\nsalt and pepper to taste\n\n");
		onerecipe.setProcedure("Combine pork, soysauce, and lemon in a bowl. Marinate for at least 1 hour.\n\nHeat oil in a pan.\n\nSaute garlic and onion.\n\nAdd the marinated pork. Cook for 5 to 7 minutes.\n\nPour in tomato sauce and water and then add the bay leaves.Let boil and simmer for 30 minutes to an hour depending on the toughness of the pork. Note: Add water as necessary.\n\nAdd-in the liver and hot dogs.Cook for 5 minutes.\n\nPut-in potatoes, carrots, sugar,salt, and pepper. Stir and cook for 8 to 12 minutes.");
		onerecipe.setVideo("http://www.youtube.com/watch?v=KiZfxq-ZyWI");
		recipeList.add(onerecipe);
    	
    	onerecipe = new Recipe();
		onerecipe.setImageid(R.drawable.food11);
		onerecipe.setId(10);
		onerecipe.setName("Beef Kaldereta");
		onerecipe.setType("Meat");
		onerecipe.setIngredient("2 lbs beef, cubed\n\n3 garlic cloves, crushed and chopped\n\n1 onion, finely chopped\n\n4 cups water\n\n1 cup red bell pepper, cut into strips\n\n1 cup tomato sauce\n\n1/2 cup liver spread or liver paste (processed using blender)\n\n1 tsp. crushed chili\n\n3 large bay leaves\n\n2 cups potatoes, sliced\n\n2 cups carrots, sliced\n\n1 cup cooking oil\n\n2/3 cup green olives\n\nsalt and pepper to taste");
		onerecipe.setProcedure("Heat the cooking oil in the pan and fry the carrots and potatoes until color turns light brown.\n\nRemove the fried carrots and potatoes from the pan and set aside.\n\nIn the same pan where the vegetables were fried, sauté the garlic and onions.\n\nAdd the beef and simmer for 5 minutes.\n\nAdd water and let the beef boil until tender (about 30 mins if using pressure cooker or 1 to 2 hours if using an ordinary pot).\n\nAdd the tomato sauce and liver spread and simmer for 10 minutes.\n\nAdd green olives, carrots, bay leaves, bell pepper, crushed chili, and potatoes and simmer for 5 to 8 minutes.\n\nAdd salt and pepper to taste.");
		onerecipe.setVideo("http://www.youtube.com/watch?v=KiZfxq-ZyWI");
		recipeList.add(onerecipe);
		
    	onerecipe = new Recipe();
		onerecipe.setImageid(R.drawable.food12);
		onerecipe.setId(11);
		onerecipe.setName("Paksiw na Bangus");
		onerecipe.setType("Fish");
		onerecipe.setIngredient("1 kilo milk fish (bangus), cleaned but not scaled cut in about 4-5 slices\n\n1 bitter melon (ampalaya), sliced\n\n1 eggplant (talong), sliced\n\n2 cloves garlic, crushed\n\n1 small onion, sliced\n\n1 thumb-sized ginger (luya), sliced and crushed\n\n3 pieces long green pepper (siling haba)\n\n1/2 cup vinegar\n\n1 cup water\n\n1 teaspoon peppercorn (pamintang buo)\n\n2 teaspoon salt or fish sauce (patis)\n\n1 teaspoon cooking oil");
		onerecipe.setProcedure("Remove the gills and innards of milk fish but do not the scales. Using a scissor or knife cut the fins and tails. Wash fish thoroughly drain and slice diagonally.\n\nIn a casserole place the garlic, onion and ginger.\n\nArrange sliced milk fish, add water, vinegar, salt, peppercorn and cooking oil.\n\nCover and simmer for 10 minutes.\n\nPut the vegetables and long green pepper. Cover and simmer in a medium heat for another 5 minutes.");
		onerecipe.setVideo("http://www.youtube.com/watch?v=KiZfxq-ZyWI");
		recipeList.add(onerecipe);
    	
    	onerecipe = new Recipe();
		onerecipe.setImageid(R.drawable.food13);
		onerecipe.setId(12);
		onerecipe.setName("Pork Estofado");
		onerecipe.setType("Meat");
		onerecipe.setIngredient("3 lbs pork, cubed\n\n3 pieces dried bay leaves\n\n1 tablespoon whole peppercorn\n\n1 1/2 cup carrot, sliced\n\n4 plantain bananas, sliced diagonally (1 inch thick)\n\n1/2 cup vinegar\n\n3/4 cup soy sauce\n\n3 tablespoons brown sugar\n\n1 cup water\n\n5 tablespoons minced garlic\n\n1 cup cooking oil");
		onerecipe.setProcedure("Heat a frying pan and pour 3/4 cups of cooking oil.\n\nWhen the oil is hot enough, fry the sliced plantains until the color of each side turns medium to dark brown. Set aside. Pour 1/4 cup of cooking oil in a separate cooking pot then apply heat.\n\nWhen the oil is hot enough, put-in the garlic and sauté until the color turns light brown.\n\nAdd the cubed pork and cook for 7 to 10 minutes.\n\nPut-in the soy sauce, water, whole peppercorns, and dried bay leaves then bring to a boil. Simmer until pork is tender.\n\nAdd vinegar and wait for the liquid to re-boil. Simmer for 5 minutes.\n\nAdd brown sugar and carrots. Stir then simmer for 10 minutes more.\n\nTurn off the heat and transfer the contents of the cooking pot to a serving plate.\n\nGarnish with fried bananas then serve.");
		onerecipe.setVideo("http://www.youtube.com/watch?v=KiZfxq-ZyWI");
		recipeList.add(onerecipe);
		
    	onerecipe = new Recipe();
		onerecipe.setImageid(R.drawable.food14);
		onerecipe.setId(13);
		onerecipe.setName("Embutido");
		onerecipe.setType("Meat");
		onerecipe.setIngredient("2 lbs ground pork\n\n12 pcs vienna sausage or 6 pcs hotdogs, cut in half lengthwise\n\n3 pcs hard boiled eggs, sliced\n\n1/2 cup sweet pickle relish\n\n1/2 cup tomato sauce\n\n2 pcs raw eggs\n\n2 cups cheddar cheese, grated\n\n1 cup red bell pepper, minced\n\n1 cup green bell pepper, minced\n\n1 1/2 cup raisins\n\n1 cup carrots, minced\n\n1 cup onion, minced\n\nsalt and pepper\n\n2 cups bread crumbs (made by placing 4 slices of tasty bread in a food processor. If not using any food processor, just tear the bread.)\n\n");
		onerecipe.setProcedure("Place the ground pork in a large container.\n\nAdd the bread crumbs then break the raw eggs and add it in. Mix well.\n\nPut-in the carrots, bell pepper (red and green), onion, pickle relish, and cheddar cheese. Mix thouroughly.\n\nAdd the raisins, tomato sauce, salt, and pepper then mix well.\n\nPlace the meat mixture in an aluminum foil and flatten it.\n\nPut in the sliced vienna sausage and sliced boiled eggs alternately on the middle of the flat meat mixture.\n\nRoll the foil to form a cylinder-locking the sausage and egg in the middle if the meat mixture. Once done, lock the edges of the foil.\n\nPlace in a steamer and let cook for 1 hour.\n\nPlace inside the refrigerator until temperature turns cold.");
		onerecipe.setVideo("http://www.youtube.com/watch?v=KiZfxq-ZyWI");
		recipeList.add(onerecipe);
    	
    	onerecipe = new Recipe();
		onerecipe.setImageid(R.drawable.food15);
		onerecipe.setId(14);
		onerecipe.setName("Beef Mechado");
		onerecipe.setType("Meat");
		onerecipe.setIngredient("3 cloves garlic, crushed\n\n1 piece large onion, sliced\n\n2 lbs beef chuck, cubed\n\n8 ounces tomato sauce\n\n1 cup water\n\n3 tbsp cooking oil\n\n1 slice lemon with rind\n\n1 piece large potato, cubed\n\n1/4 cup soy sauce\n\n1/2 tsp. ground black pepper\n\n2 pcs. bay leaves (laurel)\n\nsalt to taste");
		onerecipe.setProcedure("Heat cooking oil in a pan then saute the garlic and onion.\n\nPut-in the beef and saute for about 3 minutes or until color turns light brown.\n\nAdd the tomato sauce and water then simmer until the meat is tender. Add water as needed.\n\nAdd the soy sauce, ground black pepper, lemon rind, laurel leaves, and salt then simmer until excess liquid evaporates.\n\nPut-in the potatoes and cook until the potatoes are soft (about 15 to 25 minutes).");
		onerecipe.setVideo("http://www.youtube.com/watch?v=KiZfxq-ZyWI");
		recipeList.add(onerecipe);
		
    	onerecipe = new Recipe();
		onerecipe.setImageid(R.drawable.food16);
		onerecipe.setId(15);
		onerecipe.setName("Pochero");
		onerecipe.setType("Meat");
		onerecipe.setIngredient("1 lb pork belly, chopped\n\n2 medium tomatoes, diced\n\n1 medium onion, diced\n\n1 teaspoon garlic, minced\n\n2 to 2 1/2 tablespoons patis (fish-sauce)\n\n1 tablespoon whole pepper corn\n\n1 small can tomato sauce\n\n1 cup chick peas (garbanzos)\n\n1 large plaintain banana (ripe), chopped\n\n1 medium sized potato, cubed\n\n1 small cabbage, quartered\n\n1/4 lb long green beans\n\n1 bunch bok choy (pechay)\n\n1 cup water\n\n2 tablespoons cooking oil");
		onerecipe.setProcedure("Heat cooking oil in a cooking pot.\n\nSauté garlic, onions, and tomatoes.\n\nAdd pork and cook until the color turns light brown.\n\nPut-in fish sauce, whole pepper corn, and tomato sauce. Stir.\n\nAdd water and let boil. Simmer until pork is tender (about 30 to 40 minutes).\n\nPut-in potato, plantain, and chick peas. Cook for 5 to 7 minutes.\n\nAdd cabbage and long green beans. Cook for 5 minutes.\n\nStir-in the bok choy. Cover the pot and turn off the heat.\n\nLet the residual heat cook the bok choy (about 5 minutes).");
		onerecipe.setVideo("http://www.youtube.com/watch?v=KiZfxq-ZyWI");
		recipeList.add(onerecipe);
    	
    	onerecipe = new Recipe();
		onerecipe.setImageid(R.drawable.food17);
		onerecipe.setId(16);
		onerecipe.setName("Chicken Afritada");
		onerecipe.setType("Meat");
		onerecipe.setIngredient("2 lbs chicken, cut into serving pieces\n\n1 large potato, quartered\n\n1 large carrot, sliced\n\n1 large bell pepper, sliced\n\n1 cup green peas\n\n8 ounces tomato sauce\n\n4 cloves garlic, minced\n\n4 pieces hotdogs, sliced\n\n1 medium onion, diced\n\n1 1/4 cups chicken broth or stock\n\n2 pieces dried bay leaves\n\n1 teaspoon granulated white sugar\n\n4 tablespoons cooking oil\n\nSalt and ground black pepper to taste");
		onerecipe.setProcedure("Pour-in cooking oil in a cooking pot or casserole then apply heat.\n\nPan-fry the chicken until the color turns light brown (about 3 minutes per side).\n\nRemove the chicken from the cooking pot.\n\nOn the same cooking pot, sauté garlic and onions.\n\nPut-in the sliced hotdogs and pan-fried chicken then cook for a minute.\n\nPour-in tomato sauce and chicken stock then add bay leaves. Simmer for 30 to 40 minutes.\n\nAdd the potato and carrots then simmer for 7 minutes or until the vegetables are soft.\n\nPut-in the bell pepper, green peas, salt, sugar, and ground black pepper then cook for 3 minutes more.\n\nTurn-off heat then transfer to a serving plate.");
		onerecipe.setVideo("http://www.youtube.com/watch?v=KiZfxq-ZyWI");
		recipeList.add(onerecipe);
		
    	onerecipe = new Recipe();
		onerecipe.setImageid(R.drawable.food18);
		onerecipe.setId(17);
		onerecipe.setName("Monggo Guisado");
		onerecipe.setType("Vegetables");
		onerecipe.setIngredient("1 1/2 cups Mung beans\n\n1 tbsp garlic\n\n½ lb pork, thinly sliced\n\n2 cups spinach\n\n1 pc medium sized tomato, chopped\n\n1 medium sized onion, chopped\n\n5 to 8 pcs medium sized shrimp (optional)\n\n2 tbsp fish sauce\n\n24 ounces water (for boiling)\n\n1 pc beef cube or 1 teaspoon beef powder (for flavoring)\n\n½ cup crushed pork rind (chicharon)\n\n1/4 teaspoon ground black pepper");
		onerecipe.setProcedure("In a pan, put-in the water and bring to a boil.\n\nPut-in the Mung beans and simmer until becomes soft (about 35 to 50 minutes).\n\nOn a separate pan, sauté the garlic, onion, and tomato.\n\nAdd the pork. Cook for 5 mins.\n\nPut-in the beef cube and fish sauce. Simmer for 10 mins or until the meat is tender. Note: If necessary, you may add water to help make the meat tender but make sure to add more time to simmer.\n\nAdd the shrimp. Stir and then cook for 2 minutes.\n\nPour the cooked Mung beans. Stir and then simmer for 10 minutes.\n\nAdd the spinach and pork rinds (chicharon).\n\nSprinkle the ground black pepper.");
		onerecipe.setVideo("http://www.youtube.com/watch?v=KiZfxq-ZyWI");
		recipeList.add(onerecipe);
    	
    	onerecipe = new Recipe();
		onerecipe.setImageid(R.drawable.food19);
		onerecipe.setId(18);
		onerecipe.setName("Relyenong Bangus");
		onerecipe.setType("Fish");
		onerecipe.setIngredient("1 large sized bangus (milkfish)\n\n1 onion, chopped finely\n\n4 cloves garlic, minced\n\n1 small sized carrot, small cubes\n\n1 box raisins (optional)\n\n2 tomatoes, chopped\n\n1 raw egg, large\n\n1 tsp. Vetsin (monosodium glutamate)\n\n1 tsp. Salt\n\n½ tsp. Worcestershire sauce\n\n1 green bell pepper, chopped finely\n\n2 tbsp. Flour\n\ncooking oil for frying");
		onerecipe.setProcedure("Scrape fish scales. Clean. Gently pound fish to loosen meat from the skin. Use flat side of a knife in pounding.\n\nBreak the big bone at the nape and on the tail. Insert the end of the handle of an aluminum kitchen turner (sandok) through the fish neck.\n\nGently scrape down the handle between the meat and the skin. Scrape down to the tail, going around and on the other side of the fish.\n\nIf you feel the meat is entirely separated from the skin, remove the handle, squeeze and push out meat (with the big bone), starting from the tail going out through the head. This way, you will be able to push out the whole meat without cutting an opening on the skin.\n\nMarinate skin and head of fish with soy sauce and calamansi (lime) juice. Set aside. Boil fish meat in a little water. Drain. Pick out bones. Flake meat.\n\nSaute garlic until brown. Add onion and tomatoes. Stir in fish meat, carrot, and pepper. Season with salt, vetsin, ground pepper, and Worcestershire sauce. Add raisins.\n\nTransfer cooked mixture to a plate. Cook, then, add raw egg and flour. Fill in mixture in bangus skin. Wrap bangus in wilted banana leaves or in aluminum foil. Fry. Cool before slicing.\n\nGarnish with sliced fresh tomato, spring onions or parsley. Serve with catsup.");
		onerecipe.setVideo("http://www.youtube.com/watch?v=KiZfxq-ZyWI");
		recipeList.add(onerecipe);
		
    	onerecipe = new Recipe();
		onerecipe.setImageid(R.drawable.food20);
		onerecipe.setId(19);
		onerecipe.setName("Bicol Express");
		onerecipe.setType("Meat");
		onerecipe.setIngredient("3 cups coconut milk\n\n2 lbs pork belly, cut into strips\n\n1/2 cup Shrimp Paste\n\n1 tbsp Garlic, minced\n\n6 pieces Thai chili or Serrano pepper\n\n3 tablespoons minced ginger\n\n1 medium onion, minced\n\n2 tablespoons cooking oil\n\nSalt and Pepper to taste");
		onerecipe.setProcedure("Heat a pan and then pour-in the cooking oil.\n\nSauté the garlic, onion, and ginger.\n\nAdd the pork and then continue cooking for 5 to 7 minutes or until the color becomes light brown.\n\nPut-in the shrimp paste and Thai chili or Serrano pepper. Stir.\n\nPour the coconut milk in. Bring to a boil. Simmer for 40 minutes or until the pork is tender.\n\nAdd salt and ground black pepper to taste.");
		onerecipe.setVideo("http://www.youtube.com/watch?v=KiZfxq-ZyWI");
		recipeList.add(onerecipe);
    	
		return recipeList;
	}
}
