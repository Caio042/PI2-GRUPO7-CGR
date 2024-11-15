<template>
  <div class="relatorios">
    <h1>Relatórios</h1>
    <!-- seus relatórios aqui -->
    <div class="relatorio-card" v-for="produto in relatorio.por_produtos" :key="produto.produto">
      <h2>Estoque</h2>
      <p>Produto: {{ produto.produto }}</p>
      <p>Quantidade: {{ produto.quantidade }}</p>
      <p>Total em Compras: R$ {{ produto.total_em_compras }}</p>
      <p>Total em Vendas: R$ {{ produto.total_em_vendas }}</p>
    </div>

    <div class="relatorio-card">
      <h2>Financeiro</h2>
      <p>Total em Vendas: R$ {{ totalVendas }}</p>
      <p>Total em Compras: R$ {{ totalCompras }}</p>
    </div>
    <router-link to="/menu-principal" class="button">Voltar ao Menu Principal</router-link>
  </div>
</template>

<script>
import api from '../axiosConfig';

export default {
  name: 'Relatorios',
  data() {
    return {
      relatorio: {
        por_produtos: [],
        total_em_vendas: 0,
        total_em_compras: 0,
      },
    };
  },
  computed: {
    totalVendas() {
      return this.relatorio.total_em_vendas.toFixed(2);
    },
    totalCompras() {
      return this.relatorio.total_em_compras.toFixed(2);
    },
  },
  methods: {
    async fetchRelatorio() {
      try {
        const response = await api.get('/relatorios', {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('authToken')}`,
          },
        });
        this.relatorio = response.data;

      } catch (error) {
        console.error('Erro ao buscar o relatório:', error);
        alert('Erro ao buscar o relatório. Tente novamente.');
      }
    },
  },
  mounted() {
    this.fetchRelatorio();
  },
};
</script>

<style scoped>
.relatorio-card {
  border: 1px solid #ddd;
  padding: 10px;
  margin: 10px 0;
  border-radius: 5px;
}

.button {
  display: inline-block;
  margin: 20px 0;
  padding: 10px 20px;
  background-color: #4caf50;
  color: white;
  text-decoration: none;
  border-radius: 5px;
}
</style>
