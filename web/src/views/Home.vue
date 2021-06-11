<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png" />
    <HelloWorld msg="Welcome to Your Vue.js + TypeScript App" />
    {{ wow() }}
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import HelloWorld from "@/components/HelloWorld.vue"; // @ is an alias to /src
import { useStore } from "vuex";

export default defineComponent({
  name: "Home",
  components: {
    HelloWorld,
  },
  setup() {
    const store = useStore()
    const wow = () => {
      console.log(store)
      return 'Wow!'
    }

    return {
      wow
    }
  }
});
</script>
