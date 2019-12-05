// C++ program for implementing
// Mid-Point Circle Drawing Algorithm
#include <GL/glut.h>
#include <stdio.h>

void myInit()
{
    glClear(GL_COLOR_BUFFER_BIT);
    glMatrixMode(GL_PROJECTION);
    gluOrtho2D(0, 1000, 0, 1000);
}

void draw_pixel(int x, int y)
{
    glBegin(GL_POINTS);
    glVertex2i(x, y);
    glEnd();
}

// Implementing Mid-Point Circle Drawing Algorithm
void midPointCircleDraw(int x_centre, int y_centre, int r)
{
    glClear(GL_COLOR_BUFFER_BIT);
    int x = r, y = 0;

    // When radius is zero only a single
    // point will be printed
    if (r > 0)
    {
        draw_pixel(x + x_centre, -y + y_centre);
        draw_pixel(y + x_centre, x + y_centre);
        draw_pixel(-y + x_centre, x + y_centre);
    }

    // Initialising the value of P
    int P = 1 - r;
    while (x > y)
    {
        y++;

        // Mid-point is inside or on the perimeter
        if (P <= 0)
            P = P + 2 * y + 1;
        // Mid-point is outside the perimeter
        else
        {
            x--;
            P = P + 2 * y - 2 * x + 1;
        }

        // All the perimeter points have already been printed
        if (x < y)
            break;

        // Printing the generated point and its reflection
        // in the other octants after translation
        draw_pixel(x + x_centre, y + y_centre);
        draw_pixel(-x + x_centre, y + y_centre);
        draw_pixel(x + x_centre, -y + y_centre);
        draw_pixel(-x + x_centre, -y + y_centre);

        // If the generated point is on the line x = y then
        // the perimeter points have already been printed
        if (x != y)
        {
            draw_pixel(y + x_centre, x + y_centre);
            draw_pixel(-y + x_centre, x + y_centre);
            draw_pixel(y + x_centre, -x + y_centre);
            draw_pixel(-y + x_centre, -x + y_centre);
        }
    }
}

void myDisplay()
{
    glClearColor(0.0, 0.0, 0.0, 0.0);
    midPointCircleDraw(400, 400, 200);
    glEnd();
    glFlush();
}

int main(int argc, char **argv)
{
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
    glutInitWindowSize(500, 500);
    glutInitWindowPosition(0, 0);
    glutCreateWindow("Bresenham's Line Drawing");
    myInit();
    glutDisplayFunc(myDisplay);
    glutMainLoop();
}
