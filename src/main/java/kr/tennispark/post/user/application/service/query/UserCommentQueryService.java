package kr.tennispark.post.user.application.service.query;

import java.util.List;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.post.common.domain.entity.Comment;
import kr.tennispark.post.user.infrastructure.repository.UserCommentRepository;
import kr.tennispark.post.user.presentation.dto.response.CommentItemDTO;
import kr.tennispark.post.user.presentation.dto.response.GetCommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserCommentQueryService {

    private final UserCommentRepository commentRepository;

    public GetCommentResponse getCommentsForPost(Long postId, Member member) {
        Long loginMemberId = member.getId();
        List<Comment> comments = commentRepository.findAllByPostIdOrderByCreatedAtAsc(postId);

        List<CommentItemDTO> items = comments.stream()
                .map(c -> CommentItemDTO.of(c, loginMemberId))
                .toList();

        return GetCommentResponse.of(items);
    }
}

