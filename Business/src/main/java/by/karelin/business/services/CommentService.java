package by.karelin.business.services;

import by.karelin.business.dto.Responses.CommentResponse;
import by.karelin.business.dto.Responses.ServiceResponse;
import by.karelin.business.services.interfaces.ICommentService;
import by.karelin.domain.models.Comment;
import by.karelin.domain.models.Film;
import by.karelin.domain.models.Rating;
import by.karelin.domain.models.User;
import by.karelin.persistence.repositories.ICommentRepository;
import by.karelin.persistence.repositories.IFilmRepository;
import by.karelin.persistence.repositories.IRatingRepository;
import by.karelin.persistence.repositories.IUserRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class CommentService implements ICommentService {

    private IUserRepository userRepository;
    private ICommentRepository commentRepository;
    private IFilmRepository filmRepository;
    private IRatingRepository ratingRepository;

    public ServiceResponse<CommentResponse> CreateComment(Long userId, Long filmId, String text) {
        try {
            if (!userRepository.existsById(userId) || !filmRepository.existsById(filmId)) {
                return new ServiceResponse<CommentResponse>("User or related film not found.");
            }

            User user = userRepository.getById(userId);
            Film film = filmRepository.getById(filmId);

            Comment comment = new Comment(user, film, text, new Date());
            commentRepository.save(comment);

            Rating rating = new Rating();
            rating.setFilm(film);
            rating.setUser(user);

            Example<Rating> ratingExample = Example.of(rating);
            Optional<Rating> ratingOptional = ratingRepository.findAll(ratingExample).stream().findFirst();

            int rate = ratingOptional.isEmpty() ? 0 : rating.getRate();

            return new ServiceResponse<CommentResponse>(
                    new CommentResponse(comment.getId(), user.getName(), user.getAvatar(), text, rate, comment.getPublishDate())
            );
        } catch (Exception e) {
            return new ServiceResponse<CommentResponse>("Internal server error: " + e.getMessage());
        }
    }

    public ServiceResponse<Long> DeleteComment(Long id) {
        try {
            if (!commentRepository.existsById(id)) {
                return new ServiceResponse<>("Comment not found.");
            }

            commentRepository.deleteById(id);

            return new ServiceResponse<>(id);
        } catch (Exception e) {
            return new ServiceResponse<>("Internal server error.");
        }
    }

    public ServiceResponse<List<CommentResponse>> GetAllByFilmId(Long id) {
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
