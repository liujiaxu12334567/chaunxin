import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '@/views/LoginView.vue'
import HomeView from '@/views/HomeView.vue'
import DetailView from '@/views/DetailView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'LoginView',
      component: LoginView,
    },
    {
      path: '/',
      name: 'HomeView',
      component: HomeView,
    },
    {
      path:'/detail/:id',
      name: 'DetailView',
      component: DetailView,
    },
  ],
})

export default router
