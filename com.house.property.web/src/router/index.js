import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import House from '@/components/House'
import Register from '@/components/Register'
import HouseEdit from '@/components/HouseEdit'
import HouseAdd from '@/components/HouseAdd'

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
      path: '/houseEdit',
      name: 'houseEdit',
      component: HouseEdit
    },
    {
      path: '/houseAdd',
      name: 'houseAdd',
      component: HouseAdd
    }
  ]
})
