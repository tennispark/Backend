package kr.tennispark.point.admin.application.service;

import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.user.infrastructure.repository.MemberRepository;
import kr.tennispark.point.common.application.service.PointService;
import kr.tennispark.point.common.domain.entity.Product;
import kr.tennispark.point.common.domain.entity.enums.PointReason;
import kr.tennispark.point.common.infrastructure.ProductRepository;
import kr.tennispark.point.common.presentation.dto.PurchasePayload;
import kr.tennispark.qr.application.QrService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminProductService {

    private final QrService qrService;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final PointService pointService;

    @Transactional
    public void purchaseProduct(String token) {
        PurchasePayload payload = qrService.parseToken(token, PurchasePayload.class);
        Product product = productRepository.getById(payload.productId());
        Member member = memberRepository.getById(payload.memberId());
        pointService.applyPoint(member, product.getPoint(), PointReason.BUY, product.getName());
    }
}
