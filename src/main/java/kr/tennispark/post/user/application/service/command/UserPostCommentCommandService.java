package kr.tennispark.post.user.application.service.command;

import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.post.common.domain.entity.Comment;
import kr.tennispark.post.common.domain.entity.Post;
import kr.tennispark.post.common.domain.exception.NoSuchCommentException;
import kr.tennispark.post.user.application.exception.NotAuthorizedCommentException;
import kr.tennispark.post.user.application.service.resolver.CommentResolver;
import kr.tennispark.post.user.infrastructure.repository.UserCommentRepository;
import kr.tennispark.post.user.infrastructure.repository.UserPostRepository;
import kr.tennispark.post.user.presentation.dto.request.RegisterCommentMultiPart;
import kr.tennispark.post.user.presentation.dto.request.UpdateCommentMultiPart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserPostCommentCommandService {

    private final UserPostRepository postRepository;
    private final UserCommentRepository commentRepository;
    private final CommentResolver resolver;

    public void createComment(Member member, Long postId, RegisterCommentMultiPart request) {
        Post post = postRepository.getById(postId);
        String photoUrl = resolver.toNewPhotoUrl(request.photo());
        Comment comment = Comment.create(post, member, request.data().content(), photoUrl);
        commentRepository.save(comment);
    }

    public void updateComment(Member member, Long postId, Long commentId, UpdateCommentMultiPart request) {
        Comment comment = commentRepository.getById(commentId);
        validateCommentBelongsToPost(comment, postId);
        ensureAuthor(comment, member);

        String newUrl = resolver.resolvePhotoForUpdate(
                comment.getPhotoUrl(),
                request.photo(),
                request.data().deletePhoto()
        );
        comment.update(request.data().content(), newUrl);
    }

    public void deleteComment(Member member, Long postId, Long commentId) {
        Comment comment = commentRepository.getById(commentId);
        validateCommentBelongsToPost(comment, postId);
        ensureAuthor(comment, member);
        resolver.deleteIfExists(comment.getPhotoUrl());
        comment.remove();
    }

    private void ensureAuthor(Comment comment, Member loginMember) {
        if (comment.getMember() == null || loginMember == null ||
                !comment.getMember().getId().equals(loginMember.getId())) {
            throw new NotAuthorizedCommentException();
        }
    }

    private void validateCommentBelongsToPost(Comment comment, Long postId) {
        if (!comment.getPost().getId().equals(postId)) {
            throw new NoSuchCommentException("해당 게시물에 대한 댓글이 아닙니다.");
        }
    }
}