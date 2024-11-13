<template>
  <div class="login">
    <img
      src="./assets/GLP.png"
      alt="CGR - Controle de Gestão para Revendas"
      class="logo"
    />

    <h1>Login</h1>
    <form @submit.prevent="handleSubmit">
      <label for="email">Email:</label>
      <input type="email" id="email" v-model="form.email" required />
      <label for="password">Senha:</label>
      <input type="password" id="password" v-model="form.password" required />
      <button type="submit">Entrar</button>
    </form>

    <!-- Mensagem de erro -->
    <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>

    <p>
      Não possui cadastro? <router-link to="/Cadastro">Cadastre-se</router-link>
    </p>
  </div>
</template>

<script>
import api from '../axiosConfig';

export default {
  name: 'Login',
  data() {
    return {
      form: {
        email: '',
        password: '',
      },
      errorMessage: ''
    }
  },
  methods: {
    async handleSubmit() {
      try {
        const response = await api.post('/login', this.form);
        // Supondo que o login retorne um token de autenticação
        const token = response.data.token;
        localStorage.setItem('authToken', token); // Salva o token no localStorage
        this.$router.push('/menu-principal'); // Redireciona para o menu principal
      } catch (error) {
        this.errorMessage = 'Falha no login. Verifique suas credenciais.';
      }
    },
  },
}
</script>
