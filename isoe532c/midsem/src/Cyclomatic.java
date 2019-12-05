import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class Cyclomatic {

    public static void main(String[] args) throws IOException {

        File file = new File("/home/zerefwayne/semester-5/isoe532c/midsem/src/test.c");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        Calculator c = new Calculator();

        int complexity = c.calculateComplexity(bufferedReader);

        System.out.println(complexity);

    }

}

