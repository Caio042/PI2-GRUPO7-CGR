<template>
  <div class="cadastro-compra">
    <img
      src="./assets/GLP.png"
      alt="CGR - Controle de Gestão para Revendas"
      class="logo"
    />
    <h1>Cadastro de Compra de Gás</h1>
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label for="fornecedor">Fornecedor:</label>
        <select id="fornecedor" v-model="form.fornecedor">
          <option value="liquigas">Liquigás</option>
          <option value="ultragaz">Ultragaz</option>
        </select>
      </div>
      <div class="form-group">
        <label for="tamanho">Tamanho do Vasilhame:</label>
        <select id="tamanho" v-model="form.tamanho">
          <option value="P05">P05</option>
          <option value="P13">P13</option>
          <option value="P20">P20</option>
          <option value="P45">P45</option>
        </select>
      </div>
      <div class="form-group">
        <label for="quantidade">Quantidade:</label>
        <input
          type="number"
          id="quantidade"
          v-model="form.quantidade"
          required
        />
      </div>
      <div class="form-group">
        <label for="valor">Valor (R$):</label>
        <input
          type="number"
          id="valor"
          v-model="form.valor"
          required
          step="0.01"
        />
      </div>
      <button type="submit">Cadastrar</button>
    </form>
  </div>
  <!-- <div class="image-container">
    <img src="./assets/entregaGlp.png" alt="Imagem ilustrativa" class="image" />
  </div> -->
</template>

<script>
export default {
  name: 'CadastroCompra',
  data() {
    return {
      form: {
        fornecedor: '',
        tamanho: '',
        quantidade: '',
        valor: '',
      },
    }
  },
  methods: {
    handleSubmit() {
      // Lógica para enviar os dados para o backend
      console.log('Dados do formulário:', this.form)
    },
  },
}
</script>
