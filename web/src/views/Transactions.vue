<template>
  <div class="transactions">
    {{ privateData }}
  </div>
</template>

<script lang="ts">
import { useStore } from "@/store";
import { ActionTypes } from "@/store/modules/transactions";
import { computed, defineComponent } from "vue";

export default defineComponent({
  name: "Transactions",
  components: {},
  setup() {
    const store = useStore();

    store.dispatch(ActionTypes.GET);

    const privateData = computed<string>(() => store.getters.message);

    return {
      privateData,
    };
  },
});
</script>
