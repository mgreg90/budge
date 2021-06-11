<template>
  <div class="login">
    {{ message }}
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "Login",
  components: {},
  setup() {
    const message = "Login Page"
    return {
      message
    }
  },
});
</script>
