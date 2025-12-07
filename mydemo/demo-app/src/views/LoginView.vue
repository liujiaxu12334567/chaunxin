<template>
  <div class="login-bg">
    <div class="login-card">
      <div class="card-header">
        <h2>{{ isLogin ? 'Innovation Mall' : 'Join Us' }}</h2>
        <p>{{ isLogin ? '欢迎回到创新班商城' : '创建您的专属账号' }}</p>
      </div>

      <div class="form-container">
        <div class="input-group">
          <input type="text" v-model="form.name" placeholder="Username" />
        </div>
        <div class="input-group">
          <input type="password" v-model="form.password" placeholder="Password" />
        </div>

        <button class="submit-btn" @click="handleSubmit">
          {{ isLogin ? 'SIGN IN' : 'SIGN UP' }}
        </button>

        <div class="switch-mode">
          <span @click="toggleMode">
            {{ isLogin ? 'Create an account' : 'Already have an account? Sign in' }}
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const isLogin = ref(true)
const form = reactive({ name: '', password: '' })

function toggleMode() {
  isLogin.value = !isLogin.value
  form.name = ''
  form.password = ''
}

async function handleSubmit() {
  if (!form.name || !form.password) return alert('请输入完整信息')

  const url = isLogin.value
      ? 'http://localhost:8080/api/user/login'
      : 'http://localhost:8080/api/user/register'

  try {
    const res = await axios.post(url, form)
    if (res.data.success) {
      if (isLogin.value) {
        localStorage.setItem('currentUser', JSON.stringify(res.data.user))
        router.push('/')
      } else {
        alert('注册成功，请登录')
        toggleMode()
      }
    } else {
      alert(res.data.message)
    }
  } catch (e) {
    alert('连接服务器失败')
  }
}
</script>

<style scoped>
.login-bg {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  /* 高级深蓝渐变 */
  background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%);
  font-family: 'Segoe UI', sans-serif;
}

.login-card {
  background: rgba(255, 255, 255, 0.95);
  width: 380px;
  padding: 50px 40px;
  border-radius: 20px;
  box-shadow: 0 20px 50px rgba(0, 0, 0, 0.3);
  text-align: center;
}

.card-header h2 {
  color: #1e3c72;
  margin-bottom: 10px;
  font-weight: 700;
  letter-spacing: 1px;
}

.card-header p {
  color: #888;
  font-size: 13px;
  margin-bottom: 40px;
}

.input-group {
  margin-bottom: 20px;
}

.input-group input {
  width: 100%;
  padding: 15px;
  border: none;
  background-color: #f0f4f8;
  border-radius: 30px;
  font-size: 14px;
  text-indent: 10px;
  outline: none;
  transition: all 0.3s;
  box-sizing: border-box;
}

.input-group input:focus {
  background-color: #fff;
  box-shadow: 0 0 0 2px #2a5298;
}

.submit-btn {
  width: 100%;
  padding: 15px;
  background: linear-gradient(to right, #1e3c72, #2a5298);
  border: none;
  color: white;
  font-weight: bold;
  letter-spacing: 1px;
  border-radius: 30px;
  cursor: pointer;
  margin-top: 20px;
  box-shadow: 0 10px 20px rgba(30, 60, 114, 0.2);
  transition: transform 0.2s;
}

.submit-btn:hover {
  transform: translateY(-2px);
}

.switch-mode {
  margin-top: 25px;
  font-size: 12px;
  color: #666;
}

.switch-mode span {
  cursor: pointer;
  text-decoration: none;
  color: #1e3c72;
  font-weight: 600;
}
</style>