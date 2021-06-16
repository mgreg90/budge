<template>
  <el-row class="wrapper">
    <el-col :span="6" :offset="9">
      <el-card>
        <el-container>
          <el-col :span="20" :offset="2">
            <h2>Sign Up</h2>
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
              :rules="signUpFormValidations"
              :model="signUpFormData"
              @submit.prevent="handleSignUpFormSubmit"
              ref="signUpForm"
            >
              <el-form-item label="Email" prop="email">
                <el-input
                  v-model="signUpFormData.email"
                  placeholder="Email"
                  prefix-icon="el-icon-user"
                />
              </el-form-item>
              <el-form-item label="Password" prop="password">
                <el-input
                  v-model="signUpFormData.password"
                  placeholder="Password"
                  prefix-icon="el-icon-lock"
                  type="password"
                  show-password
                />
              </el-form-item>
              <el-form-item
                label="Password Confirmation"
                prop="passwordConfirmation"
              >
                <el-input
                  v-model="signUpFormData.passwordConfirmation"
                  placeholder="Password Confirmation"
                  prefix-icon="el-icon-lock"
                  type="password"
                  show-password
                />
              </el-form-item>
              <el-button
                :loading="signUpFormIsSubmitting"
                native-type="submit"
                size="medium"
                type="primary"
                >Submit</el-button
              >
              <p class="signup-link-msg">
                Already have an account?
                <router-link to="/login">Sign In</router-link>
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

import { ISignUpFormData } from "../types/formData.types";
import {
  validateEmail,
  validatePasswordConfirmation,
} from "../customFormValidations";
import { useStore } from "@/store";
import { ActionTypes } from "@/store/modules/auth";
import router from "@/router";

export default defineComponent({
  name: "SignUp",
  components: {},
  setup() {
    // Store
    const store = useStore();

    // Form
    const signUpForm: Ref<HTMLFormElement | undefined> = ref(undefined);

    // Form Data
    const signUpFormData: ISignUpFormData = reactive({
      email: "",
      password: "",
      passwordConfirmation: "",
    });

    // Form Validations
    const signUpFormValidations = {
      email: [
        {
          required: true,
          message: "Required",
          trigger: "blur",
        },
        {
          min: 5,
          message: "Length must be at least 5 characters",
          trigger: "blur",
        },
        {
          validator: validateEmail,
          trigger: "blur",
        },
      ],
      password: [
        { required: true, message: "Password is required", trigger: "blur" },
        {
          min: 5,
          message: "Length must be at least 5 characters",
          trigger: "blur",
        },
      ],
      passwordConfirmation: [
        {
          required: true,
          message: "Required",
          trigger: "blur",
        },
        {
          validator: validatePasswordConfirmation(
            () => signUpFormData.password
          ),
          trigger: "blur",
        },
      ],
    };

    // Form Submit Handler
    const handleSignUpFormSubmit = async () => {
      const form = signUpForm.value;
      if (!form) return;

      try {
        await form.validate();
      } catch (e) {
        console.error("Failed to validate!", e);
        return;
      }

      store.dispatch(ActionTypes.SIGNUP, signUpFormData);
      if (errorMessages.value.length) return;

      router.push("/home");
    };

    // Form Computed Properties
    const signUpFormIsSubmitting = computed<boolean>(
      () => store.getters.isLoading
    );

    const errorMessages = computed<string[]>(
      () => store.getters.signupErrorMessages
    );

    // Return Values
    return {
      signUpForm,
      signUpFormData,
      signUpFormValidations,
      handleSignUpFormSubmit,
      signUpFormIsSubmitting,
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
