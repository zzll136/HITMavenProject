package day0831Interpreter;
import org.junit.Assert;
import org.junit.Test;
/**
 * 说明:
 *测试用例
 * @author zhanglin/016873
 * @version: V1.0.0
 * @update 2020/8/31
 */
public class ProduceMixedSqlTest {

    //只包含And的测试用例
    @Test
    public void ProduceMixedSqlTest_And() {
        String input="a && b";
        String expected="select * from tableName where (a) AND (b)";
        Assert.assertEquals(expected,ProduceMixedSql.getMixedSql(input));
    }
    //只包含Or的测试用例
    @Test
    public void ProduceMixedSqlTest_Or() {
        String input="a || b";
        String expected="select * from tableName where (a) OR (b)";
        Assert.assertEquals(expected,ProduceMixedSql.getMixedSql(input));
    }

    //只包含Not的测试用例
    @Test
    public void ProduceMixedSqlTest_Not() {
        String input="!a";
        String expected="select * from tableName where NOT (a)";
        Assert.assertEquals(expected,ProduceMixedSql.getMixedSql(input));
    }
    //包含And,<,!=的测试用例
    @Test
    public void ProduceMixedSqlTest_And_LessThan_NE() {
        String expected="select * from tableName where (age<30) AND (sex<>'Male')";
        String input="(age<30) && (sex!='Male')";
        Assert.assertEquals(expected,ProduceMixedSql.getMixedSql(input));
    }
    //包含Or,<,=的测试用例
    @Test
    public void ProduceMixedSqlTest_Or_LessThan_Equal() {
        String expected="select * from tableName where (age<30) OR (sex='Male')";
        String input="(age<30) || (sex=='Male')";
        Assert.assertEquals(expected,ProduceMixedSql.getMixedSql(input));
    }
    //包含Not,<的测试用例
    @Test
    public void ProduceMixedSqlTest_Not_LessThan() {
        String expected="select * from tableName where NOT (age<30)";
        String input="!(age<30)";
        Assert.assertEquals(expected,ProduceMixedSql.getMixedSql(input));
    }

    //包含And,Or,,!=,==,<的测试用例
    @Test
    public void ProduceMixedSqlTest_And_Or_LessThan_Equal_NE() {
        String expected="select * from tableName where (companyName='HTSC') OR ((age<30) AND (sex<>'Male'))";
        String input="(companyName=='HTSC') || ((age<30) && (sex!='Male'))";
        Assert.assertEquals(expected,ProduceMixedSql.getMixedSql(input));
    }

    //包含Not,And,Or,,!=,==,<的测试用例,Not在最外层
    @Test
    public void ProduceMixedSqlTest_Not_And_Or_Not_LessThan_Equal_NE() {
        String expected="select * from tableName where NOT ((companyName='HTSC') OR ((age<30) AND (sex<>'Male')))";
        String input="!((companyName=='HTSC') || ((age<30) && (sex!='Male')))";
        Assert.assertEquals(expected,ProduceMixedSql.getMixedSql(input));
    }

    //包含And,Or,Not,!=,==,<的测试用例
    @Test
    public void ProduceMixedSqlTest_And_Or_Not_LessThan_Equal_NE() {
        String expected="select * from tableName where (NOT (companyName='HTSC')) OR ((age<30) AND (sex<>'Male'))";
        String input="!(companyName=='HTSC') || ((age<30) && (sex!='Male'))";
        Assert.assertEquals(expected,ProduceMixedSql.getMixedSql(input));
    }

    //多层嵌套的测试用例
    @Test
    public void ProduceMixedSqlTest_And2_Or2_Not2_LessThan2_Equal2_NE1() {
        String expected="select * from tableName where NOT (((companyName='HTSC') OR ((age<30) AND (sex<>'Male'))) OR ((country<>'China') AND (NOT (year<30))))";
        String input="!(((companyName=='HTSC') || ((age<30) && (sex!='Male'))) || ((country!='China') && !(year<30)))";
        Assert.assertEquals(expected,ProduceMixedSql.getMixedSql(input));
    }
}
