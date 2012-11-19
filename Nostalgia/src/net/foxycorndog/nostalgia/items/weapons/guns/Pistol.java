package net.foxycorndog.nostalgia.items.weapons.guns;

import net.foxycorndog.jdoogl.camera.Camera;
import net.foxycorndog.jdoogl.geometry.Point;
import net.foxycorndog.nostalgia.items.weapons.Bullet;
import net.foxycorndog.nostalgia.map.Map;

public class Pistol extends Gun
{
	public Pistol(Map map)
	{
		super(5999, 4, 5, 0.3f, map);
	}
}