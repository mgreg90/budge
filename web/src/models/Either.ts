export class Either<T1, T2> {
  value?: T1;
  exception?: T2;

  constructor(value?: T1, exception?: T2) {
    this.value = value;
    this.exception = exception;
    this.validate();
  }

  validate(): void {
    if ((!!this.value && !this.exception) || (!this.value && !!this.exception))
      return;
    throw Error("Cannot create Either with value and exception!");
  }

  isSuccess(): boolean {
    return !!this.value;
  }

  isFailure(): boolean {
    return !!this.exception;
  }
}
