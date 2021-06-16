// When these need inputs, write them as functions that return element+ validation functions
// e.g. validatePasswordConfirmation

export const validatePasswordConfirmation =
  (passwordFunc: () => string) =>
  (_: any, value: string, callback: (arg?: Error) => void) => {
    if (value != passwordFunc()) callback(new Error("Must match password"));
    callback();
  };

// taken from http://emailregex.com/ - RFC5322 Compliant
// eslint-disable-next-line
const emailRegex =
  /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

export const validateEmail = (
  _: any,
  value: string,
  callback: (arg?: Error) => void
) => {
  if (!emailRegex.exec(value))
    callback(new Error("Must be a valid email address"));
  callback();
};
