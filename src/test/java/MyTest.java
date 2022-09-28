import com.zf.domain.entity.CompanyImg;
import com.zf.mapper.CompanyImgMapper;
import com.zf.utils.JwtUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@SpringBootTest
public class MyTest {



   @Test
    public void password() throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123123123");

        System.out.println("encode = " + encode);
       String subject = JwtUtil.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJiNmQyZTBhMjYyY2M0YTQwOWJjNDQ4YjAxOWM4NThmYiIsInN1YiI6IjIiLCJpc3MiOiJzZyIsImlhdCI6MTY2Mzk0MzY2OCwiZXhwIjoxNjY0MTE2NDY4fQ.Xq5nqpHQw_4A9HncUjtwEiSZeI2OOuXXz-xjEooY2qg").getSubject();
       System.out.println("subject = " + subject);

   }



    @Test
    public void tesr(){
        Date date = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        String format = ft.format(date);
        System.out.println("format = " + format);
}
@Test
    public void test1(){
    boolean b = StringUtils.hasText("");
    System.out.println("b = " + b);
    boolean text = StringUtils.hasText("123123");
    System.out.println("text = " + text);

}
}
