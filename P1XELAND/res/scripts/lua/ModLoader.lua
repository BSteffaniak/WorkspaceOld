local thread   = luajava.bindClass("java.lang.Thread");

function setPlayer(p)
	player = p;
end

function setMap(m)
	map = m;
end

function setP1xeland(p)
	p1xeland = p;
end

function setResources(res, len)
	fileNames = res;
	
	resources = {};
	
	task = {};
	
	length    = len;
	
	for i = 1, length do
		resources[i] = require("res/scripts/lua/" .. fileNames[i]);
	end
end

function wait(time)
	thread:sleep(tonumber(time) * 1000);

--	p1xeland:waitMod(tonumber(id), tonumber(time) * 1000000000);
end

function start()
	mouse    = luajava.bindClass("net.foxycorndog.jdoogl.input.MouseInput");
	keyboard = luajava.bindClass("net.foxycorndog.jdoogl.input.KeyboardInput");
	frame    = luajava.bindClass("net.foxycorndog.jdoogl.components.Frame");
	gl       = luajava.bindClass("net.foxycorndog.jdoogl.GL");
	color    = luajava.bindClass("net.foxycorndog.jdoogl.Color");
	
	tile     = luajava.bindClass("net.foxycorndog.p1xeland.items.tiles.Tile");
	item     = luajava.bindClass("net.foxycorndog.p1xeland.items.Item");
	
--	do
--		local p =
--		[========================================[
--			test
--			adsf
--			adsf
--			adsf
--			asdf
--			asdf
--			{
--				tabbed!
--				[[
--					Yo..
--				]]
--			}
--		]========================================];
--		
--		p = string.sub(p, 4, string.len(p) - 3);
--		p = string.gsub(p, "\n\t\t\t", "\n");
--		
--		print(p);
--	end
end

function addTile(x, y, index, name, replace)
	tile2 = tile:getTile(name);
	
	map:addTile(x, y, index, tile2, replace);
end

function giveObject(name, quantity)
	quantity = tonumber(quantity);
	name     = tostring(name);
	
	if (quantity < 1)
	then
		return;
	end
	
	local obj = tile:getTile(name);
	
	if (obj ~= nil)
	then
		player:giveObject(obj, quantity);
	else
		obj = item:getItem(name);
		
		if (obj ~= nil)
		then
			player:giveObject(obj, quantity);
		end
	end
end

function removeObject(name, quantity)
	quantity = tonumber(quantity);
	name     = tostring(name);
	
	if (quantity < 1)
	then
		return;
	end
	
	local obj = tile:getTile(name);
	
	if (obj ~= nil)
	then
		player:dropObject(obj, quantity);
	else
		obj = item:getItem(name);
		
		if (obj ~= nil)
		then
			player:dropObject(obj, quantity);
		end
	end
end

function isKeyDown(name)
	if (type(name) == "string")
	then
		return keyboard:isKeyDown(keyboard:getKey(name));
	elseif (type(name) == "number")
	then
		return keyboard:isKeyDown(name);
	end
end

function tick(id2, delta, dfps)
	id = id2 + 1;
	
	resources[id].tick();
end

function render(id2)
	id = id2 + 1;

	resources[id].render();
end