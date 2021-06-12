// When these need inputs, write them as functions that return element+ validation functions
// e.g. validatePasswordConfirmation

export const validatePasswordConfirmation =
  (passwordFunc: () => string) =>
  (_: any, value: string, callback: (arg?: Error) => void) => {
    if (value != passwordFunc())
      callback(new Error("Does not match with password"));
    callback();
  };
