package org.itliu.algorithm.stack;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 验证括号字符串是否合法
 */
public class ParenthesisStringValidate {
    public static boolean validate(String str) {
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> map = new HashMap<>();
        map.put(')','(');
        map.put('}','{');
        map.put(']','[');
        int i = 0;
        while (i < str.length()) {
            Character character = str.charAt(i++);
            /**
             * 是左括号
             */
            if(!map.containsKey(character)) {
                stack.push(character);
            } else if(stack.empty() || !map.get(character).equals(stack.pop())){
                return false;
            }
        }
        return stack.empty();
    }

    public static void main(String[] args) {
        String str = "((()))";
        System.out.println(validate(str));
        String str1 = "((([)))";
        System.out.println(validate(str1));
        String str2 = "((([{}])))";
        System.out.println(validate(str2));
        String str3 = "((([{}]))";
        System.out.println(validate(str3));
    }
}
