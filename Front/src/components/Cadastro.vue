<template>
  <div class="cadastro-container">
    <img
      src="./assets/GLP.png"
      alt="CGR - Controle de Gestão para Revendas"
      class="logo"
    />
    <h1>Cadastro</h1>
    <form @submit.prevent="handleSubmit">
      <label for="name">Nome:</label>
      <input type="text" id="name" v-model="form.name" required />
      <label for="email">Email:</label>
      <input type="email" id="email" v-model="form.email" required />
      <label for="password">Senha:</label>
      <input type="password" id="password" v-model="form.password" required />
      <button type="submit">Cadastrar</button>
    </form>
    <p>Já tem cadastro? <router-link to="/">Login</router-link></p>
  </div>
</template>

<script>
import api from '../axiosConfig';

export default {
  name: 'Cadastro',
  data() {
    return {
      form: {
        name: '',
        email: '',
        password: '',
      },
    }
  },
  methods: {
    async handleSubmit() {
      const formData = {
        name: this.form.name,
        email: this.form.email,
        password: this.form.password,
      };

  console.log('Enviando dados do formulário:', formData);
      try {
        const response = await api.post('/cadastro', formData);
        console.log('Resposta do servidor:', response);
        alert('Cadastro realizado com sucesso!');
        this.$router.push('/'); // Redireciona para a página de login
      } catch (error) {
        console.error('Erro ao realizar o cadastro:', error);
        alert('Erro ao realizar o cadastro. Tente novamente.');
      }
    }
  }
}
</script>
