import React from "react";
import IconButton from "@material-ui/core/IconButton";
import PropTypes from "prop-types";
import MoreVert from "@material-ui/icons/MoreVert";
import { Menu, MenuItem, Modal } from "@material-ui/core";
import CommentInput from "../CommentInput";

import useStyles from "./styles";

const CommentMenu = ({
  isMenuOpen,
  anchorEl,
  handleMenuClose,
  handleMenuOpen,
  deleteCommentClickHandler,
  isEditing,
  endEditing,
  startEditing,
  commentSubmitClickHandler,
}) => {
  const classes = useStyles();
  return (
    <div className={classes.flex}>
      <IconButton
        edge="end"
        aria-haspopup="true"
        color="inherit"
        onClick={handleMenuOpen}
      >
        <MoreVert style={{ color: "#fff" }} />
      </IconButton>

      <Menu
        disableScrollLock={true}
        className={classes.menu}
        anchorEl={anchorEl}
        getContentAnchorEl={null}
        anchorOrigin={{ vertical: "bottom", horizontal: "left" }}
        open={isMenuOpen}
        onClose={handleMenuClose}
      >
        <MenuItem className={classes.menuItem} onClick={startEditing}>
          Edit
        </MenuItem>
        <MenuItem
          className={classes.menuItem}
          onClick={deleteCommentClickHandler}
        >
          Delete
        </MenuItem>
      </Menu>

      <Modal
        open={isEditing}
        onClose={endEditing}
        aria-labelledby="Edit comment"
      >
        <div className={classes.modal}>
          <CommentInput commentSubmitClick={commentSubmitClickHandler} />
        </div>
      </Modal>
    </div>
  );
};

CommentMenu.propTypes = {
  isMenuOpen: PropTypes.bool.isRequired,
  anchorEl: PropTypes.any,
  handleMenuClose: PropTypes.func.isRequired,
  handleMenuOpen: PropTypes.func.isRequired,
  deleteCommentClickHandler: PropTypes.func.isRequired,
};

export default CommentMenu;
