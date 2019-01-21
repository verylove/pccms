package pc.cms.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pc.cms.com.entity.Permission;
import pc.cms.com.entity.Role;
import pc.cms.com.entity.Users;
import pc.cms.com.mapper.UsersMapper;

import java.util.ArrayList;
import java.util.List;
@Service
public class CustomUserDetailsService implements UserDetailsService {
//    @Autowired
//    private UsersMapper usersMapper;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Users users=new Users();
        Permission permission = new Permission();
        permission.setPerCode("user:edit");
        List<Permission> plist = new ArrayList<>();
        plist.add(permission);

        Role role = new Role();
        role.setRoleCode("admin");
        role.setPermissions(plist);

        List<Role> roleList = new ArrayList<>();
        roleList.add(role);
        users.setRoles(roleList);
        users.setUserName("admin");
        users.setPassWord(new BCryptPasswordEncoder().encode("123456"));
        users.setPermissions(plist);
        return users;
    }
}
