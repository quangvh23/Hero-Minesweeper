package cnpm.hero.startpage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class SettingPage extends Activity {
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        setContentView(R.layout.settings_layout);

	    }
	    
	    public void onClickOK(View Btn)
	    {
	    	Intent launchactivity= new Intent(SettingPage.this,MenuPage.class);
	        
	        startActivity(launchactivity);
	        this.finish();
	    }
	    
	    
	}
