<template>
  <el-container>
    <el-header>
      <p>Budge - Not Logged In ❌</p>
    </el-header>
    <el-main>
      <slot></slot>
    </el-main>
  </el-container>
</template>

<script lang="ts">
import { ElContainer } from "element-plus";
import { defineComponent } from "vue";

export default defineComponent({
  components: ElContainer,
});
</script>

<style lang="scss" scoped></style>
