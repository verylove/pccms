package pc.cms.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pc.cms.com.entity.Role;
import pc.cms.com.mapper.UsersMapper;

//import javax.management.relation.Role;
//import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersServiceImpl {
//    @Autowired
//    private UsersMapper usersMapper;
//    @Override
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
////        UserDetails userDetail = new UserDetails();
//        pc.cms.com.entity.Users users1=new pc.cms.com.entity.Users();
//        users1.setName(userName);
//        pc.cms.com.entity.Users users=usersMapper.selectOne(users1);
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        for (Role role : users.getRoles()) {
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//        return new User(users.getName(), users.getPassword(), authorities);
//    }
}
