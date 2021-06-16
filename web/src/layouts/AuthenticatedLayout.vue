<template>
  <el-container>
    <el-header>
      <p>Budge - Logged In ✅</p>
    </el-header>
    <el-main>
      <slot></slot>
    </el-main>
  </el-container>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({});
</script>

<style lang="scss" scoped></style>
