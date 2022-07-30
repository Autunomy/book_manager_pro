import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true,
    meta:{
      title: "用户登陆"
    }
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页面板', icon: 'dashboard' }
    }]
  },

  {
    //院系管理
    path: '/department',
    component: Layout,
    redirect: '/department/list',
    name: 'department',
    meta: { title: '院系管理', icon: 'el-icon-film' },
    children: [
      {
        path: 'list',
        name: 'list',
        component: () => import('@/views/department/list'),
        meta: { title: '院系列表', icon: 'el-icon-document' }
      },
      {
        path: 'add',
        name: 'add',
        component: () => import('@/views/department/add'),
        meta: { title: '添加院系', icon: 'el-icon-news' }
      }
    ]
  },

  {
    //学生管理
    path: '/student',
    component: Layout,
    redirect: '/student/list',
    name: 'student',
    meta: { title: '学生管理', icon: 'el-icon-user' },
    children: [
      {
        path: 'list',
        name: 'list',
        component: () => import('@/views/student/list'),
        meta: { title: '学生列表', icon: 'el-icon-document' }
      },
      {
        path: 'add',
        name: 'add',
        component: () => import('@/views/student/add'),
        meta: { title: '添加学生', icon: 'el-icon-news' }
      }
    ]
  },
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
   mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
