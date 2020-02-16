import Vue from 'vue'
import Router from 'vue-router'
import index from '../views/index'

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'index',
      component: index,
      redirect:'/home',
      children:[
        {
          path:'/home',
          name: 'home',
          component:() => import('../components/home'),
        },
      ]
    },
    {
      path:'/register',
      name: 'register',
      component:() => import('../components/register'),
    },
    {
      path:'/login',
      name: 'login',
      component:() => import('../components/login'),
    },
    {
      path:'/manage',
      name: 'manage',
      component:() => import('../views/manage'),
      redirect: '/manage/m_users',
      children: [
        {
          path:'/manage/m_users',
          name:'m_users',
          component:() => import('../components/manage/m_users'),
        },
        {
          path:'/manage/m_picture',
          name:'m_picture',
          component:() => import('../components/manage/m_picture'),
        },
      ]
    },
  ]
})
