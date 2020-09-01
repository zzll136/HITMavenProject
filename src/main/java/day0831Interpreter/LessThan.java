package day0831Interpreter;

/**
 * 说明:
 *
 * @author zhanglin/016873
 * @version: V1.0.0
 * @update 2020/9/1
 */
public class LessThan extends Expression{
    private Expression left,right;
    public LessThan(Expression left , Expression right){
        this.left = left;
        this.right = right;
    }

    @Override
    public String produceSql() {
        return left.produceSql() + "<" + right.produceSql();
    }
}
