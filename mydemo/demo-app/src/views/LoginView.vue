<template>
    <div>
        <span>用户名：</span><input type="text" v-model="username" placeholder="请输入用户名"/><br />
        <span>密码：</span><input type="password" v-model="password" placeholder="请输入密码"/><br />
        <button @click="login">登录</button>
    </div>
    
</template>
<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const username = ref('');
const password = ref('');
const router = useRouter();

function login() {
    let isSuccess = false;
    axios.get('http://localhost:4000/users').then((res) => {
        console.log(res.data);
        res.data.forEach((item) => {
            if (item.name === username.value && item.password === password.value) {
                isSuccess = true;
                router.push('/');
            }
        })
        if (!isSuccess) {
            alert('用户名或密码错误');
        }
    })
    router.push('/home');
}

</script>
<style scoped>

</style>