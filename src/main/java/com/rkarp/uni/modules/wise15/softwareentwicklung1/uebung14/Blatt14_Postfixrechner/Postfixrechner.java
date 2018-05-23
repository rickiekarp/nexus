package com.rkarp.uni.modules.wise15.softwareentwicklung1.uebung14.Blatt14_Postfixrechner;

import java.util.Stack;

/**
 * Diese Klasse bietet eine Operation an,
 * die Ausdruecke in Postfixnotation auswertet.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Postfixrechner
{
    private static Stack<Integer> stack = new Stack<>();

    /**
     * Wertet einen Ausdruck in Postfixnotation aus.
     * Beispielsweise liefert werteAus("1 2 + 3 *") die Zahl 9.
     * 
     * @param ausdruck der auszuwertende Ausdruck in Postfixnotation
     * @return des ausgewertete Ergebnis des Ausdrucks
     */
    public static int werteAus(String ausdruck)
    {
        String[] chars = ausdruck.split(" ");
        //System.out.println("chars: " + Arrays.toString(chars));

        int n1, n2;

        //iterate through the chars array
        for (String aChar : chars) {
            //if char is number: push on stack
            //if char is a math expression: calculate elements on stack
            if (aChar.matches("[0-9].*")) {
                stack.push(Integer.parseInt(aChar));
            } else {
                //remove first element from stack
                n1 = stack.pop();
                n2 = stack.pop();

                //calculate elements
                switch (aChar) {
                    case "+":
                        stack.push(n1 + n2);
                        break;
                    case "-":
                        stack.push(n1 - n2);
                        break;
                    case "*":
                        stack.push(n1 * n2);
                        break;
                    case "/":
                        stack.push(n1 / n2);
                        break;
                    default:
                        System.out.println("Invalid operator order!");
                }
            }
        }
        return stack.pop();
    }
}
