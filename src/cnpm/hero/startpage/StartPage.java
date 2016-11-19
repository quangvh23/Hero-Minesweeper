package cnpm.hero.startpage;

import java.io.IOException;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.ParallelEntityModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.AutoParallaxBackground;
import org.anddev.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.text.TickerText;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.HorizontalAlign;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;

public class StartPage extends BaseGameActivity implements IOnSceneTouchListener{

    // text ticker 
    private Texture mFontTextureTicker;
    private Font mFontTickerYellow, mFontTickerWhite, mFontTickerCyan;

    private static final int CAMERA_WIDTH = 800;// độ rộng màn hình hiển thị
    private static final int CAMERA_HEIGHT = 480;// chiều cao màn hình hiển thị
    
    private Texture mBackgroundTexture;
    private TextureRegion mBackground;
	
    // text custom font 
    private Texture texture;
    private Font font;
    
    private Music backgroundMusic;
    
	@Override
	public Engine onLoadEngine() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera).setNeedsMusic(true).setNeedsSound(true);
		engineOptions.getTouchOptions().setRunOnUpdateThread(true);
		return new Engine(engineOptions);
	}

	@Override
	public void onLoadResources() {
		
		FontFactory.setAssetBasePath("font/"); //  câu lệnh này để chỉ tới thư mục font chưa các font chữ
		MusicFactory.setAssetBasePath("music/");
		try {
			backgroundMusic = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this,"background.MP3");
			backgroundMusic.setLooping(true);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		// load text ticker
	    this.mFontTextureTicker = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	    this.mFontTickerYellow = FontFactory.createFromAsset(this.mFontTextureTicker, this,"VNSTAMP.TTF", 55, true, Color.YELLOW);
	    
	 // load text change
	    Texture texturetextchange= new Texture(512, 512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	    this.mFontTickerWhite = FontFactory.createFromAsset(texturetextchange, this,"BRUSHSCI.TTF", 35, true, Color.WHITE);
	    mEngine.getTextureManager().loadTexture(texturetextchange);
	    
	    this.mEngine.getFontManager().loadFont(this.mFontTickerWhite);
	    
	    
	    
	    this.mEngine.getTextureManager().loadTexture(this.mFontTextureTicker);
	    this.mEngine.getFontManager().loadFont(this.mFontTickerYellow);
	    
	    
	    this.mBackgroundTexture = new Texture(1024, 1024, TextureOptions.DEFAULT);
	    this.mBackground = TextureRegionFactory.createFromAsset(this.mBackgroundTexture,
				this, "gfx/startpage.jpg", 0, 188);
	    
	    this.mEngine.getTextureManager().loadTextures( this.mBackgroundTexture);
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		final Scene scene = new Scene(2);
		
		final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0, CAMERA_HEIGHT - 
				this.mBackground.getHeight()
				, this.mBackground)));
		scene.setBackground(autoParallaxBackground);
		
	    // text tickker
		String sMinesweeper = "MODERN\nMINESWEEPER";
        final Text textsMinesweeper = new TickerText(20, CAMERA_HEIGHT/2+60, this.mFontTickerYellow,sMinesweeper, HorizontalAlign.CENTER, 20);
        textsMinesweeper.registerEntityModifier( new SequenceEntityModifier( new ParallelEntityModifier(
              new AlphaModifier(10, 1.0f, 1.0f) //new ScaleModifier(10, 0.5f, 1.0f)
            		) ) );
        textsMinesweeper.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        scene.attachChild(textsMinesweeper);
        
        
     // text change
      final ChangeableText textchanges = new ChangeableText(CAMERA_WIDTH/2 + 150, CAMERA_HEIGHT - 70, mFontTickerWhite, " Touch to continue");
      scene.attachChild(textchanges);
      
     // textchanges.setRotation(90);
      
      CountDownTimer t = new CountDownTimer(90000,300) {
    	
    	  int count = 0, dem = 0;
       @Override
       public void onTick(long millisUntilFinished) {
        // TODO Auto-generated method stub
    	   
    	   count++;
    	if(count%2 == 0)
    		textchanges.setVisible(true);
    	else
    		textchanges.setVisible(false);
        
    	if(count%4 == 0)
    	{
    		dem = 1-dem;
            textsMinesweeper.setVisible(dem==0);
    	}
    	
    	
    	
    	
    	
       }
       
       @Override
       public void onFinish() {
        
       }
      };
      t.start();
		
      	scene.setOnSceneTouchListener(this);
      	backgroundMusic.play();
		return scene;
	}

	 @Override
     public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		 
		 Intent launchactivity= new Intent(StartPage.this,MenuPage.class);
		 
		 startActivity(launchactivity);
		 StartPage.this.finish();
		 
         return false;
     }
	 
	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		
	}

}
