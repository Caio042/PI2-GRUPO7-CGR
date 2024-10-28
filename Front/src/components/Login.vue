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
export default {
  name: 'Login',
  data() {
    return {
      form: {
        email: '',
        password: '',
      },
    }
  },
  methods: {
    handleSubmit() {
      // Supondo que o login foi bem-sucedido
      this.$router.push('/menu-principal')
    },
  },
}
</script>
