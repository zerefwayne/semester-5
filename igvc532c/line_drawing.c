#include <GL/glut.h>

void init2D(float r, float g, float b)
{
// glClearColor sets the colour to clear the
//buffer to.
glClearColor(r, g, b, 0.0);
// used to set up the view volume,
//GL_MODELVIEW can be used to set up viewing
//transformation
glMatrixMode(GL_PROJECTION);
// gluOrtho2D specifies the coordinates to
//be used with the viewport which defaults to the
//window size.
gluOrtho2D(0.0, 500.0, 0.0, 500.0);
}

void display(void)
{
// clear the buffers currently enabled for color writing.
glClear(GL_COLOR_BUFFER_BIT);
glColor3f(1.0, 0.0, 0.0);
glBegin(GL_LINES);
// glVertext2i takes integer as arguments,
//we also have other versions for float and double
//glVertext2f, glVertext2d
glVertex2i(10, 10);
glVertex2i(100, 100);
glEnd();
glFlush();
}

void main(int argc, char *argv[])
{
// glutInit will initialize the GLUT library and
//negotiate a session with the window system.
glutInit(&argc,argv);
// Select a display mode with single buffer
//because its a simple application and Red, green,
//blue framebuffer
glutInitDisplayMode(GLUT_SINGLE |
GLUT_RGB);
glutInitWindowSize(500, 500);
glutInitWindowPosition(100, 100);
glutCreateWindow("line drawing in opengl");
init2D(0.0, 0.0, 0.0);
// calls the function display every/time the
//display needs to be updated
glutDisplayFunc(display);
glutMainLoop();
}