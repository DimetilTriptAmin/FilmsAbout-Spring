package by.karelin.business.services;

import by.karelin.business.dto.Requests.UpdateCommentRequest;
import by.karelin.business.utils.enums.ErrorCode;
import by.karelin.domain.models.Rating;
import by.karelin.business.dto.Responses.CommentResponse;
import by.karelin.business.dto.Responses.ServiceResponse;
import by.karelin.business.services.interfaces.ICommentService;
import by.karelin.domain.models.Comment;
import by.karelin.domain.models.Film;
import by.karelin.domain.models.User;
import by.karelin.domain.pojo.CommentViewModel;
import by.karelin.domain.utils.enums.UserRole;
import by.karelin.persistence.repositories.interfaces.ICommentRepository;
import by.karelin.persistence.repositories.interfaces.IFilmRepository;
import by.karelin.persistence.repositories.interfaces.IRatingRepository;
import by.karelin.persistence.repositories.interfaces.IUserRepository;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentService implements ICommentService {

    private final IUserRepository userRepository;
    private final ICommentRepository commentRepository;
    private final IFilmRepository filmRepository;
    private final ModelMapper modelMapper;
    private final IRatingRepository ratingRepository;
    private static Logger log = Logger.getLogger(CommentService.class.getName());

    public CommentService(IUserRepository userRepository, ICommentRepository commentRepository, IFilmRepository filmRepository, ModelMapper modelMapper, IRatingRepository ratingRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.filmRepository = filmRepository;
        this.modelMapper = modelMapper;
        this.ratingRepository = ratingRepository;
    }

    public ServiceResponse<CommentResponse> createComment(Long userId, Long filmId, String text) {
        try {
            User user = userRepository.getById(userId);
            Film film = filmRepository.getById(filmId);

            Comment comment = new Comment(user, film, text);

            Long createdCommentId = commentRepository.createComment(comment);

            if(createdCommentId == ErrorCode.RepositoryTransactionError.getValue()){
                return new ServiceResponse<>("Could not create comment.", HttpStatus.BAD_REQUEST);
            }

            Rating rating = ratingRepository.getUserRating(userId, filmId);

            CommentResponse response = new CommentResponse(
                    createdCommentId,
                    user.getName(),
                    user.getAvatar(),
                    text,
                    comment.getPublishDate(),
                    rating.getRate()
            );

            return new ServiceResponse<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ServiceResponse<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ServiceResponse<Long> deleteComment(Long id, Long userId) {
        try {
            Long resultId = commentRepository.deleteComment(id, userId);

            if(resultId == ErrorCode.RepositoryTransactionError.getValue()){
                return new ServiceResponse<>("Could not delete the comment.", HttpStatus.NOT_FOUND);
            }

            return new ServiceResponse<>(resultId, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ServiceResponse<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ServiceResponse<List<CommentResponse>> getCommentPage(Long id, Integer pageNumber, Integer pageSize) {
        try {
            List<CommentViewModel> commentViewModels = commentRepository.getCommentPage(id, pageNumber, pageSize);

            List<CommentResponse> commentViews
                    = modelMapper.map(commentViewModels, new TypeToken<List<CommentResponse>>() {}.getType());

            return new ServiceResponse<>(commentViews, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ServiceResponse<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ServiceResponse<List<CommentResponse>> getDeletedCommentPage(Long userId, Long id, Integer pageNumber, Integer pageSize) {
        try {
            User user = userRepository.getById(userId);

            if (user.getUserRole() == UserRole.USER.getValue()) {
                return new ServiceResponse<>("Forbidden", HttpStatus.FORBIDDEN);
            }
            //log.info("Admin gets deleted comments for film with id " + id.toString());
            List<CommentViewModel> commentViewModels = commentRepository.getDeletedCommentPage(id, pageNumber, pageSize);

            List<CommentResponse> commentViews
                    = modelMapper.map(commentViewModels, new TypeToken<List<CommentResponse>>() {
            }.getType());

            return new ServiceResponse<>(commentViews, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ServiceResponse<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ServiceResponse<Integer> getCommentPagesAmount(Long filmId, Integer pageSize) {
        try {
            Integer pagesAmount = commentRepository.getCommentPagesAmount(filmId, pageSize);

            if(pagesAmount == ErrorCode.RepositoryTransactionError.getValue()){
                return new ServiceResponse<>("Could not count the pages.", HttpStatus.BAD_REQUEST);
            }

            return new ServiceResponse<>(pagesAmount, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ServiceResponse<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ServiceResponse<Long> updateComment(Long userId, UpdateCommentRequest commentRequest) {
        try {
            Long resultId =
                    commentRepository.updateComment(commentRequest.getCommentId(), userId, commentRequest.getText());

            if(resultId == ErrorCode.RepositoryTransactionError.getValue()){
                return new ServiceResponse<>("Could update the comment.", HttpStatus.BAD_REQUEST);
            }

            return new ServiceResponse<>(resultId, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ServiceResponse<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
