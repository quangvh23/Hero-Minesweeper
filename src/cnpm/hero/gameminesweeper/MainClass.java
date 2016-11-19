package cnpm.hero.gameminesweeper;



import java.io.IOException;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.BoundCamera;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.anddev.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXProperties;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXProperty;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTile;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTileProperty;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cnpm.hero.startpage.EndGame;
import cnpm.hero.startpage.R;


public class MainClass extends BaseGameActivity{
 
        private static final int CAMERA_WIDTH = 800;
        private static final int CAMERA_HEIGHT = 480;
 
        private static BoundCamera mCamera;
        private Texture mTexture;

        private TiledTextureRegion mNhanVatTiledTextureRegion;
 
        private Texture mOnScreenControlTexture;
        private Texture mOnScreenControlTexture1;
        private TextureRegion mOnScreenControlBaseTextureRegion;
        private TextureRegion mOnScreenControlKnobTextureRegion;

        
        private Texture mTextureControllBack;
    	private TiledTextureRegion mTiledTextureRegionControllBack;
        
        private TMXTiledMap mTmxTiledMap;
        private TMXLayer VatCanTMXLayer;
        private String tenBanDo = "mapLevel1.tmx";
 

        
        private int trangThaiNhanVat = 0;
        private TiledTextureRegion mThanhCongCu;
        private Texture mAutoParallaxBackgroundTexture;
                
        private TileManager tilemanager;
        private MyHero hero;
  
     
        private Music backgroundMusic, eventMusicPickItem, eventMusicLostItem, eventMusicCantGo;
        // text ticker 
        private Texture mFontTextureTicker;
        private Font mFontTickerYellow, mFontTickerWhite;
    
        private DisplayInfomation mHienThiTTin;
        
    // tạo biến đêm
         int count = 0;
         private String sThongBao = "toi la luan";
        
        
        
        
        
        
        @Override
        public Engine onLoadEngine() {
            this.mCamera = new BoundCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
            return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE,
            		new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera).setNeedsMusic(true).setNeedsSound(true));
        }
 
        @Override
        public void onLoadResources() {
        	
        	mHienThiTTin = DisplayInfomation.getInfoDisplay();
        	mHienThiTTin.setFontTexture(this);
        	
        	mHienThiTTin.setupAttonLoadScene(mCamera.getMinX(), mCamera.getMaxY()-11);
        	
        	this.mEngine.getTextureManager().loadTexture(this.mHienThiTTin.mFontTexture);
    		this.mEngine.getFontManager().loadFont(this.mHienThiTTin.mFontTextureYellow);
        	
        	FontFactory.setAssetBasePath("font/"); //  câu lệnh này để chỉ tới thư mục font chưa các font chữ
        	// load text ticker
            this.mFontTextureTicker = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            
            this.mFontTickerYellow = new Font(this.mFontTextureTicker, Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC), 24, true, Color.YELLOW);
            this.mFontTickerWhite = new Font(this.mFontTextureTicker, Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC), 24, true, Color.WHITE);
            
            this.mEngine.getTextureManager().loadTexture(this.mFontTextureTicker);
            this.mEngine.getFontManager().loadFont(this.mFontTickerYellow);
            this.mEngine.getFontManager().loadFont(this.mFontTickerWhite);
        	
        	//Load am thanh
        	MusicFactory.setAssetBasePath("music/");
        	try {
        		backgroundMusic = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this,"nhacnen.mp3");
    			backgroundMusic.setLooping(true);
    			eventMusicPickItem = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this,"oh_yeah.mp3");
    			eventMusicPickItem.setLooping(false);
    			eventMusicLostItem = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this,"hero_dies.mp3");
    			eventMusicLostItem.setLooping(false);
    			eventMusicCantGo = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this,"cannot_go.mp3");
    			eventMusicCantGo.setLooping(false);
    			
    		} catch (IllegalStateException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        	
        	
        	//Texture hinh anh
            TextureRegionFactory.setAssetBasePath("gfx/");
            
            this.mAutoParallaxBackgroundTexture = new Texture(1024, 1024, TextureOptions.DEFAULT);
            
            this.mTexture = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            
            //Texture NhanVat
            this.mNhanVatTiledTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mTexture, 
            		this, "bad1.png", 0, 0, 3, 4);
   
            this.mOnScreenControlTexture = new Texture(512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            this.mOnScreenControlTexture1 = new Texture(512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            this.mAutoParallaxBackgroundTexture = new Texture(1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            
            this.mOnScreenControlBaseTextureRegion = TextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "onscreen_control_base.png", 0, 0);
            this.mOnScreenControlKnobTextureRegion = TextureRegionFactory.createFromAsset(this.mOnScreenControlTexture1, this, "onscreen_control_knob.png", 128, 0);
   
            this.mThanhCongCu = TextureRegionFactory.createTiledFromAsset(this.mAutoParallaxBackgroundTexture,this, "ThanhCongCu.png", 0, 0, 1, 1);
            
            this.mEngine.getTextureManager().loadTextures(this.mTexture, this.mOnScreenControlTexture,this.mOnScreenControlTexture1,
            													this.mAutoParallaxBackgroundTexture);       
            
            this.mTextureControllBack= new Texture(512, 512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
			this.mTiledTextureRegionControllBack = TextureRegionFactory.createTiledFromAsset(this.mTextureControllBack
					, this, "action_event.png", 10, 10,2,1);
			mEngine.getTextureManager().loadTexture(mTextureControllBack);
			
            
               }
        
        
        
        
        
 
        @Override
        public Scene onLoadScene() {
            this.mEngine.registerUpdateHandler(new FPSLogger());
 
            final Scene scene = new Scene();
            hero = MyHero.getHero();
            
            
            
            
            // khoi tao map
            mTmxTiledMap = TaiBanDo.getTMXTiledMap(scene, mEngine, this, tenBanDo, this);
            tilemanager = new TileManager (mTmxTiledMap);
 
            ArrayList<TMXLayer> mapLayers = mTmxTiledMap.getTMXLayers();
            for(TMXLayer layer : mapLayers){
                if(layer.getName().equals("vatcan")){
                    VatCanTMXLayer = layer;
                }
                scene.attachChild(layer);
            }
   
            

            final AnimatedSprite face = new AnimatedSprite(30, 30, this.mNhanVatTiledTextureRegion){
            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
        				float pTouchAreaLocalX, float pTouchAreaLocalY) {
            		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN){
            		Toast.makeText(getBaseContext(),"cham", Toast.LENGTH_SHORT).show();
            		}
        			return true;
        		};
        	};
        	
            final AnimatedSprite iThanhCongCu = new AnimatedSprite(0, CAMERA_HEIGHT, this.mThanhCongCu);
            
            
            final PhysicsHandler physicsHandler = new PhysicsHandler(face);
            face.registerUpdateHandler(physicsHandler);
            final PhysicsHandler physicsHandler2 = new PhysicsHandler(iThanhCongCu);
            iThanhCongCu.registerUpdateHandler(physicsHandler2);
            
            scene.attachChild(iThanhCongCu);
            scene.attachChild(face);
            
            scene.registerTouchArea(face);
   
            
            
            //Su kien nhan vao nut Back
    		final AnimatedSprite Controll_Back = new AnimatedSprite(mCamera.getMinX(), mCamera.getMinY(),mTiledTextureRegionControllBack)
    		{
    		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
    				float pTouchAreaLocalX, float pTouchAreaLocalY) {
    			
    			if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN){
    			this.setCurrentTileIndex(1, 0);
    		
    			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainClass.this);
    			 
    			// set title
    			alertDialogBuilder.setTitle("Exit this Game?");
     
    			
    			// set dialog message
    			alertDialogBuilder
    				.setMessage("Do you want to Exit Game and Return to Menu?")
    				.setCancelable(false)
    				.setPositiveButton("Yes, that's right",new DialogInterface.OnClickListener() {
    					public void onClick(DialogInterface dialog,int id) {
    						// if this button is clicked, close
    						// current activity
    						MainClass.this.finish();    						
    					}
    				  })
    				.setNegativeButton("No, I've made a mistake",new DialogInterface.OnClickListener() {
    					public void onClick(DialogInterface dialog,int id) {
    						// if this button is clicked, just close
    						// the dialog box and do nothing
    						
    						dialog.cancel();
    					}
    				});
     
    				this.setCurrentTileIndex(0, 0);
    				
    				// create alert dialog
    				AlertDialog alertDialog = alertDialogBuilder.create();
     
    				// show it
    				alertDialog.show();
    			}
    				
    			
    			return true;
    		};
    	};
    	
    		scene.registerTouchArea(Controll_Back);
    		scene.attachChild(Controll_Back);
    	
                     
    		
            
            final TMXLayer tmxLayer = this.mTmxTiledMap.getTMXLayers().get(0);
            mCamera.setBounds(0, tmxLayer.getWidth(), 0, tmxLayer.getHeight()+iThanhCongCu.getHeight());
            mCamera.setBoundsEnabled(true);
            mCamera.setChaseEntity(face);
            
            iThanhCongCu.setPosition(mCamera.getMinX(), mCamera.getMaxY()-iThanhCongCu.getBaseHeight()-11);
            
            final DigitalOnScreenControl digitalOnScreenControl = new DigitalOnScreenControl(0, CAMERA_HEIGHT - this.mOnScreenControlBaseTextureRegion.getHeight(), this.mCamera, this.mOnScreenControlBaseTextureRegion, this.mOnScreenControlKnobTextureRegion, 0.1f, new IOnScreenControlListener() {
                @Override
                public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
                	
                	if(pValueX != 0 || pValueY != 0){
                        float pX = 0, pY = 0;
                        pX = face.getX() + 16;
                        pY = face.getY() + 16;
                        if(pValueX > 0){
                            if(trangThaiNhanVat == 0){
                                face.animate(new long[]{200, 200, 200}, 6, 8, true);
                                
                                trangThaiNhanVat = 1;
                            }else if(trangThaiNhanVat == 1){
                            	//face.animate(new long[]{200}, new int[]{6}, 10000);
                            }
                            
                            pX = face.getX() + face.getWidth();
                            pY = face.getY() + face.getHeight()/2;
                           
                            
                            Controll_Back.setPosition(mCamera.getMinX(), mCamera.getMinY());
                            iThanhCongCu.setPosition(mCamera.getMinX(), mCamera.getMaxY()-iThanhCongCu.getBaseHeight());
                            mHienThiTTin.setPos(mCamera.getMinX(), mCamera.getMaxY());
                       
                        }else if(pValueX < 0){
                       if(trangThaiNhanVat == 0){
                                 face.animate(new long[]{200, 200, 200}, 3, 5, true);
                                 
                                 trangThaiNhanVat = 1;
                             }else if(trangThaiNhanVat == 1){
                            	 //face.animate(new long[]{200}, new int[]{3}, 10000);
                             }
                            
                             pX = face.getX();
                             pY = face.getY() + face.getHeight()/2;
                             
                             
                             
                             Controll_Back.setPosition(mCamera.getMinX(), mCamera.getMinY());
                             iThanhCongCu.setPosition(mCamera.getMinX(), mCamera.getMaxY()-iThanhCongCu.getBaseHeight());
                             mHienThiTTin.setPos(mCamera.getMinX(), mCamera.getMaxY());
//                        
                        }else if(pValueY > 0){
                            if(trangThaiNhanVat == 0){
                                face.animate(new long[]{200, 200, 200}, 0, 2, true);
                                
                                trangThaiNhanVat = 1;
                            }else if(trangThaiNhanVat == 1){
                            	//face.animate(new long[]{200}, new int[]{0}, 10000);
                            }
                            
                            pX = face.getX() + face.getWidth()/2;
                            pY = face.getY() + face.getHeight();
                            
                            
                            Controll_Back.setPosition(mCamera.getMinX(), mCamera.getMinY());
                            iThanhCongCu.setPosition(mCamera.getMinX(), mCamera.getMaxY()-iThanhCongCu.getBaseHeight());
                            mHienThiTTin.setPos(mCamera.getMinX(), mCamera.getMaxY());
//                        
                        }else if(pValueY < 0){
                            if(trangThaiNhanVat == 0){
                                face.animate(new long[]{200, 200, 200}, 9, 11, true);
                                
                                trangThaiNhanVat = 1;
                            }else if(trangThaiNhanVat == 1){
                            	//face.animate(new long[]{200}, new int[]{9}, 10000);
                            }
                            
                            pX = face.getX() + face.getWidth()/2;
                            pY = face.getY();
                                                        
                            Controll_Back.setPosition(mCamera.getMinX(), mCamera.getMinY());
                            iThanhCongCu.setPosition(mCamera.getMinX(), mCamera.getMaxY()-iThanhCongCu.getBaseHeight());
                            mHienThiTTin.setPos(mCamera.getMinX(), mCamera.getMaxY());
                        }
                        
                        int flag = tilemanager.canGo(pX, pY);
                        if (flag == 0)
                    	{
                        	String valueReceive = "";
                        	valueReceive = tilemanager.useItem(pX, pY, hero, "Key");
                        	Log.v("Tra ve: ", "  "+ valueReceive);
                    			if(valueReceive != "")
                    			{
                    				if(valueReceive.indexOf('-')!=-1)
                        				displayInfomation(scene, valueReceive, mCamera.getCenterX(), mCamera.getCenterY(),2);
                        			else
                        				displayInfomation(scene, valueReceive, mCamera.getCenterX(), mCamera.getCenterY(),1);
                    				
                    				mHienThiTTin.updateHero(hero);
                    				
                        			physicsHandler.setVelocity(pValueX * 50, pValueY * 50);
                        			Log.v("Su dung vat pham: ", valueReceive);
                        			//TEST
                        			//MainClass.this.finish();
                        			Intent i = new Intent(MainClass.this,EndGame.class);
                                	startActivity(i);
                    			}
                    			
                    			else
                    			{
                    				eventMusicCantGo.play();
                    				physicsHandler.setVelocity(pValueX * 0, pValueY * 0);
                    				Log.v("Cham vat can", "Cham vat can !!!");
                    			}
                    	}
                        else if (flag == -1)
                        {
                        	Log.v("Dich", "Dichhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                        	displayInfomation(scene, "You get the GOAL!", mCamera.getCenterX(), mCamera.getCenterY(), -1);
                        	Intent i = new Intent(MainClass.this,EndGame.class);
                        	startActivity(i);
                        	
                        	//MainClass.this.finish();
                        	//setContentView(R.layout.end_game); // có báo lỗi
                        }
                    	else
                    	{
                    		String valueReceive = "";
                    		physicsHandler.setVelocity(pValueX * 50, pValueY * 50);
                    		valueReceive = tilemanager.openSquare(pX, pY, hero);

                    		mHienThiTTin.updateHero(hero);
                    		
                    		Log.v("Main:", "" + valueReceive);  
                    		if(valueReceive !="")
                    			if(valueReceive.indexOf('-')!=-1)
                    				displayInfomation(scene, valueReceive, mCamera.getCenterX(), mCamera.getCenterY(),2);
                    			else if (valueReceive.contains("Blood"))
                    			{
                    				displayInfomation(scene, valueReceive, mCamera.getCenterX(), mCamera.getCenterY(),1);
                    				if (hero.Lives<0){
                    					displayInfomation(scene, "Game over", mCamera.getCenterX(), mCamera.getCenterY(),1);
                    					//MainClass.this.finish();
                    					//setContentView(R.layout.end_game);
                    					Intent i = new Intent(MainClass.this,EndGame.class);
                                    	startActivity(i);
                    				}
                    			}
                    			else displayInfomation(scene, valueReceive, mCamera.getCenterX(), mCamera.getCenterY(),1);
                    	}
                        
                    }else{// Dieu khien trang thai NhanVat
                    	face.animate(new long[]{200}, new int[]{0}, 10000);

                        trangThaiNhanVat = 0;
                        
                        physicsHandler.setVelocity(pValueX * 0, pValueY * 0);
                    }
                }
            });
   
            digitalOnScreenControl.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            digitalOnScreenControl.getControlBase().setAlpha(0.5f);
            digitalOnScreenControl.getControlBase().setScaleCenter(0, 128);
            digitalOnScreenControl.getControlBase().setScale(0.75f);
            digitalOnScreenControl.getControlKnob().setScale(0.75f);
            digitalOnScreenControl.refreshControlKnobPosition();
  
            digitalOnScreenControl.setTouchAreaBindingEnabled(true);
            scene.setChildScene(digitalOnScreenControl);
            
            scene.attachChild(mHienThiTTin.Blood);
            scene.attachChild(mHienThiTTin.Dynamic);
            scene.attachChild(mHienThiTTin.Gold);
            scene.attachChild(mHienThiTTin.Keys);
            scene.attachChild(mHienThiTTin.Lives);
            scene.attachChild(mHienThiTTin.Pickaxe);
            scene.attachChild(mHienThiTTin.Shield);
            scene.attachChild(mHienThiTTin.Weapon);
            
            //Play music
            this.backgroundMusic.play();
            
            return scene;
        }
 
        @Override
        public void onLoadComplete() {
 
        }
        
        public void displayInfomation(Scene scene, final String info, float px, float py, final int Type)
        {
        	this.runOnUiThread(new Runnable() {
      		   public void run() {
      			  if (info.contains("Gold"))
      			  {
      				  displayToast(getBaseContext(),info,R.drawable.vang,R.drawable.ic_launcher,1);
      			  }
      			  
      			  else if (info.contains("Dynamic"))
      			  {
      				  displayToast(getBaseContext(),info,R.drawable.bom,R.drawable.ic_launcher,1);
      			  }
      			  else if (info.contains("Weapon"))
      			  {
      				  displayToast(getBaseContext(),info,R.drawable.weapon,R.drawable.ic_launcher,1);
      			  }
      			  else if (info.contains("Shield"))
      			  {
      				  displayToast(getBaseContext(),info,R.drawable.giap,R.drawable.ic_launcher,1);
      			  }
      			  else if (info.contains("Pickaxe"))
      			  {
      				  displayToast(getBaseContext(),info,R.drawable.p,R.drawable.ic_launcher,1);
      			  }
      			  else if (info.contains("Blood"))
      			  {
      				  displayToast(getBaseContext(),info,R.drawable.mau,R.drawable.ic_launcher,1);
      			  }
      			  else if (info.contains("Key"))
      			  {
      				  displayToast(getBaseContext(),info,R.drawable.chiakhoa,R.drawable.ic_launcher,1);
      			  }
      			  else displayToast(getBaseContext(),info,R.drawable.to,R.drawable.ic_launcher,1);
      			 /*TMXTile mTMXTile = null;
      			 TMXProperties<TMXTileProperty> mTMXProperties = mTMXTile.getTMXTileProperties(mTmxTiledMap);
      			 TMXProperty mTmxProperty = mTMXProperties.get(0);
      			   tilemanager.getLayer("vang");
      			 if (mTmxProperty.getName().equals("vang"))*/
      		      
      		     if(Type == 1)
      		    	 eventMusicPickItem.play();
      		     if(Type == 2)
      		    	eventMusicLostItem.play();
      		   }
      		});
        }
        
        public static int SHORT_TOAST = 0;
        public static int LONG_TOAST = 1;
        public static void displayToast(Context caller, String toastMsg, int ImageId, int BackgroundId, int toastType){

            try {// try-catch to avoid stupid app crashes
                LayoutInflater inflater = LayoutInflater.from(caller);
                // pháº£i Ä‘á»•i vÃ¬ trong R.layout khÃ´ng cÃ³ toast_layout
                //View mainLayout = inflater.inflate(R.layout.activity_menu_page, null);
                
                View rootLayout = inflater.inflate(R.layout.toast_layout, null);
                
                TextView text = (TextView) rootLayout.findViewById(R.id.textToast);
                
                text.setText(toastMsg);
                if (ImageId!=0){
                	ImageView image = (ImageView) rootLayout.findViewById(R.id.imageToast);
                    image.setImageResource(ImageId);
                }
                
//                if (BackgroundId!=0){
//                	// vÃƒÂ­ dÃ¡Â»Â¥ R.drawable.monster
//                	LinearLayout li=(LinearLayout)mainLayout.findViewById(R.id.toast_layout);
//                    li.setBackgroundResource(BackgroundId);
//                }            
                Toast toast = new Toast(caller);
                
                //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setGravity(Gravity.BOTTOM, (int)mCamera.getCenterX()-250, (int)mCamera.getCenterY()+150);
                if (toastType==SHORT_TOAST)//(isShort)
                    toast.setDuration(Toast.LENGTH_SHORT);
                else
                    toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(rootLayout);
                toast.show();
            }
            catch(Exception ex) {// to avoid stupid app crashes
                Log.v("cannot toast", ex.toString());
            }
        }

}