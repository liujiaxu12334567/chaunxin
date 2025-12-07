<template>
  <div class="app-container">
    <nav class="navbar">
      <div class="logo">🛒 创新班商城</div>
      <div class="nav-right">
        <div v-if="user" class="user-profile">
          <span>{{ user.name }}</span>
          <button class="logout-btn" @click="logout">退出</button>
        </div>
        <button v-else class="login-btn" @click="router.push('/login')">登录</button>

        <div class="cart-box" @click="openCartDrawer">
          购物车 <span>{{ cartTotalCount }}</span>
        </div>
      </div>
    </nav>

    <main class="main-content">
      <div class="banner">
        <h1>精选好物，品质生活</h1>
      </div>

      <div class="goods-grid" v-if="list.length > 0">
        <Cardcomponent
            v-for="item in list"
            :key="item.id"
            :goodName="item.name"
            :imgurl="item.imgurl"
            :desc="item.introduce"
            :goodid="item.id"
            :isFav="favList.includes(item.id)"
            @addToCart="() => handleAddToCart(item)"
            @toggleFav="() => handleToggleFav(item.id)"
        />
      </div>
    </main>

    <div class="drawer-mask" v-if="showCart" @click="showCart = false"></div>
    <div class="cart-drawer" :class="{ open: showCart }">
      <div class="drawer-header">
        <h3>我的购物车</h3>
        <span class="close-btn" @click="showCart = false">×</span>
      </div>

      <div class="drawer-body">
        <div v-if="cartDetails.length === 0" class="empty-cart">
          <p>购物车空空如也~</p>
        </div>

        <div v-else class="cart-list">
          <div class="cart-item" v-for="item in cartDetails" :key="item.cartId">
            <img :src="item.imgurl" alt="">
            <div class="item-info">
              <h4>{{ item.name }}</h4>
              <div class="price">¥ {{ item.price }}</div>
              <div class="controls">
                <span>数量: {{ item.count }}</span>
                <button class="del-btn" @click="handleDeleteCartItem(item.cartId)">删除</button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="drawer-footer">
        <div class="total-price">
          <span>合计:</span>
          <strong>¥ {{ cartTotalPrice }}</strong>
        </div>
        <button class="checkout-btn" @click="alert('功能开发中...')">去结算</button>
      </div>
    </div>

  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import Cardcomponent from '@/commponents/Cardcomponent.vue';

const router = useRouter();
const list = ref([]); // 所有商品列表
const user = ref(null);
const cartList = ref([]); // 后端返回的购物车原始数据 (id, goodsId, count)
const favList = ref([]);
const showCart = ref(false); // 控制抽屉显示

// [新增] 计算属性：将购物车数据与商品详情组合
const cartDetails = computed(() => {
  return cartList.value.map(cartItem => {
    // 在商品列表中找到对应的商品详情
    const product = list.value.find(p => p.id === cartItem.goodsId);
    if (product) {
      return {
        ...product,        // 商品详情 (name, price, imgurl)
        count: cartItem.count, // 购物车数量
        cartId: cartItem.id    // 购物车记录ID (用于删除)
      };
    }
    return null;
  }).filter(item => item !== null);
});

// 计算属性：购物车总数量
const cartTotalCount = computed(() => {
  return cartList.value.reduce((sum, item) => sum + item.count, 0);
});

// 计算属性：购物车总价
const cartTotalPrice = computed(() => {
  return cartDetails.value.reduce((sum, item) => sum + (item.price * item.count), 0).toFixed(2);
});

onMounted(async () => {
  const userStr = localStorage.getItem('currentUser');
  if (userStr) {
    user.value = JSON.parse(userStr);
    fetchUserData();
  }
  fetchGoods();
});

function fetchGoods() {
  axios.get('http://localhost:8080/api/goods').then(res => {
    list.value = res.data;
  });
}

// 获取用户数据（购物车+收藏）
function fetchUserData() {
  if (!user.value) return;
  const uid = user.value.id;

  // 获取购物车
  axios.get(`http://localhost:8080/api/action/cart/list?userId=${uid}`).then(res => {
    cartList.value = res.data;
  });

  // 获取收藏
  axios.get(`http://localhost:8080/api/action/favorite/list?userId=${uid}`).then(res => {
    favList.value = res.data;
  });
}

// [新增] 打开购物车抽屉
function openCartDrawer() {
  if (!user.value) {
    router.push('/login');
    return;
  }
  showCart.value = true;
  fetchUserData(); // 每次打开刷新一下数据
}

// 加入购物车
function handleAddToCart(item) {
  if (!user.value) return router.push('/login');

  axios.post('http://localhost:8080/api/action/cart/add', {
    userId: user.value.id,
    goodsId: item.id,
    count: 1
  }).then(res => {
    if (res.data.success) {
      alert(`已加入购物车`);
      fetchUserData(); // 刷新购物车数据
    }
  });
}

// [新增] 删除购物车商品
function handleDeleteCartItem(cartId) {
  if(!confirm('确定要删除吗？')) return;

  axios.delete(`http://localhost:8080/api/action/cart/remove?id=${cartId}`)
      .then(res => {
        if(res.data.success) {
          // 前端直接移除，不用重新拉取接口，体验更好
          cartList.value = cartList.value.filter(item => item.id !== cartId);
        }
      });
}

function handleToggleFav(goodsId) {
  if (!user.value) return router.push('/login');
  const isCurrentlyFav = favList.value.includes(goodsId);
  const actionType = isCurrentlyFav ? 0 : 1;

  axios.post('http://localhost:8080/api/action/favorite/toggle', {
    userId: user.value.id,
    goodsId: goodsId,
    isFavorite: actionType
  }).then(res => {
    if (res.data.success) {
      if (isCurrentlyFav) {
        favList.value = favList.value.filter(id => id !== goodsId);
      } else {
        favList.value.push(goodsId);
      }
    }
  });
}

function logout() {
  localStorage.removeItem('currentUser');
  user.value = null;
  favList.value = [];
  cartList.value = [];
  router.push('/login');
}
</script>

<style scoped>
.app-container { min-height: 100vh; background-color: #f0f2f5; position: relative; overflow-x: hidden; }
.navbar { background: white; height: 64px; padding: 0 50px; display: flex; justify-content: space-between; align-items: center; box-shadow: 0 2px 8px rgba(0,0,0,0.05); position: sticky; top: 0; z-index: 100; }
.logo { font-size: 20px; font-weight: bold; color: #1e3c72; }
.nav-right { display: flex; align-items: center; gap: 20px; }
.logout-btn { background: none; border: 1px solid #ddd; padding: 5px 12px; border-radius: 4px; cursor: pointer; font-size: 12px; }
.login-btn { background: #1e3c72; color: white; border: none; padding: 8px 16px; border-radius: 4px; cursor: pointer; }

/* 购物车按钮样式升级 */
.cart-box { background-color: #eef2f7; color: #1e3c72; padding: 8px 16px; border-radius: 20px; font-weight: bold; font-size: 14px; cursor: pointer; transition: background-color 0.2s; user-select: none; }
.cart-box:hover { background-color: #dbe4ef; }

.main-content { max-width: 1200px; margin: 0 auto; padding: 30px 20px; }
.banner { background: white; padding: 40px; border-radius: 12px; margin-bottom: 30px; text-align: center; }
.banner h1 { margin: 0; color: #333; font-size: 28px; }
.goods-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(260px, 1fr)); gap: 25px; }

/* --- 侧边抽屉样式 --- */
.drawer-mask { position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); z-index: 200; }
.cart-drawer {
  position: fixed; top: 0; right: -400px; /* 默认隐藏在右侧 */
  width: 350px; height: 100%; background: white; z-index: 201;
  box-shadow: -5px 0 15px rgba(0,0,0,0.1);
  transition: right 0.3s ease;
  display: flex; flex-direction: column;
}
.cart-drawer.open { right: 0; } /* 激活时滑出 */

.drawer-header { padding: 20px; border-bottom: 1px solid #eee; display: flex; justify-content: space-between; align-items: center; }
.drawer-header h3 { margin: 0; color: #333; }
.close-btn { font-size: 24px; cursor: pointer; color: #999; }
.close-btn:hover { color: #333; }

.drawer-body { flex: 1; overflow-y: auto; padding: 20px; }
.empty-cart { text-align: center; margin-top: 50px; color: #999; }

.cart-item { display: flex; gap: 15px; margin-bottom: 20px; padding-bottom: 20px; border-bottom: 1px solid #f5f5f5; }
.cart-item img { width: 70px; height: 70px; object-fit: cover; border-radius: 8px; border: 1px solid #eee; }
.item-info { flex: 1; display: flex; flex-direction: column; justify-content: space-between; }
.item-info h4 { margin: 0; font-size: 14px; color: #333; line-height: 1.4; }
.item-info .price { color: #ff4757; font-weight: bold; margin-top: 5px; }
.controls { display: flex; justify-content: space-between; align-items: center; font-size: 12px; color: #666; margin-top: 5px; }
.del-btn { background: none; border: none; color: #999; cursor: pointer; padding: 0; }
.del-btn:hover { color: #ff4757; text-decoration: underline; }

.drawer-footer { padding: 20px; border-top: 1px solid #eee; background: #fff; }
.total-price { display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px; font-size: 16px; }
.total-price strong { color: #ff4757; font-size: 20px; }
.checkout-btn { width: 100%; padding: 12px; background: #1e3c72; color: white; border: none; border-radius: 30px; font-size: 16px; cursor: pointer; transition: background 0.2s; }
.checkout-btn:hover { background: #2a5298; }
</style>