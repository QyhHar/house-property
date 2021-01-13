import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/login/Login'
import House from '@/components/house/House'
import Register from '@/components/login/Register'
import HouseEdit from '@/components/house/HouseEdit'
import HouseAdd from '@/components/house/HouseAdd'
import HouseBuy from '@/components/house/HouseBuy'
import HouseRenting from '@/components/house/HouseRenting'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'house',
      component: House
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
    },
    {
      path: '/houseBuy',
      name: 'houseBuy',
      component: HouseBuy
    },
    {
      path: '/houseRenting',
      name: 'houseRenting',
      component: HouseRenting
    }

  ]
})
