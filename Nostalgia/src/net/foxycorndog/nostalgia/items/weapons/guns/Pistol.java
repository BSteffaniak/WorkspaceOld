package net.foxycorndog.nostalgia.items.weapons.guns;

import net.foxycorndog.jdooal.audio.AL;
import net.foxycorndog.jdoogl.camera.Camera;
import net.foxycorndog.jdoogl.geometry.Point;
import net.foxycorndog.nostalgia.items.weapons.Bullet;
import net.foxycorndog.nostalgia.map.Map;

public class Pistol extends Gun
{
	public Pistol(Map map)
	{
		super(5, 4, -1, 500, AL.genSound("res/sounds/pistolshot.wav"), AL.genSound("res/sounds/pistolreload.wav"), 0.3f, map);
	}
}