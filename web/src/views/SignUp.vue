<template>
  <div class="signup">
    <el-card>
      <h2>Signup</h2>
      <el-form
        class="signup-form"
        :model="model"
        :rules="rules"
        ref="form"
        @submit.prevent="signup"
      >
        <el-form-item prop="username">
          <el-input
            v-model="model.username"
            placeholder="Username"
            prefix-icon="fas fa-user"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="model.password"
            placeholder="Password"
            type="password"
            prefix-icon="fas fa-lock"
          ></el-input>
        </el-form-item>
        <el-form-item prop="passwordConfirmation">
          <el-input
            v-model="model.passwordConfirmation"
            placeholder="Password Confirmation"
            type="password"
            prefix-icon="fas fa-lock"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            :loading="loading"
            class="signup-button"
            type="primary"
            native-type="submit"
            block
            >Signup</el-button
          >
        </el-form-item>
        <div>
          Already have an account? <a href="/login">Login</a>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "signup",
  data() {
      const validatePasswordConfirmation = (rule, value, callback) => {
          if (value != this.model.password) callback(new Error("Does not match with password"))
      }
    return {
      validCredentials: {
        username: "lightscope",
        password: "lightscope",
      },
      model: {
        username: "",
        password: "",
        passwordConfirmation: ""
      },
      loading: false,
      rules: {
        username: [
          {
            required: true,
            message: "Username is required",
            trigger: "blur",
          },
          {
            min: 4,
            message: "Username length should be at least 5 characters",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "Password is required", trigger: "blur" },
          {
            min: 5,
            message: "Password length should be at least 5 characters",
            trigger: "blur",
          },
        ],
        passwordConfirmation: [
          { required: true, message: "Password Confirmation is required", trigger: "blur" },
          {
              validator: validatePasswordConfirmation,
              trigger: 'change'
          }
        ],
      },
    };
  },
  methods: {
    simulatesignup() {
      return new Promise((resolve) => {
        setTimeout(resolve, 800);
      });
    },
    async signup() {
      let valid = await this.$refs.form.validate();
      if (!valid) {
        return;
      }
      this.loading = true;
      await this.simulatesignup();
      this.loading = false;
      if (
        this.model.username === this.validCredentials.username &&
        this.model.password === this.validCredentials.password
      ) {
        this.$message.success("signup successfull");
      } else {
        this.$message.error("Username or password is invalid");
      }
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.signup {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}

.signup-button {
  width: 100%;
  margin-top: 40px;
}
.signup-form {
  width: 290px;
}
.forgot-password {
  margin-top: 10px;
}
</style>
<style lang="scss">
$teal: rgb(0, 124, 137);
.el-button--primary {
  background: $teal;
  border-color: $teal;

  &:hover,
  &.active,
  &:focus {
    background: lighten($teal, 7);
    border-color: lighten($teal, 7);
  }
}
.signup .el-input__inner:hover {
  border-color: $teal;
}
.signup .el-input__prefix {
  background: rgb(238, 237, 234);
  left: 0;
  height: calc(100% - 2px);
  left: 1px;
  top: 1px;
  border-radius: 3px;
  .el-input__icon {
    width: 30px;
  }
}
.signup .el-input input {
  padding-left: 35px;
}
.signup .el-card {
  padding-top: 0;
  padding-bottom: 30px;
}
h2 {
  font-family: "Open Sans";
  letter-spacing: 1px;
  font-family: Roboto, sans-serif;
  padding-bottom: 20px;
}
a {
  color: $teal;
  text-decoration: none;
  &:hover,
  &:active,
  &:focus {
    color: lighten($teal, 7);
  }
}
.signup .el-card {
  width: 340px;
  display: flex;
  justify-content: center;
}
</style>
