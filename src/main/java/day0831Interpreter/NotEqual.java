package day0831Interpreter;

/**
 * 说明:
 *
 * @author zhanglin/016873
 * @version: V1.0.0
 * @update 2020/9/1
 */
public class NotEqual  extends Expression{
    private Expression left,right;
    public NotEqual(Expression left , Expression right){
        this.left = left;
        this.right = right;
    }

    @Override
    public String produceSql() {
        return left.produceSql() + "<>" + right.produceSql();
    }
}
