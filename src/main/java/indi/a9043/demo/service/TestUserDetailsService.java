package indi.a9043.demo.service;

import indi.a9043.demo.entity.TestUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TestUserDetailsService implements UserDetailsService {
    /* 定义一个用户用来测试
     * 用户名: test1
     * 密码: 123456
     */
    private TestUser testUser;

    @Autowired
    public TestUserDetailsService() {
        testUser = new TestUser();
        testUser.setUserName("test1");
        testUser.setUserPassword(new BCryptPasswordEncoder().encode("123456"));
    }

    /**
     * 根据用户名查询UserDetails
     *
     * @param username 用户名
     * @return UserDetails
     * @throws UsernameNotFoundException 无此用户名
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals(testUser.getUserName())) {
            return testUser;
        }
        throw new UsernameNotFoundException("User " + username + " was not found");
    }
}
