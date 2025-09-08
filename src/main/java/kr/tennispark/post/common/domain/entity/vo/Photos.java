package kr.tennispark.post.common.domain.entity.vo;

import jakarta.persistence.Embeddable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Photos {

    private String photo1;
    private String photo2;
    private String photo3;

    private Photos(List<String> photos) {
        List<String> normalized = new ArrayList<>();
        if (photos != null) {
            for (String p : photos) {
                if (p != null && !p.trim().isEmpty()) {
                    normalized.add(p.trim());
                }
            }
        }
        this.photo1 = normalized.size() > 0 ? normalized.get(0) : null;
        this.photo2 = normalized.size() > 1 ? normalized.get(1) : null;
        this.photo3 = normalized.size() > 2 ? normalized.get(2) : null;
    }

    public static Photos of(List<String> urls) {
        return new Photos(urls);
    }

    public List<String> toList() {
        List<String> list = new ArrayList<>();
        if (photo1 != null) {
            list.add(photo1);
        }
        if (photo2 != null) {
            list.add(photo2);
        }
        if (photo3 != null) {
            list.add(photo3);
        }
        return Collections.unmodifiableList(list);
    }

    public Map<Integer, String> toMap() {
        Map<Integer, String> map = new LinkedHashMap<>();
        if (photo1 != null && !photo1.isBlank()) {
            map.put(1, photo1);
        }
        if (photo2 != null && !photo2.isBlank()) {
            map.put(2, photo2);
        }
        if (photo3 != null && !photo3.isBlank()) {
            map.put(3, photo3);
        }
        return map;
    }

    public String getMainImage() {
        return photo1;
    }
}