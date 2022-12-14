import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    overlay: false,
    port: 3000,
    contentBase: ".",
    proxy: {
      "/api": "http://localhost:8080",
    },
  },
  build: {
    outDir: "../src/main/resources/static",
  }
});

//현준님 노트북 IP
// 'http://10.1.8.207:8080'

// 민아님 노트북 IP
//'http://10.1.8.136:8080'

// 집 테스크탑
//'http://192.168.0.103:8080/'