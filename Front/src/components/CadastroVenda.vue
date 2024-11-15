<template>
  <div class="cadastro-venda">
    <h1>Cadastro de Venda</h1>
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label for="produto">Produto:</label>
        <select id="produto" v-model="form.produto">
          <option value="P05">P05</option>
          <option value="P13">P13</option>
          <option value="P20">P20</option>
          <option value="P45">P45</option>
        </select>
      </div>
      <div class="form-group">
        <label for="quantidade_saida">Quantidade:</label>
        <input
          type="number"
          id="quantidade_saida"
          v-model="form.quantidade_saida"
          required
        />
      </div>
      <div class="form-group">
        <label for="valor_venda">Valor de Venda (R$):</label>
        <input
          type="number"
          id="valor_venda"
          v-model="form.valor_venda"
          required
          step="0.01"
        />
      </div>
      <div class="form-group">
        <label for="fornecedor">Fornecedor:</label>
        <select id="fornecedor" v-model="form.fornecedor">
          <option value="Ultragaz">Ultragaz</option>
          <option value="Liquigás">Liquigás</option>
        </select>
      </div>
      <button type="submit">Cadastrar Venda</button>
    </form>
  </div>
</template>

<script>
import api from '../axiosConfig';

export default {
  name: 'CadastroVenda',
  data() {
    return {
      form: {
        produto: '',
        quantidade_saida: '',
        valor_venda: '',
        fornecedor: '',
      },
    }
  },
  methods: {
    async handleSubmit() {
      const formData = { ...this.form };

      console.log('Dados da venda:', formData);
      try {
        const response = await api.post('/estoques', formData, {
          headers: {
            Authorization: 'Basic ZnVsYW5vQGVtYWlsLmNvbToxMjM0',
            'Content-Type': 'application/json',
          },
        });
        console.log('Resposta do servidor:', response);
        alert('Cadastro de venda realizado com sucesso!');
        this.$router.push('/menu-principal'); // Redireciona para o menu principal
      } catch (error) {
        console.error('Erro ao realizar o cadastro da venda:', error);
        alert('Erro ao realizar o cadastro da venda. Tente novamente.');
      }
    },
  },
}
</script>
