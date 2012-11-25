package net.foxycorndog.nostalgia.items.weapons.guns;

import net.foxycorndog.jdooal.audio.AL;
import net.foxycorndog.jdoogl.camera.Camera;
import net.foxycorndog.jdoogl.geometry.Point;
import net.foxycorndog.nostalgia.items.Item;
import net.foxycorndog.nostalgia.items.weapons.Bullet;
import net.foxycorndog.nostalgia.map.Map;

public abstract class Gun extends Item
{
	private boolean reloading;
	
	private int     idleAmmo, clipAmmo;
	private int     clipSize, clipAmount;
	private int     shotDelay, reloadDelay;
	
	private float   velocity;
	
	private Map     map;
	
	private int     shotSoundIds[], reloadSoundId;
	
	public Gun(int clipSize, int clipAmount, int shotDelay, final int reloadDelay, int shotSoundId, int reloadSoundId, float velocity, Map map)
	{
		this.clipSize        = clipSize;
		this.clipAmount      = clipAmount;
		this.shotDelay       = shotDelay;
		this.reloadDelay     = reloadDelay;
		this.reloadSoundId   = reloadSoundId;
		
		this.velocity        = velocity;
		
		this.map             = map;
		
		this.shotSoundIds    = new int[5];
		this.shotSoundIds[0] = shotSoundId;
		
		for (int i = 1; i < shotSoundIds.length; i ++)
		{
			this.shotSoundIds[i] = AL.copy(shotSoundId);
		}
	}
	
	public void shoot()
	{
		if (hasAmmoReady() && !reloading)
		{
			Bullet b = new Bullet(new Point(getX(), getY(), getZ()), velocity, getYaw(), getPitch(), getRoll(), getMap());
			
			getMap().shoot(b);
			
			removeClipAmmo(1);
			
			int ind = 0;
			
			for (int i = 0; i < shotSoundIds.length; i ++)
			{
				if (!AL.isPlaying(shotSoundIds[i]))
				{
					ind = i;
					
					break;
				}
			}
			
			AL.loop(shotSoundIds[ind], 3);
		}
	}
	
	public int getMaxAmmoAmount()
	{
		return clipSize * clipAmount;
	}
	
	public int getClipAmmo()
	{
		return clipAmmo;
	}
	
	public int getIdleAmmo()
	{
		return idleAmmo;
	}
	
	public boolean hasAmmoReady()
	{
		return clipAmmo > 0;
	}
	
	public boolean hasAmmo()
	{
		return idleAmmo > 0 || clipAmmo > 0;
	}
	
	public void addAmmo(int amount)
	{
		idleAmmo += amount;
		
		if (idleAmmo + clipAmmo > getMaxAmmoAmount())
		{
			idleAmmo = getMaxAmmoAmount() - clipAmmo;
		}
	}
	
	public boolean isReloading()
	{
		return reloading;
	}
	
	public void reload()
	{
		if (clipAmmo >= clipSize)
		{
			return;
		}
		
		if (!reloading)
		{
			AL.play(reloadSoundId);
		}
		
		reloading = true;
		
		new Thread()
		{
			public void run()
			{
				try
				{
					Thread.sleep(reloadDelay);
					
					reload(false);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public void reload(boolean delay)
	{
		if (delay)
		{
			reload();
		}
		else
		{
			reloading = true;
			
			if (reloading)
			{
				int amountBefore = idleAmmo;
				
				idleAmmo -= (clipSize - clipAmmo);
				
				if (idleAmmo < 0)
				{
					idleAmmo = 0;
				}
				
				clipAmmo += amountBefore - idleAmmo;
			}
			
			stopReloading();
		}
	}
	
	public void stopReloading()
	{
		if (reloading)
		{
			reloading = false;
		}
	}
	
	public void removeIdleAmmo(int amount)
	{
		if (amount < 0)
		{
			throw new IllegalArgumentException("The amount must be > 0.");
		}
		
		idleAmmo -= amount;
		
		if (idleAmmo < 0)
		{
			idleAmmo = 0;
		}
	}
	
	public void removeClipAmmo(int amount)
	{
		if (amount < 0)
		{
			throw new IllegalArgumentException("The amount must be > 0.");
		}
		
		clipAmmo -= amount;
		
		if (clipAmmo < 0)
		{
			removeIdleAmmo(-clipAmmo);
			
			clipAmmo = 0;
		}
	}
	
	public int getShotDelay()
	{
		return shotDelay;
	}
	
	public Map getMap()
	{
		return map;
	}
}