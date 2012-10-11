package net.foxycorndog.jdoutil;

import java.io.InputStream;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class ImageUtil
{
	public static Bitmap getBitmap(Resources res, int id)
	{
//		InputStream is = res.openRawResource(id);
//		
//		Bitmap bmp = null;
//		
//		bmp = BitmapFactory.decodeStream(is);
		
		Options options  = new BitmapFactory.Options();
		options.inScaled = false;
		
		return BitmapFactory.decodeResource(res, id, options);//bmp;
	}
}