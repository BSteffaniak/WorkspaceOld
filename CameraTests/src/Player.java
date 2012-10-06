import org.lwjgl.opengl.GL11;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

/**
 * We use keyboard and mouse input values to build a view matrix which gets
 * loaded instead of the existing modelview matrix. We also attach a simple
 * white square which moves with the camera as if it were a gun.
 * 
 * @author Stephen Jones
 */
public class Player {
	private static final float _90 = (float) Math.toRadians(90);
	private float heading = 0.0f;
	private float pitch = 0.0f;
	// angle values are got via input and used to create matrix
	private float cosa, cosb, cosz, sina, sinb, sinz;
	// camera roll is not implemented so set default values
	private float cosc = 1.0f;
	private float sinc = 0.0f;
	// forward/back and strafe
	private float x, y, z;
	// matrix is initialized with the identity matrix mat
	private float[] mat = { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 };
	private FloatBuffer matrix;

	public Player() {
		matrix = BufferUtils.createFloatBuffer(mat.length);
		matrix.put(mat);
		x = z = 0.0f;
		y = -3.0f;
	}

	/*
	 * changes in mouse x values are added to heading. cosb and sinb values
	 * affect rotations about y. cosZ and sinz affect motion forward and back.
	 */
	public void setHeading(float amount) {
		heading += amount;
		cosb = (float) Math.cos(heading);
		sinb = (float) Math.sin(heading);
		cosz = (float) Math.cos(heading + _90);
		sinz = (float) Math.sin(heading + _90);
	}

	/*
	 * changes in mouse y values are added to pitch. cosa and sina affect
	 * rotations arounf x.
	 */
	public void setPitch(float amount) {
		pitch -= amount;
		cosa = (float) Math.cos(pitch);
		sina = (float) Math.sin(pitch);
	}

	public void setFord(float amount) {
		x += cosz * amount;
		z += sinz * amount;
	}

	public void setBack(float amount) {
		x -= cosz * amount;
		z -= sinz * amount;
	}

	public void setlStrafe(float amount) {
		x += cosb * amount;
		z += sinb * amount;
	}

	public void setrStrafe(float amount) {
		x -= cosb * amount;
		z -= sinb * amount;
	}

	/*
	 * after mouse and keyboard have been polled and set their respective
	 * variables, set is called to construct the rotation matrix. This matrix is
	 * multiplied with the x, y, z values that determine positions (up/down,
	 * forward/back, strafe left or right). The values from the multiplication
	 * are placed in the relevant positions in the overall view matrix.
	 */
	public void set() {
		matrix.put(0, cosc * cosb - sinc * sina * sinb);
		matrix.put(1, sinc * cosb + cosc * sina * sinb);
		matrix.put(2, -cosa * sinb);
		matrix.put(4, -sinc * cosa);
		matrix.put(5, cosc * cosa);
		matrix.put(6, sina);
		matrix.put(8, cosc * sinb + sinc * sina * cosb);
		matrix.put(9, sinc * sinb - cosc * sina * cosb);
		matrix.put(10, cosa * cosb);
		matrix.put(12, matrix.get(0) * x + matrix.get(4) * y + matrix.get(8)
				* z);
		matrix.put(13, matrix.get(1) * x + matrix.get(5) * y + matrix.get(9)
				* z);
		matrix.put(14, matrix.get(2) * x + matrix.get(6) * y + matrix.get(10)
				* z);
	}

	/*
	 * before any other objects are drawn the player/camera position is defined
	 * here. Note loadIdentity need not be called because the matrix we build is
	 * loaded directly into the openGL modelview matrix.
	 */
	public void draw() {
		// draw a square to represent a gun
		GL11.glColor3f(7.0f, 5.0f, 3.0f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3f(1.0f, 1.0f, 0.0f);
		GL11.glVertex3f(2.0f, 1.0f, 0.0f);
		GL11.glVertex3f(2.0f, 1.0f, -5.0f);
		GL11.glVertex3f(1.0f, 1.0f, -5.0f);
		GL11.glEnd();
		// rewind the float buffer then load it as the modelview matrix
		matrix.rewind();
		GL11.glLoadMatrix(matrix);
	}
}