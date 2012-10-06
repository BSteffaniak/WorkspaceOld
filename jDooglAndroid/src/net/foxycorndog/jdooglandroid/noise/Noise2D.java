package net.foxycorndog.jdooglandroid.noise;

import java.util.Random;

public class Noise2D
{
	private static final Random random = new Random();
	
	private static       long   seed   = getRandomSeed();
	
	static
	{
		random.setSeed(seed);
	}

	private int frequency;
	
	private double[] gx;
	private double[] gy;

	private Noise2D(int size, int frequency)
	{
		this.frequency = 1000 - frequency;

		gx = new double[size];
		gy = new double[size];

		for (int i = 0; i < size; i++)
		{
				gx[i] = random.nextDouble() * 2. - 1.;
				gy[i] = random.nextDouble() * 2. - 1.;

				// normalize these.
				double length = Math.sqrt(gx[i] * gx[i] + gy[i] * gy[i]);
				gx[i] /= length;
				gy[i] /= length;
		}
	}

	private static double[] generateNoise(int size, int frequency)
	{
		Noise2D oct1 = null, oct2 = null, oct3 = null;
		
		//apply multiple layers of noise.
		oct1 = new Noise2D(size, frequency);
		oct2 = new Noise2D(size, frequency);
		oct3 = new Noise2D(size, frequency);

		double[] array = new double[size];

		//scale each octave of noise differently.
		oct1.applyNoise(3.0, 3.0, array, 1.0);
		oct2.applyNoise(10.0, 10.0, array, 0.3);
		oct3.applyNoise(40.0, 40.0, array, 0.03);

		// normalize the noise, set oceans, etc.

//		for (int i = 0; i < size; i++) {
//			for (int j = 0; j < size; j++) {
//				System.out.print((int)(array[i][j] * 50));
//				System.out.print("\t");
//			}
//			System.out.println();
//		}
		
		return array;
	}
	
	public static int[] getNoise(int size, int frequency)
	{
		double array[] = generateNoise(size, frequency);
		
		int returner[] = new int[array.length];
		
		for (int i = 0; i < returner.length; i ++)
		{
			returner[i] = (int)(array[i] * 50);
		}
		
		return returner;
	}
	
//	public static void main(String args[])
//	{
//
//		final int size = 200;
//
//		//apply multiple layers of noise.
//		Noise2D oct1 = new Noise2D(size, 800);
//		Noise2D oct2 = new Noise2D(size, 800);
//		Noise2D oct3 = new Noise2D(size, 800);
//
//		double array[] = new double[size];
//
//		//scale each octave of noise differently.
//		oct1.applyNoise(3.0, 3.0, size, array, 1.0);
//		oct2.applyNoise(10.0, 10.0, size, array, 0.3);
//		oct3.applyNoise(40.0, 40.0, size, array, 0.03);
//
//		// normalize the noise, set oceans, etc.
//
//		for (int i = 0; i < size; i++)
//		{
//			System.out.print((int)(array[i] * 50));
//			System.out.print("\t");
//		}
//		
//		System.out.println();
//	}

	private double noise(double x)
	{
		// find the boundaries
		int x2 = (int)x;

		// Make these be the values within the boundaries we found above.
		x -= x2;

		// Calculate the influences of each corner dotted with the gradient
		// function to yield the noise value.
		// s = g(x0, y0) * ((x, y) - (x0, y0)). expand the dot product  
		double s = gradX(x2) * x;
		double t = gradX(x2 + 1) * (x - 1.0);
		double u = gradX(x2) * x - gradY(x2);
		double v = gradX(x2 + 1) * (x - 1.0) - gradY(x2 + 1);

		// smoothed values, via the ease curve. (3p^2 - 2p^3)
		double fadeX = fade(x);

		// Lerp them together
		double x1lerp = lerp(fadeX, s, t);
		double x2lerp = lerp(fadeX, u, v);
		return lerp(0, x1lerp, x2lerp);

		// double x1lerp = lerp(fadeX, S * fadeX, T * (1.0 - fadeX));
		// double x2lerp = lerp(fadeX, U * fadeX, V * (1.0 - fadeX));
		// return lerp(fadeY, x1lerp * fadeY, x2lerp * (1.0 - fadeY));
	}

	private double gradX(int x)
	{
		return gx[x];
	}

	private double gradY(int x)
	{
		return gy[x];
	}

	private double fade(double t)
	{
		return t * t * t * (t * (t * 6 - 15) + 10);
	}

	/**
	 * 1 Dimensional linear interpolation. Distance as a fraction between a and
	 * b.
	 */
	private double lerp(double distance, double a, double b)
	{
		return a + distance * (b - a);
	}

	private void applyNoise(final double xMax, final double yMax, double[] array, double scale)
	{
		for (int i = 0; i < array.length; i++)
		{
			double x = (xMax / frequency) * i;
			
			array[i] += (noise(x) * scale);
		}
	}

	private static final long getRandomSeed()
	{
//		return random.nextLong();
		return System.nanoTime();
	}
}