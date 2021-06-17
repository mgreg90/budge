<template>
  <el-container>
    <el-header>
      <navbar :isLoggedIn="false"></navbar>
    </el-header>
    <el-main>
      <slot></slot>
    </el-main>
  </el-container>
</template>

<script lang="ts">
import Navbar from "@/components/Navbar.vue";
import { defineComponent } from "vue";

export default defineComponent({
  components: { Navbar },
});
</script>

<style lang="scss" scoped></style>
