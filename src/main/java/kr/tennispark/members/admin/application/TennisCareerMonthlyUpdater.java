package kr.tennispark.members.admin.application;


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
        memberRepository.bulkIncreaseTennisCareer();
    }
}
