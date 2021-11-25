import { makeStyles } from "@material-ui/core/styles";

const useStyles = makeStyles((theme) => ({
  root: {
    maxWidth: 345,
    backgroundColor: "#000000",
  },
  header: {
    color: theme.palette.common.white,
  },
  rating: {
    fontStyle: "italic",
    color: theme.palette.common.white,
  },
  media: {
    paddingTop: "100%",
  },
  cardContent: {
    color: "white",
  },
  cardContentText: {
    height: "100px",
    overflow: "hidden",
  },
  button: {
    color: theme.palette.common.white,
  },
}));

export { useStyles };
