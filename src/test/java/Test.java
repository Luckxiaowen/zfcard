import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootTest
public class Test {

    @org.junit.Test
    public void password(){
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123123123");
        System.out.println("encode = " + encode);

    }
}
