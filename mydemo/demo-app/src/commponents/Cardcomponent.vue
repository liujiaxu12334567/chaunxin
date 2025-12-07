<template>
  <div class="goods-card" @click="goToDetail">
    <div class="fav-icon" @click.stop="toggleFav">
      <span v-if="isFav" style="color: #ff4757;">♥</span>
      <span v-else style="color: #ccc;">♡</span>
    </div>

    <div class="img-wrapper">
      <img :src="imgurl" :alt="goodName" />
    </div>

    <div class="info-body">
      <h3 class="title">{{ goodName }}</h3>
      <p class="desc">{{ desc }}</p>
      <div class="card-footer">
        <button class="add-cart-btn" @click.stop="addToCart">
          + 加入购物车
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

// 接收 isFav 属性
const props = defineProps(['goodName', 'imgurl', 'desc', 'goodid', 'isFav'])
const emits = defineEmits(['addToCart', 'toggleFav'])
const router = useRouter()

function addToCart() {
  emits('addToCart', 1)
}

function toggleFav() {
  // 通知父组件切换收藏状态
  emits('toggleFav', props.goodid)
}

function goToDetail() {
  router.push(`/detail/${props.goodid}`)
}
</script>

<style scoped>
.goods-card {
  position: relative; /* 为绝对定位提供参考 */
  background: white;
  border-radius: 12px;
  overflow: hidden;
  transition: all 0.3s ease;
  cursor: pointer;
  border: 1px solid #f0f0f0;
  display: flex;
  flex-direction: column;
  height: 380px;
}

.goods-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 15px 30px rgba(0,0,0,0.1);
}

.fav-icon {
  position: absolute;
  top: 15px;
  right: 15px;
  font-size: 24px;
  z-index: 10;
  cursor: pointer;
  background: rgba(255,255,255,0.8);
  border-radius: 50%;
  width: 35px;
  height: 35px;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: transform 0.2s;
}

.fav-icon:hover {
  transform: scale(1.2);
}

.img-wrapper {
  height: 200px;
  background-color: #f9f9f9;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
}

.img-wrapper img {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
}

.info-body {
  padding: 15px;
  flex: 1;
  display: flex;
  flex-direction: column;
}

.title {
  font-size: 16px;
  margin: 0 0 8px 0;
  color: #333;
}

.desc {
  font-size: 13px;
  color: #888;
  margin: 0;
  line-height: 1.5;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.card-footer {
  margin-top: auto;
  text-align: right;
  padding-top: 15px;
}

.add-cart-btn {
  background-color: #1e3c72;
  color: white;
  border: none;
  padding: 8px 15px;
  border-radius: 20px;
  font-size: 12px;
  cursor: pointer;
}
</style>