package by.karelin.business.services;

import by.karelin.business.utils.ErrorCode;
import by.karelin.domain.models.Rating;
import by.karelin.business.dto.Responses.CommentResponse;
import by.karelin.business.dto.Responses.ServiceResponse;
import by.karelin.business.services.interfaces.ICommentService;
import by.karelin.domain.models.Comment;
import by.karelin.domain.models.Film;
import by.karelin.domain.models.User;
import by.karelin.domain.pojo.CommentViewModel;
import by.karelin.persistence.repositories.interfaces.ICommentRepository;
import by.karelin.persistence.repositories.interfaces.IFilmRepository;
import by.karelin.persistence.repositories.interfaces.IRatingRepository;
import by.karelin.persistence.repositories.interfaces.IUserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentService implements ICommentService {

    private final IUserRepository userRepository;
    private final ICommentRepository commentRepository;
    private final IFilmRepository filmRepository;
    private final ModelMapper modelMapper;
    private final IRatingRepository ratingRepository;

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
                return new ServiceResponse<CommentResponse>("Could not create comment.");
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

            return new ServiceResponse<CommentResponse>(response);
        } catch (Exception e) {
            return new ServiceResponse<CommentResponse>("Internal server error: " + e.getMessage());
        }
    }

    public ServiceResponse<Long> deleteComment(Long id, Long userId) {
        try {
            Long resultId = commentRepository.deleteComment(id, userId);

            if(resultId == ErrorCode.RepositoryTransactionError.getValue()){
                return new ServiceResponse<>("Could not delete the comment.");
            }

            return new ServiceResponse<>(resultId);
        } catch (Exception e) {
            return new ServiceResponse<>("Internal server error.");
        }
    }

    public ServiceResponse<List<CommentResponse>> getAllByFilmId(Long id) {
        try {
            List<CommentViewModel> commentViewModels = commentRepository.getAllByFilmId(id);

            List<CommentResponse> commentViews
                    = modelMapper.map(commentViewModels, new TypeToken<List<CommentResponse>>() {}.getType());

            return new ServiceResponse<List<CommentResponse>>(commentViews);
        } catch (Exception e) {
            return new ServiceResponse<>("Internal server error.");
        }
    }
}
