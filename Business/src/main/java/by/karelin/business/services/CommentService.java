package by.karelin.business.services;

import by.karelin.persistence.dto.Responses.CommentResponse;
import by.karelin.persistence.dto.Responses.ServiceResponse;
import by.karelin.business.services.interfaces.ICommentService;
import by.karelin.domain.models.Comment;
import by.karelin.domain.models.Film;
import by.karelin.domain.models.User;
import by.karelin.persistence.repositories.interfaces.ICommentRepository;
import by.karelin.persistence.repositories.interfaces.IFilmRepository;
import by.karelin.persistence.repositories.interfaces.IRatingRepository;
import by.karelin.persistence.repositories.interfaces.IUserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentService implements ICommentService {

    private IUserRepository userRepository;
    private ICommentRepository commentRepository;
    private IFilmRepository filmRepository;
    private IRatingRepository ratingRepository;

    public ServiceResponse<CommentResponse> createComment(Long userId, Long filmId, String text) {
        try {
            User user = userRepository.getById(userId);
            Film film = filmRepository.getById(filmId);

            Comment comment = new Comment(user, film, text);

            boolean creationFailed = !commentRepository.tryCreateComment(comment);

            if(creationFailed){
                return new ServiceResponse<CommentResponse>("Could not create comment.");
            }

            //TODO: Rating
            return null;

        } catch (Exception e) {
            return new ServiceResponse<CommentResponse>("Internal server error: " + e.getMessage());
        }
    }

    public ServiceResponse<Long> deleteComment(Long id) {
        return null;
    }

    public ServiceResponse<List<CommentResponse>> getAllByFilmId(Long id) {
        try {
            //TODO: НАПИСАТЬ СТОРКУ
            /*
            var comments = await _unitOfWork.CommentRepository.Filter(c => c.FilmId == id);
            var ratings = await _unitOfWork.RatingRepository.Filter(r => r.FilmId == id);
            var users = await _unitOfWork.UserRepository.GetAllAsync();

            var commentsJoinRatings = from comment in comments
            join rating in ratings
            on comment.UserId equals rating.UserId
            into CommentList
            from rating in CommentList.DefaultIfEmpty()
            select new
            {
                CommentId = comment.Id,
                        UserId = comment.UserId,
                        Text = comment.Text,
                        PublishDate = comment.PublishDate,
                        Rating = rating == null ? 0 : rating.Rate,
            };

            var commentListJoinUsers = commentsJoinRatings
                    .Join(users, cnr => cnr.UserId, user => user.Id,
                (cnr, user) =>
            new CommentResponse(cnr.CommentId, user.UserName, user.Avatar, cnr.Text, cnr.Rating, cnr.PublishDate))
                    .ToList();
            */
            List<CommentResponse> commentListJoinUsers = new ArrayList<>();
            return new ServiceResponse<>(commentListJoinUsers);
        } catch (Exception e) {
            return new ServiceResponse<>("Internal server error.");
        }
    }
}
