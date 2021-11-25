import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import PropTypes from "prop-types";

import { commentListRequest, commentSubmitRequest, commentPagesAmountRequest } from "../redux/actions";
import CommentList from "../views/CommentList";
import {
  filmCommentListSelector,
  isAuthorizedSelector,
} from "../redux/selectors";

const CommentContainer = ({ filmId }) => {
  const dispatch = useDispatch();
  const commentList = useSelector(filmCommentListSelector);
  const isAuthorized = useSelector(isAuthorizedSelector);
  const page = useSelector((state) => state.commentList.page);
  const pagesAmount = useSelector((state) => state.commentList.pagesAmount);

  useEffect(() => {
    dispatch(commentListRequest({filmId, pageNumber:0, pageSize:10}));
    dispatch(commentPagesAmountRequest({filmId, pageSize:10}));
  }, [filmId, dispatch]);

  const commentSubmitClick = ({ Comment }) => {
    dispatch(commentSubmitRequest({ text: Comment, filmId: filmId }));
  };

  const handlePageClick = (event, pageNumber) => {
    dispatch(commentListRequest({ filmId, pageNumber, pageSize:10 }));
  };

  return (
    <CommentList
      page={page}
      pagesAmount={pagesAmount}
      commentList={commentList}
      isAuthorized={isAuthorized}
      commentSubmitClick={commentSubmitClick}
      handlePageClick={handlePageClick}
    />
  );
};

CommentContainer.propTypes = {
  filmId: PropTypes.number.isRequired,
};

export default CommentContainer;
