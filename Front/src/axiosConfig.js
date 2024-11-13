import axios from 'axios';

const api = axios.create({
  baseURL: 'https://back-end-production-fcb2.up.railway.app', // Essa URL será substituída pela URL correta do backend.
});

export default api;
