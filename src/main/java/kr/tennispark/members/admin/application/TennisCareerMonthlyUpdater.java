package kr.tennispark.members.admin.application;


import java.util.List;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.user.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TennisCareerMonthlyUpdater {

    private final MemberRepository memberRepository;

    @Transactional
    public void updateTennisCareer() {
        List<Member> members = memberRepository.findAll();

        for (Member member : members) {
            member.increaseTennisCareer();
        }
    }
}
