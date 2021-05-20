import com.zqm.config.MyConfig;
import com.zqm.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyTest {
    @Test
    public void test(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig.class);
        User user = (User) applicationContext.getBean("user");
        System.out.println(user.getName());
    }
}
