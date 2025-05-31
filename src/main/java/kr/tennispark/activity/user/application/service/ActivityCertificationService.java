package kr.tennispark.activity.user.application.service;

import kr.tennispark.activity.common.domain.ActivityCertification;
import kr.tennispark.activity.user.infrastructure.repository.ActivityCertificationRepository;
import kr.tennispark.members.common.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActivityCertificationService {

    private final ActivityCertificationRepository certificationRepository;

    @Transactional
    public void certifyActivity(Member member, String imageUrl) {
        ActivityCertification cert =
                ActivityCertification.of(member, imageUrl);
        certificationRepository.save(cert);
    }
}
