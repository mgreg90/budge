<template>
  <authenticated-layout v-if="session">
    <router-view />
  </authenticated-layout>
  <unauthenticated-layout v-else>
    <router-view />
  </unauthenticated-layout>
</template>

<script lang="ts">
import { computed } from "@vue/runtime-core";
import AuthenticatedLayout from "./layouts/AuthenticatedLayout.vue";
import UnauthenticatedLayout from "./layouts/UnauthenticatedLayout.vue";
import Session from "./models/Session";
import { useStore } from "./store";
import { ActionTypes } from "./store/modules/auth/actions";

export default {
  name: "App",
  components: {
    AuthenticatedLayout,
    UnauthenticatedLayout,
  },
  setup() {
    const store = useStore();

    store.dispatch(ActionTypes.SET_SESSION);

    const session = computed<Session | null>(() => store.getters.session);

    return {
      session,
    };
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
</style>
