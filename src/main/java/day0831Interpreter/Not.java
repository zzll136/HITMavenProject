package day0831Interpreter;

/**
 * 说明:
 *
 * @author zhanglin/016873
 * @version: V1.0.0
 * @update 2020/8/31
 */
public class Not extends Expression {

    private Expression exp;
    public Not(Expression exp){
        this.exp = exp;
    }

    @Override
    public String produceSql() {
        return "NOT (" + exp.produceSql() + ")";
    }

}
