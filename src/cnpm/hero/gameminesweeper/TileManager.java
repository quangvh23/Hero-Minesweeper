package cnpm.hero.gameminesweeper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXProperties;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXProperty;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTile;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTileProperty;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;

import android.util.Log;

public class TileManager {
	private ArrayList<TMXLayer> mapLayers = null; // Danh sách các layer, gồm có rất nhiều layer, tên như bên dưới.
	int tiles [][];
	boolean hasitem[][];
	// Danh sách các layer
	String[] tilename = {"background", "so", "vatpham", "tuong", "user", "camco", "vatcan", "tuongphaduoc", "vang", "bay", "quaivat", "vukhi",
						"mau", "riu", "chiakhoa", "thuocno", "vangto", "da", "so1", "so2", "so3", "cua", "dich"};
	
	// Các đối tượng vật cản, nếu không có vật phẩm thì ko thể qua
	String[] staticObject = {"dich","vatcan", "tuongphaduoc", "quaivat", "da", "bay", "cua"};
	private TextureRegion visible;
	private TMXTiledMap mTmxTiledMap;

	
	
	public TileManager(TMXTiledMap mTmxTiledMap2) 
	{
		// TODO Auto-generated constructor stub
		TMXTile mTMXTile;
		TMXLayer layer;
		mTmxTiledMap = mTmxTiledMap2;
		mapLayers = mTmxTiledMap.getTMXLayers();
		int height = (int) mapLayers.get(0).getHeight();
		int width = (int) mapLayers.get(0).getWidth();
		tiles = new int[height][width];
		hasitem = new boolean[height][width];
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
			{
				tiles[i][j] = 0; // Đánh dấu là ô đã mở hay chưa mở
				hasitem [i][j] = true;
			}
		
	}

	// Lấy layer
	public TMXLayer getLayerAt (int position)
	{
		return mapLayers.get(position);
	}
	
	
	// Kiểm tra xem ô đó có đi được không, nếu có thì mở ô luôn
	public int canGo (float pX, float pY)
	{
		int i;
		TMXTile mTMXTile = null;
		TMXLayer layer;
		for (i = 0; i < staticObject.length; i++) // Kiểm tra ô đó có phải là vật cản, tường, đá, ...
		{
			layer = this.getLayer(staticObject[i]);
			mTMXTile = layer.getTMXTileAt(pX, pY);
			if(mTMXTile != null)
			{
				try 
				{
					TMXProperties<TMXTileProperty> mTMXProperties = mTMXTile.getTMXTileProperties(mTmxTiledMap);
					TMXProperty mTmxProperty = mTMXProperties.get(0);
					if (mTmxProperty.getName().equals("dich"))
						return -1;
					// Các đối tượng vật cản nếu chưa phá hủy thì không thể đi qua
					if(mTmxProperty.getName().equals(staticObject[i]) && !mTmxProperty.getName().equals("bay") && tiles[mTMXTile.getTileRow()][mTMXTile.getTileColumn()] != 1)
					{
						//tiles[mTMXTile.getTileRow()][mTMXTile.getTileColumn()] = 1;// gán lại đã mở
						
						return 0;
					}
                
					// Nếu nó là bẫy thì mở, lần sau ko cho qua nữa
					if (mTmxProperty.getName().equals("bay"))
						if (tiles[mTMXTile.getTileRow()][mTMXTile.getTileColumn()] == 1) // Mở r thì ko cho qua nữa
							return 0;
				}
				catch(Exception e)
				{
					//Log.v("Can Go", "Khong tim thay Properties");  
				}
			}
				
		}
		return 1;
	}
	
	
	public String openSquare(float pX, float pY, MyHero hero)
	{
		String valueReturn ="";
		
		int x, y;
		// Cập nhật đã mở ô đó. Lấy đại một layer nào đó
		x = this.getLayer("background").getTMXTileAt(pX, pY).getTileRow();
		y = this.getLayer("background").getTMXTileAt(pX, pY).getTileColumn();
		if (tiles[x][y] != 1)
		{
			// Cập nhật ô đó đã mở.
			tiles[x][y] = 1;
			for (int i = 5; i > 1; i--)
				mapLayers.get(i).getTMXTileAt(pX, pY).setTextureRegion(visible);// Mở hết các layer luôn
		}
		if (hasitem[x][y] == true) // Nhat item
		{
			valueReturn = this.pickItem(pX, pY, hero);
			hasitem[x][y] = false;
		}
		return valueReturn;
	}

	public TMXLayer getLayer(String name) {
		// TODO Auto-generated method stub
		for(TMXLayer layer : mapLayers)
            if(layer.getName().equals(name))
            	return layer;
		return null;
	}
	
	public String pickItem (float pX, float pY, MyHero hero)
	{
		TMXTile mTMXTile = null;
		TMXLayer layer;
		TMXProperties<TMXTileProperty> mTMXProperties;
		TMXProperty mTmxProperty;
		layer = this.getLayer("vatpham");
		mTMXTile = layer.getTMXTileAt(pX, pY);
		String name;
		String valueReturn = "" ;
		try 
		{
			mTMXProperties = mTMXTile.getTMXTileProperties(mTmxTiledMap);
			mTmxProperty = mTMXProperties.get(0);
			name = mTmxProperty.getName();
			valueReturn = hero.pickItem(name);
		}
		catch(Exception e) // Nếu không có vật phẩm thì kiểm tra ô đó có bẫy không
		{
			try 
			{
				layer = this.getLayer("bay");
				mTMXTile = layer.getTMXTileAt(pX, pY);
				mTMXProperties = mTMXTile.getTMXTileProperties(mTmxTiledMap);
				mTmxProperty = mTMXProperties.get(0);
				name = mTmxProperty.getName();
				valueReturn = hero.pickItem(name); // Gặp bẫy
			}
			catch (Exception ex)
			{
				Log.v("Vat pham: ", "Khong co gi het");
			}
		}
		
		return valueReturn;
	}
	
	// Gửi vào tọa độ user chạm vào
	public boolean putFlag (float pX, float pY)
	{
		int x, y;
		TMXLayer layer = this.getLayer("camco");
		TMXTile mTMXTile = layer.getTMXTileAt(pX, pY);
		x = mTMXTile.getTileRow();
		y = mTMXTile.getTileColumn();
		
		if (tiles[x][y] != 1)
		{
			Log.v("putFlag: ", "Da cam co");
			tiles[x][y] = 1; // Đặt cờ, đánh dấu ô đó là đã mở để không mở ô đó
			// Load cái giao diện cắm cờ lên che cái ô đó. Không bik làm, bik mở ô không à.
			
			return true; // Đặt cờ thành công
		}
		else
		{
			Log.v("putFlag: ", "Da thao co");
			tiles[x][y] = 2; // Tháo cờ, nếu sau này hero đi qua thì mở.
			return false; // Tháo cờ thành công
		}
	}
	
	public String useItem (float pX, float pY, MyHero hero, String nameitem)
	{
		if (nameitem.equals("Key"))
			return useKey (pX, pY, hero);
		else if (nameitem.equals("Pickaxe"))
			return usePickaxe (pX, pY, hero);
		else if (nameitem.equals("Dynamic"))
			return useDynamic (pX, pY, hero);
		else
			return "";
	}
	
	public String useDynamic(float pX, float pY, MyHero hero) 
	{
		int x, y, i;
		String name = "";
		if (!hero.useItem("thuocno"))
			return name;
		int []X = {0,-32,-32, -32, 0, 32, 32, 32, 0};
		int []Y = {0,-32, 0, 32, 32, 32, 0, -32, -32};
		TMXLayer layer = this.getLayer("background");
		TMXTile mTMXTile;
		TMXProperties<TMXTileProperty> mTMXProperties;
		TMXProperty mTmxProperty;
		for (i = 0; i < 9; i++)
		{
			try 
			{
				mTMXTile = layer.getTMXTileAt(pX+X[i], pY+Y[i]);
				mTMXProperties = mTMXTile.getTMXTileProperties(mTmxTiledMap);
				mTmxProperty = mTMXProperties.get(0);
				name = mTmxProperty.getName();
				tiles[mTMXTile.getTileRow()][mTMXTile.getTileColumn()] = 1;
				hasitem[mTMXTile.getTileRow()][mTMXTile.getTileColumn()] = false;
				for (int j = 5; j > 1; j--)
					mapLayers.get(j).getTMXTileAt(pX+X[i], pY+Y[i]).setTextureRegion(visible);// Mở hết các layer luôn
			}
			catch(Exception e)
			{}
		}
		return "Dynamic: -1";
	}

	public String useKey (float pX, float pY, MyHero hero)
	{
		int x, y;
		String name = "";
		TMXLayer layer = this.getLayer("tuong");
		TMXTile mTMXTile = layer.getTMXTileAt(pX, pY);
		TMXProperties<TMXTileProperty> mTMXProperties;
		TMXProperty mTmxProperty;
		x = mTMXTile.getTileRow();
		y = mTMXTile.getTileColumn();
		try 
		{
			mTMXProperties = mTMXTile.getTMXTileProperties(mTmxTiledMap);
			mTmxProperty = mTMXProperties.get(0);
			name = mTmxProperty.getName();
			Log.v("Vat can: ", name);
			if (name.equals("cua") && hero.useItem("chiakhoa"))
			{
				for (int i = 5; i > 1; i--)
					mapLayers.get(i).getTMXTileAt(pX, pY).setTextureRegion(visible);// Mở hết các layer luôn
				tiles[x][y] = 1;
				return "Keys: -1";
			}
			else if (name.equals("quaivat") && hero.useItem("vukhi"))
			{
				for (int i = 5; i > 1; i--)
					mapLayers.get(i).getTMXTileAt(pX, pY).setTextureRegion(visible);// Mở hết các layer luôn
				tiles[x][y] = 1;
				
				return "Weapon: -1";
			}
			else if (name.equals("da"))
				return this.usePickaxe(pX, pY, hero);
			else if (name.equals("tuong"))
				return this.useDynamic(pX, pY, hero);
		}
		catch (Exception e)
		{}
		return "";
	}
	
	public String usePickaxe (float pX, float pY, MyHero hero)
	{
		int x, y, i;
		String name = "";
		if (!hero.useItem("riu"))
			return name;
		int []X = {0,-32,-32, -32, 0, 32, 32, 32, 0};
		int []Y = {0,-32, 0, 32, 32, 32, 0, -32, -32};
		TMXLayer layer = this.getLayer("user");
		TMXTile mTMXTile; 
		TMXProperties<TMXTileProperty> mTMXProperties;
		TMXProperty mTmxProperty;
		for (i = 0; i < 9; i++)
		{
			try 
			{
				mTMXTile = layer.getTMXTileAt(pX+X[i], pY+Y[i]);
				mTMXProperties = mTMXTile.getTMXTileProperties(mTmxTiledMap);
				mTmxProperty = mTMXProperties.get(0);
				name = mTmxProperty.getName();
				if (name.equals("chuamo"))
					for (int j = 5; j > 1; j--)
						mapLayers.get(j).getTMXTileAt(pX+X[i], pY+Y[i]).setTextureRegion(visible);// Mở hết các layer luôn
			}
			catch(Exception e)
			{}
		}
		layer = this.getLayer("da");
		for (i = 0; i < 9; i++)
		{
			try 
			{
				mTMXTile = layer.getTMXTileAt(pX+X[i], pY+Y[i]);
				mTMXProperties = mTMXTile.getTMXTileProperties(mTmxTiledMap);
				mTmxProperty = mTMXProperties.get(0);
				name = mTmxProperty.getName();
				if (name.equals("da"))
				{
					tiles[mTMXTile.getTileRow()][mTMXTile.getTileColumn()] = 1;
					for (int j = 5; j > 1; j--)
						mapLayers.get(j).getTMXTileAt(pX+X[i], pY+Y[i]).setTextureRegion(visible);// Mở hết các layer luôn
				}
			}
			catch(Exception e)
			{}
		}
		return "Pickaxe: -1";
	}
	
}

