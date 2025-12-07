<template>
  <div class="card" @click="goToDestil">
    <h3>商品名：{{ goodName }}</h3>
    <img :src="imgurl" alt="">
    <p>{{ desc }}</p>
    <button @click.stop="addToCart">+</button>
  </div>
</template>

<script setup>
    import { ref } from 'vue';
import { useRouter } from 'vue-router';
    let count=0
    const props=defineProps(['goodName','imgurl','desc','goodid'])
    const emits =defineEmits(['addToCart'])
    const router=useRouter()
    function addToCart(){
        count++
        emits('addToCart',count)
    }
    function goToDestil(){
        router.push(`/detail/`+props.goodid)
    }
</script>

<style scoped>
    .card{
        width: 300px;
        height: 400px;
        border: 2px solid grey;
        display: flex;
        flex-direction:column;
        align-items: center;
        border-radius: 20px;
        box-shadow: 5px 5px 10px grey;
        position: relative;

    }
    .card img{
        width: 80%;
        height: 70%;
    }
    .card p{
        font-size: 10px;
        color: grey;
        margin: 0 10px ;
    }
    .card button{
        position: absolute;
        bottom:5px;
        right: 5px;

    }
</style>