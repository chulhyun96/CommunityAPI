package com.cheolhyeon.communityapi.module.post.service;

import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.auth.exception.AuthException;
import com.cheolhyeon.communityapi.module.auth.repository.UsersRepository;
import com.cheolhyeon.communityapi.module.auth.type.AuthErrorStatus;
import com.cheolhyeon.communityapi.module.post.dto.PostGetResponse;
import com.cheolhyeon.communityapi.module.post.dto.PostRequest;
import com.cheolhyeon.communityapi.module.post.dto.PostResponse;
import com.cheolhyeon.communityapi.module.post.entity.Post;
import com.cheolhyeon.communityapi.module.post.exception.PostException;
import com.cheolhyeon.communityapi.module.post.repository.PostRepository;
import com.cheolhyeon.communityapi.module.post.type.PostErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public Post save(PostRequest postRequest, String username) {
        log.info("Saving post: Post = {}, User = {}", postRequest, username);
        Users findUser = usersRepository.findByUsername(username).orElseThrow(
                () -> {
                    log.info("User not found for username = {}", username);
                    return new AuthException(AuthErrorStatus.USER_NOT_FOUND);
                }
        );

        Post post = Post.create(postRequest);
        findUser.addPost(post);
        return postRepository.save(post);
    }

    public PostGetResponse getPost(Long id) {
        log.info("Getting post: Post = {}", id);
        Post post = postRepository.findPostWithCommentsAndUsers(id).orElseThrow(
                () -> {
                    log.info("Post not found: Post = {}", id);
                    return new PostException(PostErrorStatus.POST_NOT_FOUND);
                }
        );
        String username = post.getWriter();
        return PostGetResponse.create(post, username, post.getComments());
    }

    public Page<PostResponse> getAllPosts(Pageable pageable) {
        Page<Post> postList = postRepository.findAll(pageable);
        return postList.map(PostResponse::create);
    }

    public Page<PostResponse> getCommentsSortedPageable(Pageable pageable) {
        int limit = pageable.getPageSize();
        int offset = pageable.getPageNumber() * pageable.getPageSize();

        List<Post> postList = postRepository.findAllSortedByCommentsCount(limit, offset);

        List<PostResponse> postResponseList = postList.stream()
                .map(PostResponse::create)
                .sorted()
                .collect(Collectors.toList());

        return new PageImpl<>(postResponseList, pageable, postList.size());
    }

}
