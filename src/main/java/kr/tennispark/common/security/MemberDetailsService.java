package kr.tennispark.common.security;

import static kr.tennispark.common.constant.JwtConstants.ROLE_PREFIX;
import static kr.tennispark.common.constant.JwtConstants.USER_ROLE_VALUE;

import java.util.List;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.common.domain.entity.vo.Phone;
import kr.tennispark.members.user.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) {

        Member member = memberRepository.findByPhone(Phone.of(phoneNumber))
                .orElseThrow(() ->
                        new UsernameNotFoundException("존재하지 않는 회원 전화번호")
                );

        return new MemberPrincipal(
                member,
                List.of(new SimpleGrantedAuthority(ROLE_PREFIX + USER_ROLE_VALUE))
        );
    }
}
