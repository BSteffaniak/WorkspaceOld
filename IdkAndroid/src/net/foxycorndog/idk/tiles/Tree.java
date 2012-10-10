package net.foxycorndog.idk.tiles;

import static net.foxycorndog.idk.Idk.OBJECT_SIZE;
import static net.foxycorndog.idk.Idk.TEXTURE_SIZE;
import static net.foxycorndog.idk.Idk.scale;
import static net.foxycorndog.idk.Idk.tileSize;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;

public class Tree extends Tile
{
	public Tree(int x, int y, int z, int width, int height, int color, boolean collidable, String[] names)
	{
		super(x, y, z, 0, 0, width, height, color, collidable, names);
		
		Tile top    = new Tile(0, 2, -3, 0, 1, 1, 1, -1, false, new String[] { "Tree Top" });
		Tile bottom = new Tile(0, 3, -9, 0, 0, 1, 1, -1, true,  new String[] { "Tree Bottom" });
		
		setTile(TileId.TREE_TOP,    top);
		setTile(TileId.TREE_BOTTOM, bottom);
		
		super.setSubTiles(new Tile[] { top, bottom });
	}
	
	public float[] addVerticesToArray(int x, int y, int offsetX, int offsetY, float vertices[], int arrayWidth)
	{
		for (Tile tile : getSubTiles())
		{
			vertices = GL.addRectVertexArrayf((x * tileSize) + (offsetX * scale) + (tile.getRelativeX() * tileSize), (y * tileSize) + (offsetY * scale) + (tile.getRelativeY() * tileSize), tile.getZ(), tile.getWidth() * tileSize, tile.getHeight() * tileSize, ((x + tile.getRelativeX()) + (y + tile.getRelativeY()) * arrayWidth) * OBJECT_SIZE, vertices);
		}
		
		return vertices;
	}
	
	public float[] addTextureToArray(int x, int y, float textures[], int arrayWidth, SpriteSheet sprites)
	{
		for (Tile tile : getSubTiles())
		{
			textures = GL.addRectTextureArrayf(sprites, tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight(), ((x + tile.getRelativeX()) + (y + tile.getRelativeY()) * arrayWidth) * TEXTURE_SIZE, textures);
		}
		
		return textures;
	}
	
	public void addIdToArray(int x, int y, byte array[], int arrayWidth)
	{
		for (Tile tile : getSubTiles())
		{
			array[(x + tile.getRelativeX()) + (y + tile.getRelativeY()) * arrayWidth] = tile.getId();
		}
	}
	
	public void addTileOffsets(int x, int y, byte offsetX, byte offsetY, byte array[][], int arrayWidth)
	{
		for (Tile tile : getSubTiles())
		{
			array[(x + tile.getRelativeX()) + (y + tile.getRelativeY()) * arrayWidth] = new byte[] { offsetX, offsetY };
		}
	}
}