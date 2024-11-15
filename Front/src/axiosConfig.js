import axios from 'axios';

const api = axios.create({
  baseURL: 'https://back-end-production-fcb2.up.railway.app',
});

api.interceptors.request.use(config => {
  const token = localStorage.getItem('authToken');
  if (token) {
    config.headers.Authorization = `${token}`;
  }
  return config;
});

export default api;
