<template>
  <div class="home">
    {{ message }}
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "Home",
  components: {},
  setup() {
    const message = "Home Page - Protected";

    return {
      message,
    };
  },
});
</script>
