package personal_web.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.Generated;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

public class user implements UserDetails {
    //@Generated()
    private String username;
    private String password;


    public void setUsername(String username){
        this.username=username;
    }
    public void setPassword(String password){
        this.password=password;
    }

    private ArrayList<accounts> extractAccounts(){
        ArrayList<accounts> account = new ArrayList<>();
        //to be continue
        return account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        ArrayList<GrantedAuthority> auths= new ArrayList<>();
        ArrayList<accounts> account = this.extractAccounts();
        for(accounts temp:account){
            auths.add(new SimpleGrantedAuthority(temp.getUsername()));
        }
        return auths;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
