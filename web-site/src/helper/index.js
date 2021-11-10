export const isObjectEmpty = (obj) => Object.keys(obj).length === 0;

export const DecodeBase64 = (value) => `data:image/png;base64, ${value}`;

export const RemoveBase64 = (value, dataType) =>
  value.substring(`data:${dataType};base64,`.length);
