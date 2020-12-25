import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import House from '@/components/House'
import Register from '@/components/Register'
import Details from '@/components/Details'
import Newadd from '@/components/Newadd'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'login',
      component: Login
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/house',
      name: 'house',
      component: House
    },
    {
      path: '/register',
      name: 'register',
      component: Register
    },
    {
      path: '/details',
      name: 'details',
      component: Details
    },
    {
      path: '/newadd',
      name: 'newadd',
      component: Newadd
    }
  ]
})
