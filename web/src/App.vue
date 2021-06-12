<template>
  <authenticated-layout v-if="isLoggedIn()">
    <router-view />
  </authenticated-layout>
  <unauthenticated-layout v-else>
    <router-view />
  </unauthenticated-layout>
</template>

<script lang="ts">
import AuthenticatedLayout from "./layouts/AuthenticatedLayout.vue";
import UnauthenticatedLayout from "./layouts/UnauthenticatedLayout.vue";
export default {
  name: "App",
  components: {
    AuthenticatedLayout,
    UnauthenticatedLayout,
  },
  setup() {
    const isLoggedIn = () => false; // TODO
    return {
      isLoggedIn,
    };
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
