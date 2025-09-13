import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/api/v1",
});

// for the real application key would be stored in redis or some similiar cache instead of here 

api.interceptors.request.use((config) => {
  config.headers["SECURITY-KEY"] = "key";
  return config;
});

export default api;