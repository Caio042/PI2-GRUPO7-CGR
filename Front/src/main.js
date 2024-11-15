import { createApp } from 'vue'
import { createRouter, createWebHistory } from 'vue-router'
import App from './App.vue'
import Login from './components/Login.vue'
import Cadastro from './components/Cadastro.vue'
import CadastroCompra from './components/CadastroCompra.vue'
import CadastroVenda from './components/CadastroVenda.vue'
import Relatorios from './components/Relatorios.vue'
import MenuPrincipal from './components/MenuPrincipal.vue'
import './assets/App.css'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', name: 'login', component: Login },
    { path: '/usuarios', name: 'cadastro', component: Cadastro },
    {
      path: '/menu-principal',
      name: 'menu-principal',
      component: MenuPrincipal,
      meta: { requiresAuth: true },
    },
    {
      path: '/estoques',
      name: 'cadastro-compra',
      component: CadastroCompra,
      meta: { requiresAuth: true },
    },
    {
      path: '/cadastro-venda',
      name: 'cadastro-venda',
      component: CadastroVenda,
      meta: { requiresAuth: true },
    },
    {
      path: '/relatorios',
      name: 'relatorios',
      component: Relatorios,
      meta: { requiresAuth: true },
    },
  ],
})

const app = createApp(App)

app.use(router)
app.mount('#app')
