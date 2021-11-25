import React from "react";
import Comment from "../Comment";
import PropTypes from "prop-types";
import { Container } from "@material-ui/core";
import Pagination from "@mui/material/Pagination";
import { Oval } from "@agney/react-loading";
import { ErrorOutline } from "@material-ui/icons";
import useStyles from "./styles";
import CommentInput from "../CommentInput";

const CommentList = ({
  commentList,
  isAuthorized,
  commentSubmitClick,
  handlePageClick,
  pagesAmount,
  page,
}) => {
  const classes = useStyles();

  return (
    <Container maxWidth="lg">
      {isAuthorized && (
        <CommentInput commentSubmitClick={commentSubmitClick} />
      )}
      {!commentList.isLoading ? (
        commentList.Loaded ? (
          <>
            {commentList.values.map((comment, key) => (
              <Comment
                id={comment.id}
                username={comment.username}
                avatar={comment.avatar}
                text={comment.text}
                publishDate={comment.publishDate}
                rating={comment.rating}
                key={key}
              />
            ))}
            <div className={classes.paginationContainer}>
              <Pagination
                classes={{ ul: classes.pagination }}
                color="standard"
                count={pagesAmount}
                onChange={handlePageClick}
                page={page}
              />
            </div>
          </>
        ) : (
          <div className={`${classes.metaComponent} ${classes.flex}`}>
            <ErrorOutline
              style={{ fontSize: 100, color: "#fff", margin: "auto" }}
            />
          </div>
        )
      ) : (
        <Container className={`${classes.metaComponent} ${classes.flex}`}>
          <Oval width="100" color="#fff" />
        </Container>
      )}
    </Container>
  );
};

CommentList.propTypes = {
  commentList: PropTypes.object.isRequired,
  isAuthorized: PropTypes.bool.isRequired,
  commentSubmitClick: PropTypes.func.isRequired,
};

export default CommentList;
