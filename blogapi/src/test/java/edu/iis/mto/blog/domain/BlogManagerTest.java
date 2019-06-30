package edu.iis.mto.blog.domain;

import edu.iis.mto.blog.domain.errors.DomainError;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.repository.BlogPostRepository;
import edu.iis.mto.blog.domain.repository.LikePostRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import edu.iis.mto.blog.api.request.UserRequest;
import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.User;
import edu.iis.mto.blog.domain.repository.UserRepository;
import edu.iis.mto.blog.mapper.BlogDataMapper;
import edu.iis.mto.blog.services.BlogService;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogManagerTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    BlogDataMapper dataMapper;

    @Autowired
    BlogService blogService;

    @MockBean
    BlogPostRepository blogPostRepository;

    @MockBean
    LikePostRepository likePostRepository;

    private User confirmedUser;
    private User newUser;
    private BlogPost blogPost;

    @Before
    public void setUp() {
        confirmedUser = new User();
        confirmedUser.setFirstName("Ala");
        confirmedUser.setLastName("Nowak");
        confirmedUser.setEmail("ala@domain.com");
        confirmedUser.setAccountStatus(AccountStatus.CONFIRMED);
        confirmedUser.setId(1l);

        newUser = new User();
        newUser.setFirstName("Ola");
        newUser.setLastName("Nowakowa");
        newUser.setEmail("ola@domain.com");
        newUser.setAccountStatus(AccountStatus.NEW);
        newUser.setId(2l);

        blogPost = new BlogPost();
        blogPost.setEntry("entry");
        blogPost.setUser(newUser);

        Mockito.when(userRepository.findById(confirmedUser.getId())).thenReturn(Optional.of(confirmedUser));
        Mockito.when(userRepository.findById(newUser.getId())).thenReturn(Optional.of(newUser));
        Mockito.when(blogPostRepository.findById(blogPost.getId())).thenReturn(Optional.of(blogPost));
    }


    @Test
    public void creatingNewUserShouldSetAccountStatusToNEW() {
        blogService.createUser(new UserRequest("John", "Steward", "john@domain.com"));
        ArgumentCaptor<User> userParam = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userParam.capture());
        User user = userParam.getValue();
        Assert.assertThat(user.getAccountStatus(), Matchers.equalTo(AccountStatus.NEW));
    }

    @Test
    public void confirmedUser_shouldAddLikeToPost() {
        blogService.addLikeToPost(confirmedUser.getId(), blogPost.getId());

        ArgumentCaptor<LikePost> likePostParam = ArgumentCaptor.forClass(LikePost.class);
        Mockito.verify(likePostRepository).save(likePostParam.capture());
        LikePost like = likePostParam.getValue();
        Assert.assertThat(like.getUser(), Matchers.equalTo(confirmedUser));
        Assert.assertThat(like.getPost(), Matchers.equalTo(blogPost));
    }

    @Test(expected = DomainError.class)
    public void newUser_addLikeToPost_shouldThrowError() {
        blogService.addLikeToPost(newUser.getId(), blogPost.getId());
        Assert.assertTrue(false);
    }

}
