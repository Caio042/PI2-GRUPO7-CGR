<template>
  <div class="cadastro-venda">
    <img
      src="./assets/GLP.png"
      alt="CGR - Controle de Gestão para Revendas"
      class="logo"
    />
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
        <label for="quantidade">Quantidade:</label>
        <input
          type="number"
          id="quantidade"
          v-model="form.quantidade"
          required
        />
      </div>
      <div class="form-group">
        <label for="valorVenda">Valor de Venda (R$):</label>
        <input
          type="number"
          id="valorVenda"
          v-model="form.valorVenda"
          required
          step="0.01"
        />
      </div>
      <button type="submit">Cadastrar Venda</button>
    </form>
  </div>
</template>

<script>
export default {
  name: 'CadastroVenda',
  data() {
    return {
      form: {
        produto: '',
        quantidade: '',
        valorVenda: '',
      },
    }
  },
  methods: {
    handleSubmit() {
      console.log('Dados da venda:', this.form)
    },
  },
}
</script>
