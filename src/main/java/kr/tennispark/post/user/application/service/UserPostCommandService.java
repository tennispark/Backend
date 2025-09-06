package kr.tennispark.post.user.application.service;

import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.post.common.domain.entity.Post;
import kr.tennispark.post.common.domain.entity.vo.Photos;
import kr.tennispark.post.user.application.exception.NotAuthorizedPostException;
import kr.tennispark.post.user.application.service.resolver.PostResolver;
import kr.tennispark.post.user.infrastructure.repository.UserPostRepository;
import kr.tennispark.post.user.presentation.dto.request.RegisterPostMultiPart;
import kr.tennispark.post.user.presentation.dto.request.UpdatePostMultiPart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserPostCommandService {

    private final UserPostRepository postRepository;
    private final PostResolver resolver;

    public void createPost(Member member, RegisterPostMultiPart request) {
        Photos photos = resolver.toPhotos(request.photos());
        Post post = Post.create(member, request.data().title(), request.data().content(), photos);
        postRepository.save(post);
    }

    public void updatePost(Member member, Long postId, UpdatePostMultiPart request) {
        Post post = postRepository.getById(postId);
        ensureAuthor(post, member);

        String title = request.data().title();
        String content = request.data().content();

        Photos photos = resolver.replacePhotos(post.getPhotos(), request.photos());
        post.update(title, content, photos);

    }

    public void deletePost(Member member, Long postId) {
        Post post = postRepository.getById(postId);
        ensureAuthor(post, member);

        postRepository.delete(post);
    }

    private void ensureAuthor(Post post, Member loginMember) {
        if (!post.getMember().getId().equals(loginMember.getId())) {
            throw new NotAuthorizedPostException();
        }
    }
}
