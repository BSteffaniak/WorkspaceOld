package net.foxycorndog.jdoogl.noise;

import java.awt.Point;

public class LineNoise
{
	public static int[] generateCircleHill(int numCircles, int circleRadius, int width, int maxHeight)
	{
		int circleRadiusSquared = circleRadius * circleRadius;

		// make + fill a 2D array
		int heightMap[] = new int[width];
//		heightMap.fill(50);

		// iterate once per hill you want to add (given by numCircles)
		for(int circleIndex = 0; circleIndex < numCircles; circleIndex ++)
		{
			int pd_x = (int)(Math.random() * maxHeight);

			// no iterate over all the values around the hill
			for(int i = 0; i < width; i++)
			{
					// only edit those within a certain distance
					int pd_d = (pd_x - i)*(pd_x - i);
					if(pd_d < circleRadiusSquared)
					{
						
						int circleHeightIncrease = 2;
						// change the height trigonometrically
						int pd_a = (int)((circleHeightIncrease / 2f) * (1 + Math.cos(Math.PI * pd_d / (circleRadiusSquared))));
						heightMap[i] = Math.min(heightMap[i] + pd_a, maxHeight);
					}
			}
		}

		return heightMap;
	}
	
	//Randomly displaces color value for midpoint depending on size
	//of grid piece.
	public static float displace(float num, int width, int height)
	{
		float max = num / (float)(width + height) * 3;
		return ((float)Math.random() - 0.5f) * max;
	}
	
	//This is something of a "helper function" to create an initial grid
	//before the recursive function is called.	
	public static float[][] drawPlasma(int width, int height)
	{
		float array[][] = new float[width][height];
		
		float c1, c2, c3, c4;
		
		//Assign the four corners of the intial grid random color values
		//These will end up being the colors of the four corners of the applet.		
		c1 = (float)Math.random();
		c2 = (float)Math.random();
		c3 = (float)Math.random();
		c4 = (float)Math.random();
		
		
		
		divideGrid(0, 0, width * 2 , height * 2 , c1, c2, c3, c4, array);
		
		return array;
	}
	
	//This is the recursive function that implements the random midpoint
	//displacement algorithm.  It will call itself until the grid pieces
	//become smaller than one pixel.	
	public static void divideGrid(float x, float y, float width, float height, float c1, float c2, float c3, float c4, float array[][])
	{
		float Edge1, Edge2, Edge3, Edge4, Middle;
		float newWidth = width / 2;
		float newHeight = height / 2;

		if (width > 2 || height > 2)
		{	
			Middle = (c1 + c2 + c3 + c4) / 4 + displace(newWidth + newHeight, (int)width, (int)height);	//Randomly displace the midpoint!
			Edge1 = (c1 + c2) / 2;	//Calculate the edges by averaging the two corners of each edge.
			Edge2 = (c2 + c3) / 2;
			Edge3 = (c3 + c4) / 2;
			Edge4 = (c4 + c1) / 2;
			
			//Make sure that the midpoint doesn't accidentally "randomly displaced" past the boundaries!
			if (Middle < 0)
			{
				Middle = 0;
			}
			else if (Middle > 1.0f)
			{
				Middle = 1.0f;
			}
			
			//Do the operation over again for each of the four new grids.			
			divideGrid(x, y, newWidth, newHeight, c1, Edge1, Middle, Edge4, array);
			divideGrid(x + newWidth, y, newWidth, newHeight, Edge1, c2, Edge2, Middle, array);
			divideGrid(x + newWidth, y + newHeight, newWidth, newHeight, Middle, Edge2, c3, Edge3, array);
			divideGrid(x, y + newHeight, newWidth, newHeight, Edge4, Middle, Edge3, c4, array);
		}
		else	//This is the "base case," where each grid piece is less than the size of a pixel.
		{
			//The four corners of the grid piece will be averaged and drawn as a single pixel.
			float c = (c1 + c2 + c3 + c4) / 4;
			
			array[(int)x / 2][(int)y / 2] = c;
		}
	}
	
//	public static int[] generateLimitedRandom(int width, int maxHeight)
//	{
//		// create a two-dimensional array
//		int heightMap[] = new int[width];
//
//		// iterate over every index
//		for(int i = 0; i < width; i++){
//				int a = 0;
//
//				// grab a previous value
//				if(i != 0){
//					a = (heightMap.get(i - 1, j) + heightMap[i - 1] / 2;
//				}
//				else
//				{
//					a = (int)(Math.random() * maxHeight);
//				}
//
//				// add a random number, capped my a maximum increase
//				var h = a + maximumHeightIncrease * (Math.random() - 1 / 2);
//
//				// set the value at that index, between predefined boundaries
//				heightMap.set(i, j, Math.min(h, maxHeight));
//			}
//		}
//
//		return heightMap;
//	}
	
	 /**
	  * Enumerates the noise quality.
	  */
	   public enum NoiseQuality
	   {

	     /**
	      * Generates coherent noise quickly. When a coherent-noise function with
	      * this quality setting is used to generate a bump-map image, there are
	      * noticeable "creasing" artifacts in the resulting image. This is
	      * because the derivative of that function is discontinuous at integer
	      * boundaries.
	      */
	     QUALITY_FAST,

	     /**
	      * Generates standard-quality coherent noise. When a coherent-noise
	      * function with this quality setting is used to generate a bump-map
	      * image, there are some minor "creasing" artifacts in the resulting
	      * image. This is because the second derivative of that function is
	      * discontinuous at integer boundaries.
	      */
	     QUALITY_STD,

	     /** Generates the best-quality coherent noise. When a coherent-noise
	      * function with this quality setting is used to generate a bump-map
	      * image, there are no "creasing" artifacts in the resulting image. This
	      * is because the first and second derivatives of that function are
	      * continuous at integer boundaries.
	      */
	     QUALITY_BEST
	   }

	   static final int X_NOISE_GEN = 1619;
	   static final int Y_NOISE_GEN = 31337;
	   static final int Z_NOISE_GEN = 6971;
	   static final int SEED_NOISE_GEN = 1013;
	   static final int SHIFT_NOISE_GEN = 8;

	   public static double gradientCoherentNoise3D (double x, double y, double z, int seed,
	         NoiseQuality noiseQuality)
	   {
	      
	      // Create a unit-length cube aligned along an integer boundary. This cube
	      // surrounds the input point.
	      int x0 = (x > 0.0? (int)x: (int)x - 1);
	      int x1 = x0 + 1;
	      int y0 = (y > 0.0? (int)y: (int)y - 1);
	      int y1 = y0 + 1;
	      int z0 = (z > 0.0? (int)z: (int)z - 1);
	      int z1 = z0 + 1;

	      // Map the difference between the coordinates of the input value and the
	      // coordinates of the cube's outer-lower-left vertex onto an S-curve.
	      double xs = 0, ys = 0, zs = 0;
	      switch (noiseQuality)
	      {
	         case QUALITY_FAST:
	            xs = (x - (double)x0);
	            ys = (y - (double)y0);
	            zs = (z - (double)z0);
	            break;
	         case QUALITY_STD:
	            xs = Interp.SCurve3 (x - (double)x0);
	            ys = Interp.SCurve3 (y - (double)y0);
	            zs = Interp.SCurve3 (z - (double)z0);
	            break;
	         case QUALITY_BEST:
	            xs = Interp.SCurve5 (x - (double)x0);
	            ys = Interp.SCurve5 (y - (double)y0);
	            zs = Interp.SCurve5 (z - (double)z0);
	            break;
	      }

	      // Now calculate the noise values at each vertex of the cube. To generate
	      // the coherent-noise value at the input point, interpolate these eight
	      // noise values using the S-curve value as the interpolant (trilinear
	      // interpolation.)
	      double n0, n1, ix0, ix1, iy0, iy1;
	      n0 = gradientNoise3D (x, y, z, x0, y0, z0, seed);
	      n1 = gradientNoise3D (x, y, z, x1, y0, z0, seed);
	      ix0 = Interp.linearInterp (n0, n1, xs);
	      n0 = gradientNoise3D (x, y, z, x0, y1, z0, seed);
	      n1 = gradientNoise3D (x, y, z, x1, y1, z0, seed);
	      ix1 = Interp.linearInterp (n0, n1, xs);
	      iy0 = Interp.linearInterp (ix0, ix1, ys);
	      n0 = gradientNoise3D (x, y, z, x0, y0, z1, seed);
	      n1 = gradientNoise3D (x, y, z, x1, y0, z1, seed);
	      ix0 = Interp.linearInterp (n0, n1, xs);
	      n0 = gradientNoise3D (x, y, z, x0, y1, z1, seed);
	      n1 = gradientNoise3D (x, y, z, x1, y1, z1, seed);
	      ix1 = Interp.linearInterp (n0, n1, xs);
	      iy1 = Interp.linearInterp (ix0, ix1, ys);

	      return Interp.linearInterp (iy0, iy1, zs);
	   }

	   public static double gradientNoise3D (double fx, double fy, double fz, int ix,
	         int iy, int iz, int seed)
	   {
	      
	      VectorTable vectorTable = new VectorTable();
	      // Randomly generate a gradient vector given the integer coordinates of the
	      // input value. This implementation generates a random number and uses it
	      // as an index into a normalized-vector lookup table.
	      int vectorIndex = (X_NOISE_GEN * ix
	            + Y_NOISE_GEN * iy
	            + Z_NOISE_GEN * iz
	            + SEED_NOISE_GEN * seed)
	            & 0xffffffff;
	      
	      vectorIndex ^= (vectorIndex >> SHIFT_NOISE_GEN);
	      vectorIndex &= 0xff;
	      
	      double xvGradient = vectorTable.getRandomVectors(vectorIndex, 0);
	      double yvGradient = vectorTable.getRandomVectors(vectorIndex, 1);
	      double zvGradient = vectorTable.getRandomVectors(vectorIndex, 2);
	      // array size too large when using this original, changed to above for all 3
	      // double zvGradient = vectorTable.getRandomVectors(vectorIndex << 2, 2);

	      // Set up us another vector equal to the distance between the two vectors
	      // passed to this function.
	      double xvPoint = (fx - (double)ix);
	      double yvPoint = (fy - (double)iy);
	      double zvPoint = (fz - (double)iz);

	      // Now compute the dot product of the gradient vector with the distance
	      // vector. The resulting value is gradient noise. Apply a scaling value
	      // so that this noise value ranges from -1.0 to 1.0.
	      return ((xvGradient * xvPoint)
	            + (yvGradient * yvPoint)
	            + (zvGradient * zvPoint)) * 2.12;
	   }

	   public static int intValueNoise3D (int x, int y, int z, int seed)
	   {
	      // All constants are primes and must remain prime in order for this noise
	      // function to work correctly.
	      int n = (X_NOISE_GEN * x
	            + Y_NOISE_GEN * y
	            + Z_NOISE_GEN * z
	            + SEED_NOISE_GEN * seed)
	            & 0x7fffffff;
	      
	      n = (n >> 13) ^ n;
	  
	      return (n * (n * n * 60493 + 19990303) + 1376312589) & 0x7fffffff;
	   }

	   public static double valueCoherentNoise3D (double x, double y, double z, int seed,
	         NoiseQuality noiseQuality)
	   {
	      // Create a unit-length cube aligned along an integer boundary. This cube
	      // surrounds the input point.
	      int x0 = (x > 0.0? (int)x: (int)x - 1);
	      int x1 = x0 + 1;
	      int y0 = (y > 0.0? (int)y: (int)y - 1);
	      int y1 = y0 + 1;
	      int z0 = (z > 0.0? (int)z: (int)z - 1);
	      int z1 = z0 + 1;

	      // Map the difference between the coordinates of the input value and the
	      // coordinates of the cube's outer-lower-left vertex onto an S-curve.
	      double xs = 0, ys = 0, zs = 0;
	      switch (noiseQuality)
	      {
	         case QUALITY_FAST:
	            xs = (x - (double)x0);
	            ys = (y - (double)y0);
	            zs = (z - (double)z0);
	            break;
	         case QUALITY_STD:
	            xs = Interp.SCurve3 (x - (double)x0);
	            ys = Interp.SCurve3 (y - (double)y0);
	            zs = Interp.SCurve3 (z - (double)z0);
	            break;
	         case QUALITY_BEST:
	            xs = Interp.SCurve5 (x - (double)x0);
	            ys = Interp.SCurve5 (y - (double)y0);
	            zs = Interp.SCurve5 (z - (double)z0);
	            break;
	      }

	      // Now calculate the noise values at each vertex of the cube. To generate
	      // the coherent-noise value at the input point, interpolate these eight
	      // noise values using the S-curve value as the interpolant (trilinear
	      // interpolation.)
	      double n0, n1, ix0, ix1, iy0, iy1;
	      n0 = valueNoise3D (x0, y0, z0, seed);
	      n1 = valueNoise3D (x1, y0, z0, seed);
	      ix0 = Interp.linearInterp (n0, n1, xs);
	      n0 = valueNoise3D (x0, y1, z0, seed);
	      n1 = valueNoise3D (x1, y1, z0, seed);
	      ix1 = Interp.linearInterp (n0, n1, xs);
	      iy0 = Interp.linearInterp (ix0, ix1, ys);
	      n0 = valueNoise3D (x0, y0, z1, seed);
	      n1 = valueNoise3D (x1, y0, z1, seed);
	      ix0 = Interp.linearInterp (n0, n1, xs);
	      n0 = valueNoise3D (x0, y1, z1, seed);
	      n1 = valueNoise3D (x1, y1, z1, seed);
	      ix1 = Interp.linearInterp (n0, n1, xs);
	      iy1 = Interp.linearInterp (ix0, ix1, ys);
	  
	      return Interp.linearInterp (iy0, iy1, zs);
	   }

	   public static double valueNoise3D (int x, int y, int z, int seed)
	   {
	      return 1.0 - ((double)intValueNoise3D (x, y, z, seed) / 1073741824.0);
	   }
	   
	   /** Modifies a floating-point value so that it can be stored in a
	    * int32 variable.
	    *
	    * @param n A floating-point number.
	    *
	    * @returns The modified floating-point number.
	    *
	    * This function does not modify @a n.
	    *
	    * In libnoise, the noise-generating algorithms are all integer-based;
	    * they use variables of type int32. Before calling a noise
	    * function, pass the @a x, @a y, and @a z coordinates to this function to
	    * ensure that these coordinates can be cast to a int32 value.
	    *
	    * Although you could do a straight cast from double to int32, the
	    * resulting value may differ between platforms. By using this function,
	    * you ensure that the resulting value is identical between platforms.
	    */
	   public static double makeInt32Range (double n)
	   {
	     if (n >= 1073741824.0)
	        return (2.0 * (n % 1073741824.0)) - 1073741824.0;
	     else if (n <= -1073741824.0)
	        return (2.0 * (n % 1073741824.0)) + 1073741824.0;
	     else
	        return n;
	   }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public static int[] generateNoise(int amplitude, int frequency, int length)
//	{
//		int noise[] = new int[length];
//		
//		boolean rise = true;
//		
//		Point destination = generateDestination(amplitude, frequency, length, 0,  rise);
//		
////		destination.x = 22;
////		destination.y = 12;
//		
//		float halfX = destination.x / 2f;
//		float halfY = destination.y / 2f;
//		
////		System.out.println(destination.x + ", " + destination.y + " ------------------");
//		
//		for (int i = 1; i < length; i ++)
//		{
//			int outcome = 0;
//			
////			int freq    = (int)(Math.random() * frequency);
////			
////			int amp     = (int)(Math.random() * amplitude);
////			
////			if (amp < freq)
////			{
////				if (rising == -1)
////				{
////					rising = 1;
////				}
////				else
////				{
////					rising = -1;
////				}
////			}
////			else
////			{
////				
////			}
////			
////			outcome  = rising == 1 ? noise[i - 1] + 1 : (rising == 0 ? noise[i - 1] : noise[i - 1] - 1);
//			
//			if (destination.y == 0)
//			{
//				
//			}
//			else
//			{
//				if (i >= destination.x)
//				{
//					System.out.print("From: " + destination.x + ", " + destination.y + " to ");
//					
//					rise = !rise;
//					
//					destination = generateDestination(amplitude, frequency, length - i, noise[i], rise);
//					
//					halfX = destination.x / 2f;
//					halfY = destination.y / 2f;
//					
//					System.out.println(destination.x + ", " + destination.y);
//				}
//				if (i >= halfX)
//				{
//					int dx = (int)(-halfX + (i - halfX));
//					int dy = (int)(-halfY + (i - halfY));
//					
//					if (destination.y == 0 || ((destination.x / destination.y) / 2) == 0)
//					{
//						
//					}
//					else
//					{
////						outcome = -(dx / ((destination.x / destination.y) / 2));
//						outcome = -(int)((dx * dx) * (1f / (halfX * (halfX / halfY)))) + (int)halfY * 2;
//					}
//				}
//				else
//				{
//					if (halfY == 0 || halfX / halfY == 0)
//					{
//						
//					}
//					else
//					{
//						outcome = (int)((i * i) * (1f / (halfX * (halfX / halfY))));
//					}
//				}
//			}
////						System.out.println(i + ", " + outcome);
//			
//			noise[i] = outcome;
//		}System.out.println();
//		
//		return noise;
//	}
//	
//	public static int[] generateNoise(int amplitude, int frequency, int length, int prev[])
//	{
//		int noise[] = new int[length];
//		
//		for (int i = 0; i < length; i ++)
//		{
//			
//		}
//		
//		return noise;
//	}
//	
//	private static Point generateDestination(int amplitude, int frequency, int length, int y, boolean rise)
//	{
//		int destX = (int)(Math.random() * amplitude);
//		
//		destX = destX >= length ? length - 1 : destX;
//		
//		int destY = (int)(Math.random() * amplitude);
//		
//		destY *= (rise ? 1 : -1);
//		
//		destY += y;
//		
//		destY = destX < destY ? destX : destY;
//		
//		return new Point(destX, destY);
//	}
}