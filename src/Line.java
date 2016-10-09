
import javax.swing.JFrame;

import java.util.Random;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class Line implements GLEventListener{
	
	static GLProfile profile = GLProfile.get(GLProfile.GL2);
    static GLCapabilities capabilities = new GLCapabilities(profile);
    // The canvas 
    static GLCanvas glcanvas = new GLCanvas(capabilities);
    
   public static void main(String[] args) {
	      //getting the capabilities object of GL2 profile
	   	  
	      
	      Line l = new Line();
	      //creating frame
	      glcanvas.addGLEventListener(l);
	      glcanvas.setSize(600, 400);
	      
	      final JFrame frame = new JFrame ("straight Line");
	      //adding canvas to frame
	      frame.getContentPane().add(glcanvas);
	      frame.setSize(frame.getContentPane().getPreferredSize());
	      frame.setVisible(true);
	      
	   }
   public void display(GLAutoDrawable drawable) {
      final GL2 gl = drawable.getGL().getGL2();
//       	  gl.glBegin (GL2.GL_POINTS);//static field
//          gl.glVertex2d(0.5f,0.5f);
//          gl.glVertex2d(0.5f,-0.5f);
//          gl.glVertex2d(-0.5f,0.5f);
//          gl.glVertex2d(-0.5f,-0.5f);
//          gl.glEnd();
//           
     
      double xmax=.3;
	  double xmin=-.4;
	  double ymax=.5;
	  double ymin=-.2;
	  gl.glBegin (GL2.GL_LINES);
	  gl.glColor3f( 1.0f, 1.0f, 1.0f ); 
      gl.glVertex2d(xmin, -.9);
      gl.glVertex2d(xmin, .9);
      gl.glVertex2d(xmax, -.9);
      gl.glVertex2d(xmax, .9);
      gl.glVertex2d(-.9,ymin);
      gl.glVertex2d(.9,ymin);
      gl.glVertex2d( -.9,ymax);
      gl.glVertex2d( .9,ymax);
      gl.glEnd();
      
      int i=1000;
     while (i-->0)
      {
	   double x1=randNumb();
	   double y1=randNumb();
	   double x2=randNumb();
	   double y2=randNumb();
	   //Main algorithm's result
	   String result=sutherlandClipping(xmin,xmax,ymin,ymax,x1,y1,x2,y2);
	   if(result.equals("FA"))
	   {
		   gl.glBegin( GL2.GL_LINES );
		   gl.glColor3f( 0.0f,1.0f,0.0f ); 
		   gl.glVertex2d(x1,y1 );
		   gl.glVertex2d( x2,y2 );
		   gl.glEnd();
	   }
	   else if(result.equals("FR"))
	   {
		   gl.glBegin( GL2.GL_LINES );
		   gl.glColor3f( 1.0f, 0.0f, 0.0f );  
		   gl.glVertex2d(x1,y1 );
		   gl.glVertex2d( x2,y2 );
		   gl.glEnd();
	   }
//	   else
//	   {
//		   gl.glBegin( GL2.GL_LINES );
//		   gl.glColor3f( 0.0f, 0.0f, 1.0f ); 
//		   gl.glVertex2d(x1,y1 );
//		   gl.glVertex2d( x2,y2 );
//		   gl.glEnd();
//	   
//	   }
      }
//     System.exit(0);
   }
   
   public void dispose(GLAutoDrawable arg0) {
      //method body
   }

   
   public void init(GLAutoDrawable drawable) {
      // method body
	   //4. drive the display() in a loop
	    }
   
   public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
      // method body
   }
   
   public String sutherlandClipping(double xmin,double xmax,double ymin,double ymax,double x1,double y1,double x2,double y2)
   {
   	//making ABRL
	   String binary=above(ymax,ymin,y1)+""+below(ymax,ymin,y1)+""+right(xmax,xmin,x1)+""+left(xmax,xmin,x1);
	   int xr=Integer.parseInt(binary,2);
	   
	   binary=above(ymax,ymin,y2)+""+below(ymax,ymin,y2)+""+right(xmax,xmin,x2)+""+left(xmax,xmin,x2);
	   int yr =Integer.parseInt(binary,2);
	   //Bitwise comes real handy in this case
	   int result=yr&xr;
	   
	   if(xr==0 && yr==0)
	   {

//		   System.out.println(x1+" "+y1+" "+x2+" "+y2);
		   return "FA";//Fully Accepted
	   }
	   else if(result==0)
	   {
		   
		   return "PA";//Partially Accepted
	   }
	   else
	   {
		   return "FR";//Fully Rejected
	   }
	   
   }
   public int above(double ymax,double ymin,double y)
   {
   	// taking max caz what if some people do it wrong :p like take ymax in ymin
	   if(y > Math.max(ymax, ymin))
	   {
		   return 1;
	   }
	   return 0;
   }
   public int below(double ymax,double ymin,double y)
   {
	   if(y < Math.min(ymax, ymin))
	   {
		   return 1;
	   }
	   return 0;
   }
   public int right(double xmax,double xmin,double x)
   {
	   if(x > Math.max(xmax, xmin))
	   {
		   return 1;
	   }
	   return 0;
   }
   public int left(double xmax,double xmin,double x)
   {
	   if(x < Math.min(xmax, xmin))
	   {
		   return 1;
	   }
	   return 0;
   }
// SOme random numer generator
public double randNumb(){
	   Random rn = new Random();
	   double max,min, num=0;
	   max=1;
	   min=-1;
	   double range = max - min;
	   num = rn.nextDouble() * range + min;
	   
	   return num;
   }
}//end of classimport javax.media.opengl.GL2;
