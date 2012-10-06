require("res/scripts/lua/ModLoader");

--luajava.newInstance("net.foxycorndog.jdoogl.sprites.Texture", "res/images/GUI/TextField.png", "PNG", true, false)

local commandField = luajava.newInstance("net.foxycorndog.jdoogl.components.ImageTextField", gl:getWhite());
commandField:setWidth(90 * 3);
commandField:setHeight(8 * 3);
commandField:setLocation(0, 15 * 3);
frame:add(commandField);

local active       = false;
local instantMine  = false;

local stringUtil   = luajava.bindClass("net.foxycorndog.jdoutil.StringUtil");

Direction       = luajava.bindClass("net.foxycorndog.p1xeland.actors.Actor$Direction");

function exec(str)
	return pcall( function() loadstring("" .. str)(); end );
end

function startsWith(full, start)
	s, e = string.find(full, start);
	
	if (s == nil)
	then
		return false;
	end
	
	return s == 1;
end

function getLastNonDigitIndex(str1)
	local leng = string.len(str1);

	local index = getLastNonDigitIndex2(str1, leng);
	
	if (index == -1)
	then
		return nil;
	else
		return index;
	end
end

function getLastNonDigitIndex2(str2, lastInd)
	for i = lastInd, 1, -1
	do
		local ch = string.sub(str2, i, i);
		
		if (stringUtil:isNumber(ch) == false)
		then
			return i;
		end
	end
	
	return -1;
end

function tick()
	if (p1xeland:nextKey())
	then
		if (isKeyDown(keyboard.KEY_SLASH) and active == false)
		then
			if (active == false)
			then
				active = true;
				p1xeland:setFrozen(true);
				
				commandField:setText("");
			end
		elseif (isKeyDown(keyboard.KEY_RETURN) and active == true)
		then
			if (active)
			then
				active = false;
				p1xeland:setFrozen(false);
				
				local command = commandField:getText();
				
				local s, e = string.find(command, "/")
				
				local len2;
				local subst;
				local origCommand = command;
				
				if (s ~= nil and e ~= nil)
				then
					len2 = string.len(command);
					
					local lastSlash = 1;
					
					for i = 1, len2
					do
						if (string.sub(command, i, i) == "/")
						then
							lastSlash = lastSlash + 1;
						end
					end
					
					subst = string.sub(command, lastSlash, len);
				
					origCommand = subst;
				
					command = string.lower(origCommand);
				end
				
				if (startsWith(command, "instantmine"))
				then
					if (instantMine)
					then
						instantMine = false;
					else
						instantMine = true;
					end
				elseif (startsWith(command, "setspeed "))
				then wait(4);
					pcall( function()
								local speed = string.sub(command, 10, string.len(command));
								
								player:setSpeed(speed);
							end );
				elseif (startsWith(command, "setreach "))
				then
					pcall( function()
								local reach = string.sub(command, 10, string.len(command));
								
								player:setReach(reach);
								
								print(reach);
							end );
				elseif (startsWith(command, "fly"))
				then
					if (player:isFlying())
					then
						player:setFlying(false);
					else
						player:setFlying(true);
					end
				elseif (startsWith(command, "noclip"))
				then
					if (player:isCollidable())
					then
						player:setCollidable(false);
					else
						player:setCollidable(true);
					end
				elseif (startsWith(command, "give "))
				then
					pcall(
						function()
							local len2      = string.len(command);
				
							local lastSpace = getLastNonDigitIndex(command);
							
							local quantity  = string.sub(command, lastSpace + 1, len2)
							
							local itemName  = string.sub(command, 6, lastSpace - 1);
							
							giveObject(itemName, quantity);
							
							player:getQuickBar():refreshInventory();
						end
					);
				elseif (startsWith(command, "drop "))
				then
					pcall(
						function()
							local len2      = string.len(command);
				
							local lastSpace = getLastNonDigitIndex(command);
							
							local quantity  = string.sub(command, lastSpace + 1, len2)
							
							local itemName  = string.sub(command, 6, lastSpace - 1);
							
							removeObject(itemName, quantity);
							
							player:getQuickBar():refreshInventory();
						end
					);
				elseif (startsWith(command, "settime "))
				then
					pcall(
						function()
							local len2       = string.len(command);
				
							local lastSpace  = getLastNonDigitIndex(command);
							local lastSpace2 = getLastNonDigitIndex2(command, lastSpace - 1);
							local lastSpace3 = getLastNonDigitIndex2(command, lastSpace2 - 1);
							
							local len01 = len2 - lastSpace;
							local len02 = lastSpace - lastSpace2 - 1;
							local len03 = lastSpace2 - lastSpace3 - 1;
							
							local hours   = string.sub(command, lastSpace3 + 1, lastSpace2 - 1);
							local minutes = string.sub(command, lastSpace2 + 1, lastSpace - 1);
							local seconds = string.sub(command, lastSpace + 1, len2);
							
							map:getDayCycle():setTime(hours, minutes, seconds);
						end
					);
				else
				
					local status, error = exec(origCommand);
					
					
					if (error ~= nil)
					then
						print ("STATUS: " + tostring(status) .. " : " .. error);
					end
				end
			end
		elseif (isKeyDown(keyboard.KEY_ESCAPE) and active == true)
		then
			active = false;
			p1xeland:setFrozen(false);
			
			commandField:setText("");
		end
	end
	
	update();
end

function update()
	if (instantMine and mouse:isButtonDown(0))
	then
		p1xeland:setBlockDamage(9999);
	elseif (instantMine == false)
	then
		
	end
end

function render()
	if (active)
	then
		commandField:setFocused(true);
		
		gl:beginManipulation();
		gl:scalef(2, 2, 1);
		commandField:render(0, 0, 0, 150);
		gl:endManipulation();
	end
end