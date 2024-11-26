package com.cheolhyeon.communityapi.module.post.service;

import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.auth.exception.UserException;
import com.cheolhyeon.communityapi.module.auth.repository.UsersRepository;
import com.cheolhyeon.communityapi.module.auth.type.ErrorStatus;
import com.cheolhyeon.communityapi.module.post.dto.PostRequest;
import com.cheolhyeon.communityapi.module.post.entity.Post;
import com.cheolhyeon.communityapi.module.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public Post save(PostRequest postRequest, String username) {
        Users findUser = usersRepository.findByUsername(username).orElseThrow(
                () -> new UserException(ErrorStatus.USER_NOT_FOUND)
        );

        Post post = Post.create(postRequest);
        findUser.addPost(post);
        return postRepository.save(post);
    }
}
