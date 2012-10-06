module(..., package.seeall);

vector = {};
vector.prototype = { x = 0, y = 0, randOffset = 0, lifespan = 50 };
vector.metatable = { __index = vector.prototype };

function vector:new()
	local object = {};
	
	setmetatable(object, vector.metatable);
	
	return object;
end

showParticles        = false;

local locations      = {};

local particlesQ     = 2000;

local lightBuffer    = luajava.bindClass("net.foxycorndog.jdoutil.LightBuffer");

local verticesBuffer = luajava.new(lightBuffer, particlesQ * 4 * 2);
local texturesBuffer = luajava.new(lightBuffer, particlesQ * 4 * 2);

for i = 1, particlesQ
do
	locations[i] = vector:new();
	
	locations[i].x = 0;
	locations[i].y = 0;
	
	locations[i].randOffset = math.random();
	
	locations[i].lifeSpan = 200;
	
	texturesBuffer:setData((i - 1) * 4 * 2, gl:addRectTextureArrayf(tile:getTerrain():getImageOffsetsf(1, 0, 1, 1), 0, nil));
end

--print(tile:getTerrain():getImageOffsetsf(1, 1, 1, 1)[1]);


--local task = luajava.createProxy("net.foxycorndog.jdoutil.Task",
--{
--	run = function(index)
--		gl:setColor
--		return true;
--	end
--});

function tick()
	if (showParticles)
	then
		for i = 1, particlesQ
		do
			locations[i].x = locations[i].x + (math.random() -  0.5) * 8;
			locations[i].y = locations[i].y - 1 - math.abs(locations[i].randOffset);
			
			if (locations[i].y <= -locations[i].lifeSpan * (locations[i].randOffset))
			then
				locations[i].x = 0;
				locations[i].y = 0;
				
				locations[i].randOffset = math.random() - 0.5;
			end
			
--			locations[i].x = 0;
--			locations[i].y = 0;
			
			verticesBuffer:setData((i - 1) * 4 * 2, gl:addRectVertexArrayf(-locations[i].y, locations[i].x, 1, 1, 0, nil));
		end
	end
end

function render()
	if (showParticles)
	then
		gl:beginManipulation();
			gl:scalef(p1xeland:getScale(), p1xeland:getScale(), 1);
		
			gl:translatef(player:getScreenX() + (player:getWidth() / 2) + 4, player:getScreenY() + 12 + 14, 0);
			
--			gl:setColori(0, 0, 0, 255);
			
			tile:getTerrain():bind();
			
			gl:renderQuads(verticesBuffer, texturesBuffer, tile:getTerrain(), 0, particlesQ);

--			for i = 1, particlesQ
--			do
--				gl:glBegin(7);
--					gl:glVertex3d(locations[i].x,     locations[i].y,     0.0);
--					gl:glVertex3d(locations[i].x + 1, locations[i].y, 0.0);
--					gl:glVertex3d(locations[i].x + 1, locations[i].y + 1, 0.0);
--					gl:glVertex3d(locations[i].x    , locations[i].y + 1, 0.0);
--				gl:glEnd();
--			end
			
--			gl:setColori(255, 255, 255, 255);
		gl:endManipulation();
	end
end