import React from "react";
import PropTypes from "prop-types";
import useStyles from "./styles";
import { Button } from "@material-ui/core";
import { Formik, Form, Field } from "formik";
import { FormikTextField } from "formik-material-fields";
import * as Yup from "yup";

const CommentInput = ({ commentSubmitClick }) => {
  const classes = useStyles();

  const validationSchema = Yup.object({
    Comment: Yup.string("Enter your comment")
      .required("Comment can not be empty.")
      .max(255, "Review is too long."),
  });

  return (
    <div className={classes.commentContainer}>
      <Formik
        className={classes.root}
        initialValues={{ Comment: "" }}
        validationSchema={validationSchema}
        onSubmit={commentSubmitClick}
      >
        {({ errors, touched, values, isValid }) => (
          <Form className={classes.form}>
            <Field
              component={FormikTextField}
              className={`${classes.input} ${classes.field}`}
              name="Comment"
              label="Enter your review"
              InputProps={{
                className: classes.input,
                multiline: true,
              }}
            ></Field>
            <Button className={classes.submit} type="submit" disabled={!isValid}>
              Submit
            </Button>
          </Form>
        )}
      </Formik>
    </div>
  );
};

CommentInput.propTypes = {
  commentList: PropTypes.object.isRequired,
  isAuthorized: PropTypes.bool.isRequired,
  commentSubmitClick: PropTypes.func.isRequired,
};

export default CommentInput;
