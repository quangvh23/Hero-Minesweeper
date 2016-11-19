package cnpm.hero.startpage;

import cnpm.hero.gameminesweeper.MainClass;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class MenuPage extends Activity {
	ImageView playbtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_menu_page);
		// Show the Up button in the action bar.
		//setupActionBar();
		
		
	}

	
	
	public void onBtnPlay(View button)
    {
        Intent launchactivity= new Intent(MenuPage.this,MainClass.class);
        
        startActivity(launchactivity);
        
    }
	
	public void onBtnHighScore(View button)
    {
        Intent launchactivity= new Intent(MenuPage.this,HighScore.class);
        
        startActivity(launchactivity);
        
    }
	
	public void onBtnOption(View button)
    {
        Intent launchactivity= new Intent(MenuPage.this,SettingPage.class);
        
        startActivity(launchactivity);
        
    }
	
	
	
	@Override
	public void onBackPressed() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
 
			// set title
			alertDialogBuilder.setTitle("Exit");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("Do you want to Exit Game?")
				.setCancelable(false)
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						MenuPage.this.finish();
						
					}
				  })
				.setNegativeButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);

        playbtn=(ImageView)findViewById(R.id.playbtn);
        ViewGroup.LayoutParams layout=playbtn.getLayoutParams();
        layout.width= playbtn.getHeight()/5*4;
        playbtn.setLayoutParams(layout);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_page, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
