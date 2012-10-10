package net.foxycorndog.jdoogl.image.imagemap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES10;
import android.opengl.GLES11;
import android.opengl.GLUtils;
import android.util.Log;
import android.widget.ImageView;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;

import net.foxycorndog.jdoogl.GL;

public class Texture extends ImageMap
{
	private String  location, format;
	private int     width, height;
	private float   texWid, texHei;
	private int     texture;
	private boolean flipped;
	
	public Texture(Resources res, int id)
	{
		InputStream is = res.openRawResource(id);
		
		Bitmap bmp = null;
		
		bmp = BitmapFactory.decodeStream(is);
		
		this.location = location;
		this.format   = format;
		this.flipped  = flipped;
		
		this.texture       = loadTexture(bmp);
		
//		init();
	}
	
	private int newTextureId()
	{
		int textureId[] = new int[1];
		
		GLES10.glGenTextures(1, textureId, 0);
		
		return textureId[0];
	}
	
	private int loadTexture(Bitmap bmp)
	{
		// In which ID will we be storing this texture?
	    int id = newTextureId();
	    
	    // We need to flip the textures vertically:
//	    Matrix flip = new Matrix();
//	    flip.postScale(1f, -1f);
	    
	    // This will tell the BitmapFactory to not scale based on the device's pixel density:
	    // (Thanks to Matthew Marshall for this bit)
//	    BitmapFactory.Options opts = new BitmapFactory.Options();
//	    opts.inScaled = false;
	    
//	    BitmapDrawable drawable = (BitmapDrawable)img.getDrawable();
//	    
//	    // Load up, and flip the texture:
//	    Bitmap temp = drawable.getBitmap();//BitmapFactory.decodeResource(activity.getResources(), resourceId, opts);
//	    
//	    Bitmap bmp = Bitmap.createBitmap(temp, 0, 0, temp.getWidth(), temp.getHeight(), flip, true);
	    
	    this.width  = bmp.getWidth();
	    this.height = bmp.getHeight();
	    
	    this.genTexDimensions();
	    
//	    temp.recycle();
	    
	    GLES10.glBindTexture(GLES10.GL_TEXTURE_2D, id);
	    
	    // Set all of our texture parameters:
//	    GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MIN_FILTER, GLES10.GL_LINEAR_MIPMAP_NEAREST);
//	    GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MAG_FILTER, GLES10.GL_LINEAR_MIPMAP_NEAREST);
//	    GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_WRAP_S,     GLES10.GL_REPEAT);
//	    GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_WRAP_T,     GLES10.GL_REPEAT);
	    
//	    ByteBuffer bb = extract(bmp);
	    
//	    GLES10.glTexImage2D(GLES10.GL_TEXTURE_2D, 0, GLES10.GL_RGBA, width, height, 0, GLES10.GL_RGBA, GLES10.GL_UNSIGNED_BYTE, bb);
	    GLUtils.texImage2D(GLES10.GL_TEXTURE_2D, 0, bmp, 0);
	    
	    GLES11.glTexParameteri(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MIN_FILTER, GLES10.GL_NEAREST);
	    GLES11.glTexParameteri(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MAG_FILTER, GLES10.GL_NEAREST);
	    
//	    // Generate, and load up all of the mipmaps:
//	    for(int level=0, height = bmp.getHeight(), width = bmp.getWidth(); true; level++) {
//	        // Push the bitmap onto the GPU:
//	        GLUtils.texImage2D(GLES10.GL_TEXTURE_2D, level, bmp, 0);
//	        
//	        // We need to stop when the texture is 1x1:
//	        if(height==1 && width==1) break;
//	        
//	        // Resize, and let's go again:
//	        width >>= 1; height >>= 1;
//	        if(width<1)  width = 1;
//	        if(height<1) height = 1;
//	        
//	        Bitmap bmp2 = Bitmap.createScaledBitmap(bmp, width, height, true);
//	        bmp.recycle();
//	        bmp = bmp2;
//	    }
	    
	    bmp.recycle();
	    
	    GLES10.glEnable(GLES10.GL_TEXTURE_2D);
	    
	    return id;
	}
	
	private void genTexDimensions()
	{
		int pow = 2;
		
		while (pow < width)
		{
			pow *= 2;
		}
		
		texWid = (float)width / pow;
		
		pow = 2;
		
		while (pow < height)
		{
			pow *= 2;
		}
		
		texHei = (float)height / pow;
		
//		Log.v("", "" + texWid + ", " + texHei);
	}
	
	private ByteBuffer extract(Bitmap bmp)
	{
		ByteBuffer bb = ByteBuffer.allocate(bmp.getWidth() * bmp.getHeight() * 4);
		
		bb.order(ByteOrder.BIG_ENDIAN);
		
		IntBuffer ib = bb.asIntBuffer();
		
		for (int y = bmp.getHeight() - 1; y > -1; y --)
		{
			for (int x = 0; x < bmp.getWidth(); x ++)
			{
				int pix = bmp.getPixel(x, y);//bmp.getHeight() - y - 1);
				
				int alpha = ((pix >> 24) & 0xff);
				int red   = ((pix >> 16) & 0xff);
				int green = ((pix >> 8 ) & 0xff);
				int blue  = ((pix      ) & 0xff);
				
				ib.put(red << 24 | green << 16 | blue << 8 | alpha);
			}
		}
		
		bb.position(0);
		
		return bb;
	}
	
	public float[] getImageOffsetsf()
	{
		float offsets[] = new float[4];
		
		float w = texWid;
		float h = texHei;
		
		offsets[0] = 0;
		offsets[1] = 0;
		offsets[2] = 1;//w;
		offsets[3] = 1;//h;
		
		return offsets;
	}
	
	public void bind()
	{
		GLES10.glBindTexture(GLES10.GL_TEXTURE_2D, texture);
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
}