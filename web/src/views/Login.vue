<template>
  <el-row class="wrapper">
    <el-col :span="8" :offset="8">
      <el-card>
        <el-container>
          <el-col :span="20" :offset="2">
            <h2>Sign In</h2>
            <el-form
              @submit.prevent="handleSubmit"
              :rules="rules"
              :model="signUpFormData"
            >
              <el-form-item label="Email" prop="email">
                <el-input placeholder="Email" v-model="signUpFormData.email" />
              </el-form-item>
              <el-form-item label="Password" prop="password">
                <el-input
                  placeholder="Password"
                  v-model="signUpFormData.password"
                  type="password"
                />
              </el-form-item>
              <el-button native-type="submit" size="medium" type="primary"
                >Submit</el-button
              >
              <p class="login-link-msg">
                Need to create an account?
                <router-link to="/register">Sign Up</router-link>
              </p>
            </el-form>
          </el-col>
        </el-container>
      </el-card>
    </el-col>
  </el-row>
</template>

<script lang="ts">
import { defineComponent, reactive } from "vue";

import { ILogInFormData } from "../types/formData.types";

export default defineComponent({
  name: "SignUp",
  components: {},
  setup() {
    const signUpFormData: ILogInFormData = reactive({
      email: "",
      password: "",
    });

    const rules = {
      email: [
        {
          required: true,
          message: "Email is required",
          trigger: "blur",
        },
        {
          min: 5,
          message: "Email length should be at least 5 characters",
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
    };

    const handleSubmit = () => {
      const x = signUpFormData;
      debugger;
    };

    return {
      signUpFormData,
      rules,

      handleSubmit,
    };
  },
});
</script>

<style lang="scss" scoped>
.wrapper {
  margin-top: 4rem;
}
</style>
