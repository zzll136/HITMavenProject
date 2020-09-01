package day0831Interpreter;

/**
 * 说明:
 *表示字符串变量
 * @author zhanglin/016873
 * @version: V1.0.0
 * @update 2020/8/31
 */
public class Variable extends Expression {

    private String name;

    public Variable(String name){
        this.name = name;
    }
    @Override
    public String produceSql() {
        return name;
    }

}