import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/login/Login'
import House from '@/components/house/House'
import Register from '@/components/login/Register'
import HouseEdit from '@/components/house/HouseEdit'
import HouseAdd from '@/components/house/HouseAdd'

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
