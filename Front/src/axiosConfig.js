import axios from 'axios';

const api = axios.create({
  baseURL: 'https://back-end-production-fcb2.up.railway.app', // URL do backend.
  headers: {
    'Content-Type': 'application/json',
  },
});

export default api;
