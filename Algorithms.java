import java.util.Stack;

public class Algorithms {
    public static <T extends Comparable<T>> int linearSearch(T[] array, T key) {
        int result = -1;

        for (int i = 0; i < array.length; i++) {
            if (array[i].compareTo(key) == 0)
                return i;
        }

        return result;
    }

    public static <T extends Comparable<T>> int binarySearch(T[] array, T key) {
        int lowerBound = 0;
        int upperBound = array.length - 1;
        int curIn;

        while (true) {
            curIn = (lowerBound + upperBound) / 2;

            if (array[curIn].compareTo(key) == 0)
                return curIn;
            else if (lowerBound > upperBound) {
                return -1;
            }
            else {
                if (array[curIn].compareTo(key) < 0) {
                    lowerBound = curIn + 1;
                }
                else {
                    upperBound = curIn - 1;
                }
            }
        }
    }

    public static <T extends Comparable<T>> T[] bubbleSort(T[] array) {
        T[] result = array.clone();

        for (int out = result.length - 1; out > 1; out--) {
            for (int in = 0; in < out; in++) {
                if (result[in].compareTo(result[in + 1]) > 0) {
                    T temp = result[in];
                    result[in] = result[in + 1];
                    result[in + 1] = temp;
                }
            }
        }

        return result;
    }

    public static <T extends Comparable<T>> T[] selectionSort(T[] array) {
        T[] result = array.clone();

        for (int out = 0; out < result.length - 1; out++) {
            int min = out;
            for (int in = out + 1; in < result.length; in++) {
                if (result[in].compareTo(result[min]) < 0) {
                    min = in;
                }
            }
            T temp = result[out];
            result[out] = result[min];
            result[min] = temp;
        }

        return result;
    }

    public static <T extends Comparable<T>> T[] insertionSort(T[] array) {
        T[] result = array.clone();

        for (int out = 1; out < result.length; out++) {
            T temp = result[out];
            int in = out;
            while(in > 0 && !(result[in - 1].compareTo(temp) < 0)) {
                result[in] = result[in - 1];
                in--;
            }
            result[in] = temp;
        }

        return result;
    }

    public static <T extends Comparable<T>> int recursiveBinarySearch(T[] array, T key, int lower, int upper) {
        int current = (lower + upper) / 2;

        if (array[current].compareTo(key) == 0)
            return current;

        if (array[current].compareTo(key) < 0)
            return recursiveBinarySearch(array, key, current + 1, upper);
        else
            return recursiveBinarySearch(array, key, lower, current - 1);
    }

    public static int triangle(int n) {
        if (n == 1) return n;
        return n + triangle(n - 1);
    }

    public static int factorial(int n) {
        if (n == 0) return n;
        return n * factorial(n - 1);
    }

    public static void permutations(StringBuffer out, char[] chars, int position) {
        if (position == chars.length - 1) {
            out.append(String.valueOf(chars) + "\n");
        }

        for (int i = position; i < chars.length; i++) {
            char temp = chars[i];
            chars[i] = chars[position];
            chars[position] = temp;

            permutations(out, chars, position + 1);

            temp = chars[i];
            chars[i] = chars[position];
            chars[position] = temp;
        }
    }

    public static void checkBrackets(String str) {
        Stack<Character> stack = new Stack<>(str.length());

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            switch(c) {
                case '{':
                case '[':
                case '(':
                    stack.push(c);
                    break;
                case '}':
                case ']':
                case ')':
                    if (!stack.isEmpty()) {
                        char cx = stack.pop();

                        if (c == '}' && cx != '{' ||
                                c == ']' && cx != '[' ||
                                c == ')' && cx != '(')
                            System.out.println("Error: " + c + " at " + i);
                    }
                    else
                        System.out.println("Error: " + c + " at " + i);
                    break;
                default:
                    break;
            }
        }

        if (!stack.isEmpty()) {
            System.out.println("Error: missing right delimiter");
        }
    }

    public static String postfix(String str) {
        Stack<Character> stack = new Stack<>(str.length());
        String result = "";

        for (int i = 0; i < str.length(); i++) {
            char inElem = str.charAt(i);
            int prec1 = 0;

            switch(inElem) {
                case '+':
                case '-':
                    prec1 = 1;
                    break;
                case '*':
                case '/':
                    prec1 = 2;
                    break;
                case '(':
                    stack.push(inElem);
                    break;
                case ')':
                    while (!stack.isEmpty()) {
                        char chx = stack.pop();

                        if (chx == '(')
                            break;
                        else
                            result += chx;
                    }
                    break;
                default:
                    result += inElem;
            }

            if (prec1 > 0) {
                while (!stack.isEmpty()) {
                    char topElem = stack.pop();

                    if (topElem == '(') {
                        stack.push(topElem);
                        break;
                    }
                    else {
                        int prec2 = topElem == '+' || topElem == '-' ? 1 : 2;

                        if (prec2 < prec1) {
                            stack.push(topElem);
                            break;
                        }
                        else {
                            result += topElem;
                        }
                    }
                }
                stack.push(inElem);
            }
        }

        while (!stack.isEmpty()) {
            result += stack.pop();
        }

        return result;
    }

    public static double parsePostfix(String str) {
        Stack<Double> stack = new Stack<>(str.length());
        double result, num1, num2;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (c >= '0' && c <= '9') {
                stack.push((double) c - '0');
            }
            else {
                num2 = stack.pop();
                num1 = stack.pop();

                switch (c) {
                    case '+': result = num1 + num2; break;
                    case '-': result = num1 - num2; break;
                    case '*': result = num1 * num2; break;
                    case '/': result = num1 / num2; break;
                    default: result = 0.0;
                }
                stack.push(result);
            }
        }

        return stack.pop();
    }
}
