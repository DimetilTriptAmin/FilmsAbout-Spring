import { call } from "@redux-saga/core/effects";

import { axiosDefault } from "../../axios";

//#region User's call Methods

export function* userRequest(payload, accessToken) {
  return yield call(
    axiosDefault,
    `https://localhost:8443/api/User`,
    "get",
    null,
    accessToken,
  );
}

export function* changePasswordRequest(payload, accessToken) {
  return yield call(
    axiosDefault,
    `https://localhost:8443/api/User/changePassword`,
    "put",
    payload,
    accessToken,
  );
}

export function* logInRequest(payload) {
  const response = yield call(
    axiosDefault,
    `https://localhost:8443/api/User/login`,
    "post",
    JSON.stringify(payload.values),
  );
  window.localStorage.setItem("accessToken", response.data.accessToken);
  yield call(payload.push, "/");
  return response;
}

export function* updateRequest(payload, accessToken) {
  const response = yield call(
    axiosDefault,
    `https://localhost:8443/api/User/avatar`,
    "put",
    JSON.stringify(payload),
    accessToken,
  );
  return response;
}

export function* registrationRequest(payload) {
  const response = yield call(
    axiosDefault,
    `https://localhost:8443/api/User/register`,
    "post",
    JSON.stringify(payload.values),
  );
  window.localStorage.setItem("accessToken", response.data.accessToken);
  yield call(payload.push, "/");
  return response;
}

export function* logOutRequest(payload, accessToken) {
  yield window.localStorage.removeItem("accessToken");
  const response = yield call(
    axiosDefault,
    `https://localhost:8443/api/User/logout`,
    "delete",
    payload,
    accessToken,
  );
  yield call(payload.go(0));
  return response;
}

export function* currentFilmRatingRequest(payload, accessToken) {
  return yield call(
    axiosDefault,
    `https://localhost:8443/api/Rating/${payload}`,
    "get",
    null,
    accessToken,
  );
}

export function* goToSettingsRequest(payload) {
  yield call(payload.push, "/profile");
}

export function* setFilmRatingRequest(payload, accessToken) {
  return yield call(
    axiosDefault,
    `https://localhost:8443/api/Rating`,
    "post",
    payload,
    accessToken,
  );
}

//#endregion

//#region Comment's call Methods

export function* commentListRequest(payload) {
  return yield call(
    axiosDefault,
    `https://localhost:8443/api/Comment/all/${payload}`,
    "get",
  );
}

export function* commentSubmitRequest(payload, accessToken) {
  return yield call(
    axiosDefault,
    `https://localhost:8443/api/Comment`,
    "post",
    payload,
    accessToken,
  );
}

export function* commentDeleteRequest(payload, accessToken) {
  return yield call(
    axiosDefault,
    `https://localhost:8443/api/Comment/${payload}`,
    "delete",
    null,
    accessToken,
  );
}

//#endregion

//#region Film's call Methods

export function* filmListRequest() {
  return yield call(axiosDefault, `https://localhost:8443/api/Film/all`, "get");
}

export function* filmRequest(payload) {
  return yield call(
    axiosDefault,
    `https://localhost:8443/api/Film/${payload}`,
    "get",
  );
}

export function* filmIdRequest(payload) {
  return yield call(
    axiosDefault,
    `https://localhost:8443/api/Film/id/${payload.replaceAll("-", " ")}`,
    "get",
  );
}

export function* goToFilmRequest(payload) {
  yield call(
    payload.push,
    "/" + payload.title.replace(/\s+/g, "-").toLowerCase(),
  );
}

//#endregion

//#region Rating's call Methods

export function* getUserRatingRequest(payload) {
  return yield call(
    axiosDefault,
    `https://localhost:8443/api/Rating/${payload.filmId}`,
    "get",
  );
}

//#endregion
