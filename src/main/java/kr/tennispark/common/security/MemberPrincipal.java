package kr.tennispark.common.security;

import java.util.Collection;
import java.util.List;
import kr.tennispark.members.common.domain.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class MemberPrincipal implements UserDetails {

    private final Member member;
    private final List<? extends GrantedAuthority> authorities;

    public MemberPrincipal(Member member,
                           List<? extends GrantedAuthority> authorities) {
        this.member = member;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return member.getPhone().getNumber();
    }

    @Override
    public String getPassword() {
        return "";
    }
}
