import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Calculator {

    public String extractFunctionName(String nextString) {

        String functionPattern = "(void|char|float|long|int|double){1}.+[(]{1}.*[)]{1}";

        String name = "";

        if(Pattern.matches(functionPattern, nextString)){
            String[] value = nextString.split(" ");
            StringBuilder s = new StringBuilder();
            String req = value[1];
            for(int i=0;i<req.length();i++){
                if(req.charAt(i) == '(')
                    break;
                s.append(req.charAt(i));
            }
            name = s.toString();
        }

        return name;

    }

    public int countLoops(String[] array){

        Integer count = 0;

        for(String val : array){
            if(Pattern.matches("if[(].*", val)){
                count++;
            }
            else if(Pattern.matches("while[(].*", val)){
                count++;
            }
            else if(Pattern.matches("for[(].*", val)){
                count++;
            }
            else if(Pattern.matches("do", val)){
                count++;
            } else if(Pattern.matches("case.*[:]", val)){
                count++;
            } else if(Pattern.matches("&&", val)) {
                count++;
            }
        }

        return count;

    }

    public int calculateComplexity(BufferedReader br) throws IOException {

        String nextString;
        Integer answer = 0;
        Integer count = 0;
        Boolean flag = false;

        String name = "";

        Stack<Character> functionStack = new Stack<Character>();

        while((nextString = br.readLine()) != null){

            name = extractFunctionName(nextString);

            if(Pattern.matches("[a-zA-Z]*[(].*[);]", nextString.trim())) {
                System.out.println("Function found: "+nextString.trim());
                count++;
            }

            for(int i=0;i<nextString.length();i++) {
                if (nextString.charAt(i) == '{') {
                    functionStack.push(nextString.charAt(i));
                    flag = true;
                } else if (nextString.charAt(i) == '}') {
                    functionStack.pop();
                }
            }

            nextString = nextString.trim();
            String[] arr = nextString.split(" ");

            count += countLoops(arr);

            if(functionStack.empty() && flag){
                count++;
                answer += count;
                count = 0;
                flag = false;
            }

        }

        return answer;
    }
}
