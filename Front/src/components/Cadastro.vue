<template>
  <div class="cadastro-container">
    <img
      src="./assets/GLP.png"
      alt="CGR - Controle de Gestão para Revendas"
      class="logo"
    />
    <h1>Cadastro</h1>
    <form @submit.prevent="handleSubmit">
      <label for="nome">Nome:</label>
      <input type="text" id="nome" v-model="form.nome" required />
      <label for="login">Email:</label>
      <input type="email" id="login" v-model="form.login" required />
      <label for="password">Senha:</label>
      <input type="password" id="senha" v-model="form.senha" required />
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
        nome: '',
        login: '',
        senha: '',
      },
    }
  },
  methods: {
    async handleSubmit() {
      const formData = {
        nome: this.form.name,
        login: this.form.login,
        senha: this.form.password,
      };

  console.log('Enviando dados do formulário:', formData);
      try {
        const response = await api.post('/usuarios', formData);
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
