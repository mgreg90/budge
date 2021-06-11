<template>
  <div class="signup">
    {{ message }}
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "SignUp",
  components: {},
  setup() {
    const message = "Sign Up Page"
    return {
      message
    }
  },
});
</script>
