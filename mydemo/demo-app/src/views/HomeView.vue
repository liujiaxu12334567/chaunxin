<template>
    <div>  
        <button @click="login">登录</button>
        <div class="cardlist">        <Cardcomponent v-for="(item,index) in list" :key="index" :goodName="item.name" :imgurl="'http://localhost:4000'+item.imgurl"  :desc="item.introduce" :goodid="item.id"
            @addToCart="(countFromChild)=>{
                changeCount(countFromChild,item)
            }">

        </Cardcomponent>
</div>
    </div>
   
</template>
<script setup>
import axios from 'axios';
 import {ref,onMounted} from 'vue';
import {useRouter } from'vue-router';
import Cardcomponent from '@/commponents/Cardcomponent.vue';

    const router=useRouter();
    const list =ref([])

function login(){
   router.push('/login');
}
onMounted(()=>{
    axios.get('http://localhost:4000/goods').then((response)=>{
        console.log(response.data)
        list.value=response.data;
    })
})
function changeCount(countFromChild,item){
    console.log(`${item.name}添加到购物车了，当前数量是${countFromChild}`)
}
</script>
<style scoped>
    .cardlist{
        display: flex;
        justify-content: space-evenly;
    }
</style>
