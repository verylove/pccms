import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordTest {
    @Test
    public void test() {
        String pwd = "{bcrypt}123456";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 加密
        String encodedPassword = passwordEncoder.encode(pwd);
        System.out.println(encodedPassword);
//        log.info("【加密后的密码为：】" + encodedPassword);
    }
}
//$2a$10$Y0I27iqQNaC3y/rjWlb.7OY9oNr60wNjUpKkTSkzHeIo1c7EDTVHK
