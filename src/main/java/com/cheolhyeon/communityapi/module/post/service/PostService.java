package com.cheolhyeon.communityapi.module.post.service;

import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.auth.exception.AuthException;
import com.cheolhyeon.communityapi.module.auth.repository.UsersRepository;
import com.cheolhyeon.communityapi.module.auth.type.AuthErrorStatus;
import com.cheolhyeon.communityapi.module.comment.entity.Comment;
import com.cheolhyeon.communityapi.module.comment.repository.CommentRepository;
import com.cheolhyeon.communityapi.module.post.dto.PostGetResponse;
import com.cheolhyeon.communityapi.module.post.dto.PostRequest;
import com.cheolhyeon.communityapi.module.post.dto.PostResponse;
import com.cheolhyeon.communityapi.module.post.entity.Post;
import com.cheolhyeon.communityapi.module.post.exception.PostException;
import com.cheolhyeon.communityapi.module.post.repository.PostRepository;
import com.cheolhyeon.communityapi.module.post.type.PostErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UsersRepository usersRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Post save(PostRequest postRequest, String username) {
        Users findUser = usersRepository.findByUsername(username).orElseThrow(
                () -> new AuthException(AuthErrorStatus.USER_NOT_FOUND)
        );

        Post post = Post.create(postRequest);
        findUser.addPost(post);
        return postRepository.save(post);
    }

    public PostGetResponse getPost(Long id) {
        Post post = postRepository.findPostWithUser(id).orElseThrow(
                () -> new PostException(PostErrorStatus.POST_NOT_FOUND)
        );
        List<Comment> commentByPostId = commentRepository.findByPostId(id);
        String username = post.getUser().getUsername();
        return PostGetResponse.create(post, username, commentByPostId);
    }

    public Page<PostResponse> getAllPosts(Pageable pageable) {
        Page<Post> postList = postRepository.findAll(pageable);
        return postList.map(PostResponse::create);
    }

    public Page<PostResponse> getCommentsSortedPageable(Pageable pageable) {
        int limit = pageable.getPageSize();
        int offset = pageable.getPageNumber() * pageable.getPageSize();

        List<Post> postList = postRepository.findAllByCommentsCount(limit, offset);

        List<PostResponse> postResponseList = postList.stream()
                .map(PostResponse::create)
                .sorted()
                .collect(Collectors.toList());

        return new PageImpl<>(postResponseList, pageable, postList.size());
    }

}
