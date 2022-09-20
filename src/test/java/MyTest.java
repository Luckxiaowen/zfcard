import com.zf.domain.entity.CompanyImg;
import com.zf.mapper.CompanyImgMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;


@SpringBootTest
public class MyTest {



   @Test
    public void password(){
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123123123");
        System.out.println("encode = " + encode);

    }




}
