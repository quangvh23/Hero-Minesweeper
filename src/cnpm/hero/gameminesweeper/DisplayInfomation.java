package cnpm.hero.gameminesweeper;

import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;

import android.content.Context;
import android.graphics.Color;

public class DisplayInfomation {
	public ChangeableText Blood;
	public ChangeableText Shield;
	public ChangeableText Weapon;
	public ChangeableText Pickaxe;
	public ChangeableText Dynamic;
	public ChangeableText Gold;
	public ChangeableText Lives;
	public ChangeableText Keys;
	
	public Texture mFontTexture;
	public Font mFontTextureYellow;
    
	private MyHero hero;
	
	private static final DisplayInfomation INSTANCE = new DisplayInfomation();
	
	private DisplayInfomation()
	{
		
	}
	
	void setupAttonLoadScene(float width, float height)
	{
		Blood = new ChangeableText(width + 130, height - 20, mFontTextureYellow, "100");//D
		Shield = new ChangeableText(width+ 185, height - 20, mFontTextureYellow, "0");//D
		Weapon = new ChangeableText(width +320, height - 20, mFontTextureYellow, "0"); //D
		Pickaxe = new ChangeableText(width + 380, height - 20, mFontTextureYellow, "0");
		Dynamic = new ChangeableText(width + 455, height - 20, mFontTextureYellow, "0");
		Gold = new ChangeableText(width + 720, height - 20, mFontTextureYellow, "0");
		Lives = new ChangeableText(width + 530, height - 20, mFontTextureYellow, "3");
		Keys = new ChangeableText(width + 260, height - 20, mFontTextureYellow, "0");
	}
	
		
	void setPos(float Dwidth, float Dheight)
	{
		Blood.setPosition(130 + Dwidth, Dheight - 20);
		Shield.setPosition(185 + Dwidth, Dheight - 20);
		Weapon.setPosition(320 + Dwidth, Dheight - 20);
		Pickaxe.setPosition(380 + Dwidth, Dheight - 20);
		Dynamic.setPosition(455 + Dwidth, Dheight - 20);
		Gold.setPosition(720 + Dwidth, Dheight - 20);
		Lives.setPosition(530 + Dwidth,Dheight - 20);
		Keys.setPosition(260 + Dwidth, Dheight - 20);
	}
	
	
	public static DisplayInfomation getInfoDisplay(){
		return INSTANCE;
	}
	
	public void setFontTexture(Context ctx)
	{
		FontFactory.setAssetBasePath("font/"); //  câu lệnh này để chỉ tới thư mục font chưa các font chữ
		this.mFontTexture = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFontTextureYellow = FontFactory.createFromAsset(this.mFontTexture, ctx,"VNSTAMP.TTF", 15, true, Color.YELLOW);
		
	}
	
	public void updateHero(MyHero hero)
	{
		Blood.setText(Integer.toString(hero.Blood));
		Shield.setText(Integer.toString(hero.Shield));
		Weapon.setText(Integer.toString(hero.Weapon));
		Pickaxe.setText(Integer.toString(hero.Pickaxe));
		Dynamic.setText(Integer.toString(hero.Dynamic));
		Gold.setText(Integer.toString(hero.Gold));
		Lives.setText(Integer.toString(hero.Lives));
		Keys.setText(Integer.toString(hero.Keys));
	}
	
}
