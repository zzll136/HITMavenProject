package day0831Interpreter;

import java.util.Stack;

/**
 * 说明:
 *
 * @author zhanglin/016873
 * @version: V1.0.0
 * @update 2020/8/31
 */
public class ProduceMixedSql {
    public static String Prefix = "select * from tableName where ";

    public static String getMixedSql(String str) {
        String finnalStr = addSpace(str);
        Stack<String> stack1 = new Stack<>();
        Stack<String> stack2 = new Stack<>();
        String[] strArray = finnalStr.split(" ");
        for (int i = 0; i < strArray.length; i++) {
            if (!IsOperator(strArray[i]) && !IsParen(strArray[i])) {
                stack2.push(strArray[i]);
            } else if (strArray[i].equals("(")) {
                stack1.push(strArray[i]);
            } else if (strArray[i].equals(")")) {
                //如果是右括号')' 则一次弹出s1栈顶的运算符，并压入s2 直到遇到左括号位置,然后将这一对括号丢弃
                while (!stack1.peek().equals("(")) {
                    stack2.add(stack1.pop());
                }
                stack1.pop(); //将 ( 弹出s1栈 消除小括号
            } else {
                //s1不为空且当栈顶不为左括号，且当运算符小于等于s1栈顶运算符的优先级的时候，将s1栈顶的运算符弹出，加入到s2中
                while (stack1.size() != 0 && !stack1.peek().equals("(") && getValue(strArray[i]) <= getValue(stack1.peek())) {
                    stack2.add(stack1.pop());
                }
                stack1.push(strArray[i]);
            }
        }
        //将s1中剩余的运算符依次压入s2
        while (stack1.size() != 0) {
            stack2.add(stack1.pop());
        }
        //存储中间步骤的运行结果
        Stack<Expression> stack4 = new Stack<>();
        for (int i = 0; i < stack2.size(); i++) {
            if (!IsOperator(stack2.get(i)) && !IsParen(stack2.get(i))) {
                Expression res = new Variable(stack2.get(i));
                stack4.push(res);
            } else if (!stack2.get(i).equals("!")) {
                Expression temp1 = stack4.pop();
                Expression temp2 = stack4.pop();
                Expression res = getExpression(temp2, temp1, stack2.get(i));
                stack4.push(res);
            } else {
                Expression temp = stack4.pop();
                Expression res = getExpression(temp, temp, stack2.get(i));
                stack4.push(res);
            }
        }
        return Prefix + stack4.pop().produceSql();
    }

    //判断是否是运算符
    public static boolean IsOperator(String s) {
        if (s.equals("&&") || s.equals("||") || s.equals("!") || s.equals("==") || s.equals("!=") || s.equals("<"))
            return true;
        else return false;
    }

    //判断是否是括号
    public static boolean IsParen(String s) {
        if (s.equals("(") || s.equals(")"))
            return true;
        else return false;
    }

    //创建对象
    public static Expression getExpression(Expression a, Expression b, String s) {
        switch (s) {
            case ("||"):
                return new Or(a, b);
            case ("&&"):
                return new And(a, b);
            case ("!"):
                return new Not(a);
            case ("=="):
                return new Equal(a, b);
            case ("!="):
                return new NotEqual(a, b);
            case ("<"):
                return new LessThan(a, b);
        }
        return null;
    }

    //对字符串进行处理，添加空格
    public static String addSpace(String str) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            switch (str.charAt(i)) {
                case ('('): {
                    s.append('(');
                    s.append(' ');
                }
                break;
                case (')'): {
                    s.append(' ');
                    s.append(')');
                }
                break;
                case ('<'): {
                    s.append(' ');
                    s.append('<');
                    s.append(' ');
                }
                break;
                case ('='): {
                    s.append(' ');
                    s.append('=');
                    s.append('=');
                    s.append(' ');
                    i++;
                }
                break;
                case ('!'): {
                    if (str.charAt(i + 1) == '=') {
                        s.append(' ');
                        s.append('!');
                        s.append('=');
                        s.append(' ');
                        i++;
                    } else {
                        s.append(str.charAt(i));
                        s.append(" ");
                    }
                }
                break;
                default:
                    s.append(str.charAt(i));
                    break;
            }
        }
        String re = s.toString();
        return re;
    }

    //获取运算符的优先级
    public static int getValue(String operation) {
        int OR = 1;
        int AND = 2;
        int Equal = 3;
        int NotEqual = 3;
        int LessThan = 4;
        int NOT = 5;
        //写一个方法 返回对应的优先级
        int result = 0;
        switch (operation) {
            case "&&":
                result = AND;
                break;
            case "||":
                result = OR;
                break;
            case "!":
                result = NOT;
                break;
            case "==":
                result = Equal;
                break;
            case "!=":
                result = NotEqual;
                break;
            case "<":
                result = LessThan;
                break;
            default:
                System.out.println("不能存在该运算符");
                break;
        }
        return result;
    }
}
