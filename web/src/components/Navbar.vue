<template>
<el-affix>
  <el-menu
    mode="horizontal"
    :router="true"
    :default-active="activeRoute"
  >
    <el-menu-item
      v-for="item in menuItems"
      :class="item.class ?? undefined"
      :key="item.key"
      :index="item.route ?? undefined"
      @click="item?.handleClick && item.handleClick()"
    >
      {{ item.text }}
    </el-menu-item>
  </el-menu>
</el-affix>
</template>

<script lang="ts">
import { defineComponent, ref, watch } from 'vue'
import { v4 as uuid } from 'uuid';
import router, { goTo, RouteEnum } from '@/router';
import { useRoute } from 'vue-router';
import { RootStore, useStore } from '@/store';
import { MutationTypes } from '@/store/modules/auth/mutations';
import { ActionTypes } from '@/store/modules/auth';

interface Props {
  isLoggedIn: boolean
}

interface MenuItem {
  key: string
  text: string
  route?: RouteEnum
  class?: string
  handleClick?: () => void
}

export default defineComponent({
  props: {
    isLoggedIn: {
      type: Boolean,
      required: true,
    },
  },
  setup(props: Props) {
    console.log('props', props)
    const route = useRoute()
    const store = useStore()

    let menuItems: MenuItem[] = buildMenuItems(props.isLoggedIn, store)

    let activeRoute = ref('')
    const setRouterActive = () => {
      const currentPath = window.location.pathname
      menuItems = buildMenuItems(props.isLoggedIn, store)

      activeRoute.value = menuItems.find((item: MenuItem) => item.route === currentPath)?.route ?? '/'
      console.log('menuItems', menuItems)
      console.log('currentPath', currentPath)
      console.log('activeRoute', activeRoute)
    }

    watch(() => route.name, setRouterActive)
    setRouterActive()
    return {
      menuItems,
      activeRoute,
    }
  },
})

const buildMenuItems = (isLoggedIn: boolean, store: RootStore): MenuItem[] => {
  let menuItems: MenuItem[] = [
    {
      key: uuid(),
      text: "Budge",
      class: "logo",
      handleClick: () => {
        goTo(RouteEnum.Transactions)
      }
    },
  ]
  if (isLoggedIn) {
    menuItems = [
      ...menuItems,
      {
        key: uuid(),
        text: "Transactions",
        route: RouteEnum.Transactions,
      },
      {
        key: uuid(),
        text: "Data",
        route: RouteEnum.Data,
      },
      {
        key: uuid(),
        text: "Log Out",
        handleClick: () => {
          store.dispatch(ActionTypes.SET_SESSION, null)
        },
      }
    ];
  } else {
    menuItems = [
      ...menuItems,
      {
        key: uuid(),
        text: "Sign In",
        route: RouteEnum.Login,
      },
      {
        key: uuid(),
        text: "Register",
        route: RouteEnum.Register,
      },
    ];
  }
  return menuItems
}
</script>

<style lang="scss" scoped>

</style>