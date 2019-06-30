package edu.iis.mto.blog.domain.repository;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.model.User;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LikePostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LikePostRepository repository;

    private User user;
    private BlogPost blogPost;
    private LikePost likePost;

    @Before
    public void setUp() {
        user = new User();
        user.setFirstName("Stefan");
        user.setLastName("Kowalski");
        user.setEmail("stefan@domain.com");
        user.setAccountStatus(AccountStatus.NEW);
        entityManager.persist(user);

        blogPost = new BlogPost();
        blogPost.setUser(user);
        blogPost.setEntry("test");
        entityManager.persist(blogPost);

        likePost = new LikePost();
        likePost.setUser(user);
        likePost.setPost(blogPost);
    }

    @Test
    public void shouldFindNoLikePostIfRepositoryIsEmpty() {

        List<LikePost> users = repository.findAll();

        Assert.assertThat(users, Matchers.hasSize(0));
    }


    @Test
    public void shouldFindOneUsersIfRepositoryContainsOneUserEntity() {
        LikePost like = entityManager.persist(likePost);
        List<LikePost> likePosts = repository.findAll();

        Assert.assertThat(likePosts, Matchers.hasSize(1));
        Assert.assertThat(likePosts.get(0).getUser(), Matchers.equalTo(like.getUser()));
    }


    @Test
    public void shouldStoreANewUser() {

        LikePost like = repository.save(likePost);

        Assert.assertThat(like.getId(), Matchers.notNullValue());
    }

    @Test
    public void shouldFindLikeByUSerAndPost(){
        LikePost persistedLike = entityManager.persist(likePost);
        LikePost like = repository.findByUserAndPost(user, blogPost).orElse(null);

        Assert.assertThat(like.getUser(), Matchers.equalTo(persistedLike.getUser()));
        Assert.assertThat(like.getPost(), Matchers.equalTo(persistedLike.getPost()));
    }

    @Test
    public void shouldNotFindLikeByUSerAndPost(){
        LikePost persistedLike = entityManager.persist(likePost);
        LikePost like = repository.findByUserAndPost(user, null).orElse(null);

        Assert.assertThat(like, Matchers.nullValue());
    }

}