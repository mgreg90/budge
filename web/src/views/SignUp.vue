<template>
  <el-row class="wrapper">
    <el-col :span="8" :offset="8">
      <el-card>
        <el-container>
          <el-col :span="20" :offset="2">
            <h2>Sign Up</h2>
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
              <el-button native-type="submit" size="medium" type="primary"
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
import { validatePasswordConfirmation } from "../customFormValidations";
import { useStore } from "@/store";

export default defineComponent({
  name: "SignUp",
  components: {},
  setup() {
    // Store
    const store = useStore();

    // Form
    const signUpForm: Ref<HTMLFormElement | undefined> = ref(undefined);
    const signUpFormData: ISignUpFormData = reactive({
      email: "",
      password: "",
      passwordConfirmation: "",
    });

    const signUpFormValidations = {
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
      passwordConfirmation: [
        {
          required: true,
          message: "Password Confirmation is required",
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

    const handleSignUpFormSubmit = async () => {
      const formData = signUpForm.value;
      if (!formData) return;

      try {
        await formData.validate();
      } catch (e) {
        console.error("Failed to validate!", e);
        // return
      }

      try {
        debugger
        // const { dispatch } = store.store
        await store.dispatch("signup", formData);
      } catch (e) {
        console.error("dispatch failed!", e);
      }
    };

    const signUpFormIsSubmitting = computed<boolean>(() => store.getters.isLoading);

    return {
      signUpForm,
      signUpFormData,
      signUpFormValidations,
      handleSignUpFormSubmit,
    };
  },
});
</script>

<style lang="scss" scoped>
.wrapper {
  margin-top: 4rem;
}
</style>
