<template>
  <ul class="error-msg-list">
    <li class="error-msg-item" v-for="message in errorMessages" :key="message">
      <i class="el-icon-warning" /> {{ message }}
    </li>
  </ul>
</template>

<script lang="ts">
interface Props {
  errorMessages: string[];
}

import { defineComponent } from "vue";

export default defineComponent((props: Props) => ({
  name: "FormServerErrors",
  components: {},
  props: {
    errorMessages: {
      type: Array,
      required: true,
    },
  },
  setup() {
    const { errorMessages } = props;
    return {
      errorMessages,
    };
  },
}));
</script>

<style lang="scss" scoped>
@import "~element-plus/packages/theme-chalk/src/common/var";

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
