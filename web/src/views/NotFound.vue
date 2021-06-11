<template>
  <div class="notfound">
    {{ message }}
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "NotFound",
  components: {},
  setup() {
    const message = "404 - Not Found"
    return {
      message
    }
  },
});
</script>
