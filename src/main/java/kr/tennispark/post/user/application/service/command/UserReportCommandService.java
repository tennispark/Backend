package kr.tennispark.post.user.application.service.command;

import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.post.common.domain.entity.Comment;
import kr.tennispark.post.common.domain.entity.CommentReport;
import kr.tennispark.post.common.domain.entity.Post;
import kr.tennispark.post.common.domain.entity.PostReport;
import kr.tennispark.post.common.domain.exception.NoSuchCommentException;
import kr.tennispark.post.user.infrastructure.repository.CommentReportRepository;
import kr.tennispark.post.user.infrastructure.repository.PostReportRepository;
import kr.tennispark.post.user.infrastructure.repository.UserCommentRepository;
import kr.tennispark.post.user.infrastructure.repository.UserPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserReportCommandService {

    private final UserPostRepository postRepository;
    private final UserCommentRepository commentRepository;
    private final PostReportRepository postReportRepository;
    private final CommentReportRepository commentReportRepository;

    public void reportPost(Member reporter, Long postId, String reason) {
        Post post = postRepository.getById(postId);
        PostReport report = PostReport.create(post, reporter, reason);
        postReportRepository.save(report);
    }

    public void reportComment(Member reporter, Long postId, Long commentId, String reason) {
        Comment comment = commentRepository.getById(commentId);
        if (!comment.getPost().getId().equals(postId)) {
            throw new NoSuchCommentException("해당 게시물에 대한 댓글이 아닙니다.");
        }
        CommentReport report = CommentReport.create(comment, reporter, reason);
        commentReportRepository.save(report);
    }
}

