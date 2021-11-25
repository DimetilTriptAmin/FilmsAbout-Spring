package services;

import by.karelin.business.dto.Requests.UpdateCommentRequest;
import by.karelin.business.dto.Responses.ServiceResponse;
import by.karelin.business.exceptions.ServiceException;
import by.karelin.business.services.interfaces.ICommentService;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes = by.karelin.filmsabout.FilmsAboutApplication.class)
public class CommentServiceTest {
    @Autowired
    private ICommentService commentService;

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(commentService);
    }
}
