<template>
  <p>商品名：{{goodsInfo.name}}</p>
  <p>商品价格：{{goodsInfo.price}}</p>
  <p>用户评论：{{goodsInfo.comments}}</p>

</template>

<script setup>
import {ref,onMounted} from "vue";
import axios from "axios";
import {useRouter} from"vue-router";
const router = useRouter();
const goodsId = ref(router.currentRoute.value.params.id);
const goodsInfo =ref({})
onMounted(() => {
  axios.get(`http://localhost:8080/api/goods/detail?id=${goodsId.value}`)
    .then(res => {
      if(res.data.success) {
        goodsInfo.value = res.data.data;
      }
    })
})
</script>

<style scoped>

</style>