<template>
  <div class="cadastro-compra">
    <h1>Cadastro de Compra de Gás</h1>
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label for="fornecedor">Fornecedor:</label>
        <select id="fornecedor" v-model="form.fornecedor">
          <option value="Ultragaz">Ultragaz</option>
          <option value="Liquigás">Liquigás</option>
        </select>
      </div>
      <div class="form-group">
        <label for="produto">Tamanho do Vasilhame:</label>
        <select id="produto" v-model="form.produto">
          <option value="P05">P05</option>
          <option value="P13">P13</option>
          <option value="P20">P20</option>
          <option value="P45">P45</option>
        </select>
      </div>
      <div class="form-group">
        <label for="quantidade_entrada">Quantidade:</label>
        <input
          type="number"
          id="quantidade_entrada"
          v-model="form.quantidade_entrada"
          required
        />
      </div>
      <div class="form-group">
        <label for="valor_compra">Valor (R$):</label>
        <input
          type="number"
          id="valor_compra"
          v-model="form.valor_compra"
          required
          step="0.01"
        />
      </div>
      <button type="submit">Cadastrar</button>
    </form>
    <button @click="goBack" class="button-voltar">Voltar ao Menu Principal</button>
  </div>
</template>

<script>
import api from '../axiosConfig';

export default {
  name: 'CadastroCompra',
  data() {
    return {
      form: {
        quantidade_entrada: '',
        valor_compra: '',
        produto: '',
        fornecedor: '',
      },
    }
  },
  methods: {
    async handleSubmit() {
      const formData = { ...this.form };

      console.log('Dados do formulário:', formData);
      try {
        const token = localStorage.getItem('authToken');
        console.log('Token:', token);

        const response = await api.post('/estoques', formData, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        console.log('Resposta do servidor:', response);
        alert('Cadastro de compra realizado com sucesso!');
        this.$router.push('/menu-principal'); // Redireciona para o menu principal
      } catch (error) {
        console.error('Erro ao realizar o cadastro da compra:', error);
        alert('Erro ao realizar o cadastro da compra. Tente novamente.');
      }
    },
    goBack() {
      this.$router.push('/menu-principal');
    }
  },
}
</script>

<style scoped>
.cadastro-compra {
  background-color: var(--primary-color);
  color: white;
  padding: 20px;
  border-radius: 5px;
}

.cadastro-compra {
  width: 350px;
  min-height: 350px;
  padding: 50px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
}

.cadastro-compra form {
  display: flex;
  flex-direction: column;
}

.cadastro-compra .form-group {
  margin-bottom: 20px;
}

.button-voltar {
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.button-voltar:hover {
  background-color: #45a049;
}
</style>
