import React from "react";
import { useState } from "react";
import { useDispatch } from "react-redux";
import PropTypes from "prop-types";

import { commentDeleteRequest, commentUpdateRequest } from "../redux/actions/index";
import CommentMenu from "../views/CommentMenu";

const CommentMenuContainer = ({ id }) => {
  const [anchorEl, setAnchorEl] = useState(null);
  const [isEditing, setIsEditing] = useState(null);
  const isMenuOpen = Boolean(anchorEl);
  const dispatch = useDispatch();

  const handleMenuOpen = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  const deleteCommentClickHandler = () => {
    dispatch(commentDeleteRequest(id));
  };

  const commentSubmitClickHandler = ({Comment}) => {
    dispatch(commentUpdateRequest({commentId: id, text: Comment}));
    endEditing();
  }

  const startEditing = () => {
    setIsEditing(true)
  }

  const endEditing = () => {
    setIsEditing(false)
  }

  return (
    <CommentMenu
      handleMenuOpen={handleMenuOpen}
      handleMenuClose={handleMenuClose}
      isMenuOpen={isMenuOpen}
      anchorEl={anchorEl}
      deleteCommentClickHandler={deleteCommentClickHandler}
      commentSubmitClickHandler={commentSubmitClickHandler}
      isEditing={isEditing}
      startEditing={startEditing}
      endEditing={endEditing}
    />
  );
};

CommentMenuContainer.propTypes = {
  id: PropTypes.number.isRequired,
};

export default CommentMenuContainer;
