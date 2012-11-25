package net.foxycorndog.nostalgia.items.weapons.guns;

import net.foxycorndog.jdooal.audio.AL;
import net.foxycorndog.nostalgia.map.Map;

public class MachineGun extends Gun
{
	public MachineGun(Map map)
	{
		super(50, 4, 4, 5700, AL.genSound("res/sounds/machinegunshot.wav"), AL.genSound("res/sounds/machinegunreload.wav"), 0.4f, map);
	}
}