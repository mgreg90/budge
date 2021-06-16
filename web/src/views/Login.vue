<template>
  <el-row class="wrapper">
    <el-col :span="6" :offset="9">
      <el-card>
        <el-container>
          <el-col :span="20" :offset="2">
            <h2>Sign In</h2>
            <!-- TODO Figure out how to make this a component -->
            <ul class="error-msg-list">
              <li
                class="error-msg-item"
                v-for="message in errorMessages"
                :key="message"
              >
                <i class="el-icon-warning" /> {{ message }}
              </li>
            </ul>
            <el-form
              :rules="loginFormValidations"
              :model="loginFormData"
              @submit.prevent="handleLoginFormSubmit"
              ref="loginForm"
            >
              <el-form-item label="Email" prop="email">
                <el-input placeholder="Email" v-model="loginFormData.email" />
              </el-form-item>
              <el-form-item label="Password" prop="password">
                <el-input
                  placeholder="Password"
                  v-model="loginFormData.password"
                  type="password"
                />
              </el-form-item>
              <el-button
                :loading="loginFormIsSubmitting"
                native-type="submit"
                size="medium"
                type="primary"
                >Submit</el-button
              >
              <p class="login-link-msg">
                Don't have an account?
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
import { computed, defineComponent, reactive, Ref, ref } from "vue";

import { ILogInFormData } from "../types/formData.types";
import { useStore } from "@/store";
import { validateEmail } from "../customFormValidations";
import { ActionTypes } from "@/store/modules/auth";
import router from "@/router";

export default defineComponent({
  name: "SignUp",
  components: {},
  setup() {
    // Store
    const store = useStore();

    // Form
    const loginForm: Ref<HTMLFormElement | undefined> = ref(undefined);

    // Form Data
    const loginFormData: ILogInFormData = reactive({
      email: "",
      password: "",
    });

    // Form Validations
    const loginFormValidations = {
      email: [
        { required: true, message: "Required", trigger: "blur" },
        {
          min: 5,
          message: "Length must be at least 5 characters",
          trigger: "blur",
        },
        { validator: validateEmail, trigger: "blur" },
      ],
      password: [
        { required: true, message: "Required", trigger: "blur" },
        {
          min: 5,
          message: "Length must be at least 5 characters",
          trigger: "blur",
        },
      ],
    };

    // Form Submit Handler
    const handleLoginFormSubmit = async () => {
      const form = loginForm.value;
      if (!form) return;

      try {
        await form.validate();
      } catch (e) {
        console.error("Failed to validate!", e);
        return;
      }

      await store.dispatch(ActionTypes.LOGIN, loginFormData);
      if (errorMessages.value.length) return;

      router.push("/home");
    };

    // Form Computed Properties
    const loginFormIsSubmitting = computed<boolean>(
      () => store.getters.isLoading
    );

    const errorMessages = computed<string[]>(
      () => store.getters.loginErrorMessages
    );

    return {
      loginForm,
      loginFormData,
      loginFormValidations,
      handleLoginFormSubmit,
      loginFormIsSubmitting,
      errorMessages,
    };
  },
});
</script>

<style lang="scss" scoped>
@import "~element-plus/packages/theme-chalk/src/common/var";

.wrapper {
  margin-top: 4rem;
}

.error-msg-list {
  margin: 0;
  padding: 0;
  text-align: start;
  min-height: 22px;
}

.error-msg-item {
  list-style-type: none;
  padding: 0;
  margin: 0;
  color: $--color-danger;
}
</style>
