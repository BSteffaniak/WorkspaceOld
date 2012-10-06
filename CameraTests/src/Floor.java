import org.lwjgl.opengl.GL11;
/**
* The floor is acts as a reference. It is basically the bottom of
* a box.
* @author Stephen Jones
*/
public class Floor {
public void draw(){
GL11.glBegin(GL11.GL_QUADS);
GL11.glColor3f(0.9f, 0.2f, 0.1f);
GL11.glVertex3f(-100.0f, 1.0f, 100.0f);
GL11.glColor3f(0.4f, 0.8f, 0.4f);
GL11.glVertex3f(-100.0f, 1.0f, -100.0f);
GL11.glColor3f(0.2f, 0.9f, 0.1f);
GL11.glVertex3f(100.0f, 1.0f, -100.0f);
GL11.glColor3f(0.5f, 0.2f, 0.9f);
GL11.glVertex3f(100.0f, 1.0f, 100.0f);
GL11.glEnd();
}
}