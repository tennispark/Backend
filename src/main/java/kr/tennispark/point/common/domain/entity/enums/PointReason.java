package kr.tennispark.point.common.domain.entity.enums;

import lombok.Getter;

@Getter
public enum PointReason {
    ATTENDANCE("출석체크", true),
    EVENT("이벤트 참여", true),
    BUY("상품 구매", false),
    RECOMMEND_FRIEND("친구 추천", true),
    WIN_MATCH("경기 승리", true),
    ;

    private final String defaultTitle;
    private final boolean earned;      // true → 지급, false → 차감

    PointReason(String defaultTitle, boolean earned) {
        this.defaultTitle = defaultTitle;
        this.earned = earned;
    }

    public String makeType() {
        return earned ? "EARNED" : "USED";
    }
}
