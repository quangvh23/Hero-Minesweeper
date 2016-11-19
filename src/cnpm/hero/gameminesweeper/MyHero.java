package cnpm.hero.gameminesweeper;

import android.content.Context;
import android.util.Log;

public class MyHero {
	public int Blood;
	public int Shield;
	public int Weapon;
	public int Pickaxe;
	public int Dynamic;
	public int Gold;
	public int Lives;
	public int Keys;
	
	private int isAlive;
	
	//public ArrayList<TrangBi> nMyItems;
	private String []items = {"vang", "vangto", "riu", "vukhi", "giap", "mau", "thuocno"};
	
	private static final MyHero INSTANCE = new MyHero();
	
	private MyHero()
	{
		Blood = 100 ; Lives = 3; isAlive = 1;
		Shield = Weapon = Pickaxe = Dynamic = Gold = Keys = 0;
	}
	
	public static MyHero getHero(){
		return INSTANCE;
	}
	
	public String pickItem (String name)
	{
		Log.v("Nhat vat pham: ",name);
		int value = 0;
		if (name.equals("vang"))
		{
			Gold += 5; value = 5;
			return "Gold: +" + Integer.toString(value);
		}
		else if (name.equals("vangto"))
		{
			Gold += 100; value = 100;
			return "Gold: +" + Integer.toString(value);
		}
		else if (name.equals("riu"))
		{
			Pickaxe ++; value = 1;
			return "Pickaxe: +" + Integer.toString(value);
		}
		else if (name.equals("vukhi"))
		{
			Weapon ++; value = 1;
			return "Weapon: +" + Integer.toString(value);
		}
		else if (name.equals("giap"))
		{
			Shield ++; value = 1;
			return "Shield: +" + Integer.toString(value);
		}
		else if (name.equals("mau"))
		{
			Blood +=10; value = 10;
			return "Blood: +" + Integer.toString(value);
		}
		else if (name.equals("chiakhoa"))
		{
			Keys ++; value = 1;
			return "Key: +" + Integer.toString(value);
		}
		else if (name.equals("thuocno"))
		{
			Dynamic ++; value = 1;
			return "Dynamic: +" + Integer.toString(value);
		}
		else if (name.equals("bay")) // Gặp bẫy
		{
			Blood -= 10;
			if (Shield != 0)
			{
				Shield --;
				return "Shield: -1";
			}
			if(Blood < 0)
			{
				Blood = 100;
				Lives--;
				if(Lives < 0)
				{
					isAlive = 0;
					return "Chet CMNR";
				}
			}
			return "Blood: -10";
		}
		return "";
	}
	
	public void meetTrap(String name)
	{
		if (name.equals("quaivat"))
		{
			//Keys--;
			Blood-=10;
			if(Blood < 0)
			{
				Blood = 100;
				Lives--;
				if(Lives < 0)
				{
					isAlive = 0;
					return;
				}
			}
			
		}
		
	}
	
	public boolean useItem(String name)
	{
		if (name.equals("chiakhoa"))
		{
			if (Keys > 0) 
			{
				Keys--;
				return true;
			}
		}
		else if (name.equals("riu"))
		{
			if (Pickaxe>0) 
			{
				Pickaxe--;
				return true;
			}
		}
		else if (name.equals("vukhi"))
		{
			if (Weapon>0) 
			{
				
				Weapon--;
				return true;
			}
		}
		else if (name.equals("thuocno"))
		{
			if (Dynamic>0) 
			{
				Dynamic--;
				return true;
			}
		}
		else
			Log.v("kiem tra va su dung vat pham: ",name);
		return false;
	}
}
